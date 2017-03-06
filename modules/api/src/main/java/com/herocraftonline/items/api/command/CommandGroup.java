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
package com.herocraftonline.items.api.command;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A command that only contains child commands.
 *
 * @author Austin Payne
 */
public class CommandGroup {

    protected final ItemPlugin plugin;
    private final String name;
    private final Map<String, CommandGroup> children = new LinkedHashMap<>();
    private final List<String> tabCompleteList = new ArrayList<>();
    private Permission permission = null;
    private int minArgsLength = 0;
    private int maxArgsLength = -1;
    private boolean playerOnly = true;

    /**
     * An empty list of strings for use in command tab completion.
     */
    public static final List<String> EMPTY_LIST = new ArrayList<>();

    /**
     * Creates a new command group.
     *
     * @param plugin the item plugin instance
     * @param name   the name of the command
     */
    public CommandGroup(ItemPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name.toLowerCase();
    }

    /**
     * Gets the command group's name.
     *
     * @return the command group's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the plugin instance that created this command.
     *
     * @return the plugin instance
     */
    public ItemPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets the command group's permission node.
     *
     * @return the command group's permission node
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the command group's permission node.
     *
     * @param permission the command group's permission node
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
        if (Bukkit.getServer().getPluginManager().getPermission(permission.getName()) == null) {
            Bukkit.getServer().getPluginManager().addPermission(permission);
        }
    }

    /**
     * Gets the minimum required args length of the command group.
     *
     * @return the minimum required args length
     */
    public int getMinArgsLength() {
        return minArgsLength;
    }

    /**
     * Gets the maximum required args length of the command group.
     *
     * @return the maximum required args length
     */
    public int getMaxArgsLength() {
        return maxArgsLength;
    }

    /**
     * Sets the argument range of the command group.
     *
     * @param minArgsLength the minimum required args length
     * @param maxArgsLength the maximum required args length. -1 for no max
     */
    public void setArgumentRange(int minArgsLength, int maxArgsLength) {
        this.minArgsLength = minArgsLength;
        this.maxArgsLength = maxArgsLength;
    }

    /**
     * Checks to see if the command group can only be run by a player.
     *
     * @return {@code true} if the command group is player only
     */
    public boolean isPlayerOnly() {
        return playerOnly;
    }

    /**
     * Sets if the command group can only be run by a player.
     *
     * @param playerOnly the the command group can only be run by a player
     */
    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }

    /**
     * Checks to see if the command group has the child command.
     *
     * @param name the name of the child command
     * @return {@code true} if the command group has the child command
     */
    public boolean hasChildCommand(String name) {
        return children.containsKey(name.toLowerCase());
    }

    /**
     * Gets a child command of the command group.
     *
     * @param name the name of the child command
     * @return the child command
     */
    public CommandGroup getChildCommand(String name) {
        return children.get(name.toLowerCase());
    }

    /**
     * Adds a child command to the command group.
     *
     * @param command the child command
     * @return the command group the child command was added to
     */
    public CommandGroup addChildCommand(CommandGroup command) {
        children.put(command.getName().toLowerCase(), command);
        if (command instanceof Command && ((Command) command).isVisible()) {
            tabCompleteList.add(command.getName().toLowerCase());
        }
        if (permission != null && command.getPermission() != null) {
            command.getPermission().addParent(permission, true);
        }
        return this;
    }

    /**
     * Gets the command group's children.
     *
     * @param deep the the method should return all children, or only the command group's immediate children
     * @return the command group's children
     */
    public List<CommandGroup> getChildren(boolean deep) {
        if (deep) {
            List<CommandGroup> deepChildren = new ArrayList<>();
            for (CommandGroup child : children.values()) {
                if (child instanceof Command && !deepChildren.contains(child)) {
                    deepChildren.add(child);
                }
                child.getChildren(true).stream().filter(deepChild -> !deepChildren.contains(deepChild)).forEach(deepChildren::add);
            }
            return deepChildren;
        } else {
            return new ArrayList<>(children.values());
        }
    }

    /**
     * The command executor.
     *
     * @param command the command label
     * @param sender  the sender of the command
     * @param args    the arguments sent with the command
     */
    public void execute(String command, CommandSender sender, List<String> args) {
        Messenger messenger = plugin.getMessenger();

        CommandGroup entry = children.get(command.toLowerCase());
        if (entry instanceof Command) {
            if ((entry.getMinArgsLength() <= args.size() || entry.getMinArgsLength() == -1) && (entry.getMaxArgsLength() >= args.size() || entry.getMaxArgsLength() == -1)) {
                if (entry.getPermission() == null || sender.hasPermission(entry.getPermission())) {
                    if (sender instanceof Player || !entry.isPlayerOnly()) {
                        entry.execute(command, sender, args);
                    } else {
                        messenger.sendErrorMessage(sender, RelMessage.COMMAND_NOTAPLAYER);
                    }
                } else {
                    messenger.sendErrorMessage(sender, RelMessage.COMMAND_NOPERMISSION, command);
                }
            } else {
                messenger.sendErrorMessage(sender, RelMessage.COMMAND_USAGE, ((Command) entry).getCommandUsage());
            }
        } else {
            String subCommand = args.size() == 0 ? "" : args.get(0);
            if (entry.hasChildCommand(subCommand)) {
                if (!args.isEmpty()) {
                    args.remove(0);
                }
                entry.execute(subCommand, sender, args);
            } else {
                messenger.sendErrorMessage(sender, RelMessage.COMMAND_INVALID);
            }
        }
    }

    /**
     * Gets the tab completion list of the command group.
     *
     * @param args the args already entered
     * @return the tab completion list of the command group
     */
    public List<String> tabComplete(List<String> args) {
        switch (args.size()) {
            case 1:
                return tabCompletions(args.get(0), tabCompleteList);
            default:
                return EMPTY_LIST;
        }
    }

    /**
     * Gets a list of possible tab completions from an initial list of suggestions.
     *
     * @param arg         the string being tab completed
     * @param suggestions the initial list of suggestions
     * @return the list of possible tab completions
     */
    public static List<String> tabCompletions(String arg, List<String> suggestions) {
        if (arg.isEmpty()) {
            return suggestions;
        }
        arg = arg.toLowerCase();
        List<String> modifiedList = new ArrayList<>();
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().startsWith(arg)) {
                modifiedList.add(suggestion);
            }
        }
        if (modifiedList.isEmpty()) {
            return suggestions;
        }
        return modifiedList;
    }

}
