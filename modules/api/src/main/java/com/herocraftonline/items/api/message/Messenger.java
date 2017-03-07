/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.message;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.storage.config.ConfigAccessor;
import com.herocraftonline.items.api.storage.config.ConfigManager;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages message sending, logging, and debugging.
 *
 * @author Austin Payne
 */
public class Messenger {

    private ItemPlugin plugin;
    private boolean debug;
    private Logger log;
    private Map<Class<?>, RecipientHandler> recipientHandlers = new HashMap<>();

    /**
     * Basic color scheme in the item plugin.
     */
    public static ChatColor PRIMARY_COLOR = ChatColor.AQUA;
    public static ChatColor SECONDARY_COLOR = ChatColor.GRAY;
    public static ChatColor HIGHLIGHT_COLOR = ChatColor.DARK_GRAY;

    /**
     * Creates a new message manager.<br>
     * Must be created after the {@link ConfigManager}!
     *
     * @param plugin the item plugin instance
     */
    public Messenger(ItemPlugin plugin) {
        this.plugin = plugin;
        this.debug = plugin.getConfig().getBoolean("debug", false);
        this.log = plugin.getLogger();

        registerMessages(EnumSet.allOf(RelMessage.class));

        // Register types of message recipients
        registerRecipient(CommandSender.class, (recipient, message) -> ((CommandSender) recipient).sendMessage(message));
        registerRecipient(Server.class, (recipient, message) -> ((Server) recipient).broadcastMessage(message));

        // Load color theme of messages from config
        FileConfiguration config = plugin.getConfig();
        PRIMARY_COLOR = ChatColor.valueOf(config.getString("colors.primary", "AQUA"));
        SECONDARY_COLOR = ChatColor.valueOf(config.getString("colors.secondary", "GRAY"));
        HIGHLIGHT_COLOR = ChatColor.valueOf(config.getString("colors.highlights", "DARK_GRAY"));
    }

    /**
     * Adds the message defaults to the message config and loads them.
     *
     * @param messages the messages to register
     * @return the messenger
     */
    public Messenger registerMessages(EnumSet<? extends Message> messages) {
        // Add missing messages to message config
        ConfigAccessor messageConfig = plugin.getConfigManager().getConfigAccessor(DefaultConfig.MESSAGE);
        FileConfiguration messageConfigFile = messageConfig.getConfig();
        messages.stream()
                .filter(message -> !messageConfigFile.isString(message.getPath()))
                .forEach(message -> messageConfigFile.set(message.getPath(), message.getMessage()));
        messageConfig.saveConfig();

        // Load messages from message config
        for (Message message : messages) {
            message.setMessage(ChatColor.translateAlternateColorCodes('&', messageConfigFile.getString(message.getPath())));
        }
        return this;
    }

    /**
     * Registers a recipient with a recipient handler.
     *
     * @param recipientClass   the recipient's class
     * @param recipientHandler the recipient handler
     * @return the messenger
     */
    public Messenger registerRecipient(Class recipientClass, RecipientHandler recipientHandler) {
        recipientHandlers.put(recipientClass, recipientHandler);
        return this;
    }

    /**
     * Sends a message to a recipient.
     *
     * @param recipient the recipient of the message. Type of recipient must be registered
     * @param message   the message
     * @param replace   strings to replace any occurences of %s in the message with
     */
    public void sendMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, RelMessage.PREFIX + s);
        }
    }

    /**
     * Sends an error message to a recipient.
     *
     * @param recipient the recipient of the error message. Type of recipient must be registered
     * @param message   the error message
     * @param replace   strings to replace any occurences of %s in the message with
     */
    public void sendErrorMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, RelMessage.PREFIX_ERROR + s);
        }
    }

    /**
     * Sends a short message to a recipient.
     *
     * @param recipient the recipient of the message. Type of recipient must be registered
     * @param message   the message
     * @param replace   strings to replace any occurences of %s in the message with
     */
    public void sendShortMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, RelMessage.PREFIX_SHORT + s);
        }
    }

    /**
     * Sends a short error message to a recipient.
     *
     * @param recipient the recipient of the error message. Type of recipient must be registered
     * @param message   the error message
     * @param replace   strings to replace any occurences of %s in the message with
     */
    public void sendShortErrorMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, RelMessage.PREFIX_ERROR_SHORT + s);
        }
    }

    /**
     * Sends a raw message string to a recipient.
     *
     * @param recipient the recipient of the message. Type of recipient must be registered
     * @param message   the message
     */
    public void sendRawMessage(Object recipient, Object message) {
        if (recipient != null && message != null) {
            for (Class<?> recipientClass : recipientHandlers.keySet()) {
                if (recipientClass.isAssignableFrom(recipient.getClass())) {
                    recipientHandlers.get(recipientClass).sendMessage(recipient, message.toString());
                    break;
                }
            }
        }
    }

    /**
     * Logs one or more messages to the console.
     *
     * @param level    the level to log the message at
     * @param messages the message(s) to log
     */
    public void log(Level level, Object... messages) {
        for (Object message : messages) {
            log.log(level, message.toString());
        }
    }

    /**
     * Decides whether or not to print the stack trace of an exception.
     *
     * @param e the exception to debug
     */
    public void debug(Exception e) {
        if (debug) {
            e.printStackTrace();
        }
    }

    /**
     * Decides whether or not to print a debug message.
     *
     * @param message the message to debug
     */
    public void debug(Object message) {
        if (debug) {
            log.log(Level.INFO, message.toString());
        }
    }

    /**
     * Gets the logger.
     *
     * @return the logger
     */
    public Logger getLogger() {
        return log;
    }

    /**
     * Handles sending a message to a recipient.
     */
    public interface RecipientHandler {

        /**
         * Sends a message to the recipient.
         *
         * @param recipient the recipient
         * @param message   the message
         */
        void sendMessage(Object recipient, String message);

    }

}
