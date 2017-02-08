/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.message;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.config.ConfigAccessor;
import ninja.amp.items.api.config.DefaultConfig;
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
     * Basic color scheme in the amp items plugin.
     */
    public static ChatColor PRIMARY_COLOR = ChatColor.AQUA;
    public static ChatColor SECONDARY_COLOR = ChatColor.GRAY;
    public static ChatColor HIGHLIGHT_COLOR = ChatColor.DARK_GRAY;

    /**
     * Creates a new message manager.<br>
     * Must be created after the {@link ninja.amp.items.api.config.ConfigManager}!
     *
     * @param plugin The amp items plugin instance
     */
    public Messenger(ItemPlugin plugin) {
        this.plugin = plugin;
        this.debug = plugin.getConfig().getBoolean("debug", false);
        this.log = plugin.getLogger();

        registerMessages(EnumSet.allOf(AIMessage.class));

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
     * @param messages The messages to register
     * @return The messenger
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
     * @param recipientClass   The recipient's class
     * @param recipientHandler The recipient handler
     * @return The messenger
     */
    public Messenger registerRecipient(Class recipientClass, RecipientHandler recipientHandler) {
        recipientHandlers.put(recipientClass, recipientHandler);
        return this;
    }

    /**
     * Sends a message to a recipient.
     *
     * @param recipient The recipient of the message. Type of recipient must be registered
     * @param message   The message
     * @param replace   Strings to replace any occurences of %s in the message with
     */
    public void sendMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, AIMessage.PREFIX + s);
        }
    }

    /**
     * Sends an error message to a recipient.
     *
     * @param recipient The recipient of the error message. Type of recipient must be registered
     * @param message   The error message
     * @param replace   Strings to replace any occurences of %s in the message with
     */
    public void sendErrorMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, AIMessage.PREFIX_ERROR + s);
        }
    }

    /**
     * Sends a short message to a recipient.
     *
     * @param recipient The recipient of the message. Type of recipient must be registered
     * @param message   The message
     * @param replace   Strings to replace any occurences of %s in the message with
     */
    public void sendShortMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, AIMessage.PREFIX_SHORT + s);
        }
    }

    /**
     * Sends a short error message to a recipient.
     *
     * @param recipient The recipient of the error message. Type of recipient must be registered
     * @param message   The error message
     * @param replace   Strings to replace any occurences of %s in the message with
     */
    public void sendShortErrorMessage(Object recipient, Message message, Object... replace) {
        for (String s : (replace == null ? message.getMessage() : String.format(message.getMessage(), (Object[]) replace)).split("\\\\n")) {
            sendRawMessage(recipient, AIMessage.PREFIX_ERROR_SHORT + s);
        }
    }

    /**
     * Sends a raw message string to a recipient.
     *
     * @param recipient The recipient of the message. Type of recipient must be registered
     * @param message   The message
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
     * @param level    The level to log the message at
     * @param messages The message(s) to log
     */
    public void log(Level level, Object... messages) {
        for (Object message : messages) {
            log.log(level, message.toString());
        }
    }

    /**
     * Decides whether or not to print the stack trace of an exception.
     *
     * @param e The exception to debug
     */
    public void debug(Exception e) {
        if (debug) {
            e.printStackTrace();
        }
    }

    /**
     * Decides whether or not to print a debug message.
     *
     * @param message The message to debug
     */
    public void debug(Object message) {
        if (debug) {
            log.log(Level.INFO, message.toString());
        }
    }

    /**
     * Gets the logger.
     *
     * @return The logger
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
         * @param recipient The recipient
         * @param message   The message
         */
        void sendMessage(Object recipient, String message);

    }

}
