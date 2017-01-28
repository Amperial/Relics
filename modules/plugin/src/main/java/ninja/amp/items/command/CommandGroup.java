/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.command;

import ninja.amp.items.AmpItems;
import ninja.amp.items.message.AIMessage;
import ninja.amp.items.message.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

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

    protected final AmpItems plugin;
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
     * @param plugin The amp items plugin instance
     * @param name   The name of the command
     */
    public CommandGroup(AmpItems plugin, String name) {
        this.plugin = plugin;
        this.name = name.toLowerCase();
    }

    /**
     * Gets the command group's name.
     *
     * @return The command group's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the plugin instance that created this command.
     *
     * @return The plugin instance
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets the command group's permission node.
     *
     * @return The command group's permission node
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the command group's permission node.
     *
     * @param permission The command group's permission node
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
     * @return The minimum required args length
     */
    public int getMinArgsLength() {
        return minArgsLength;
    }

    /**
     * Gets the maximum required args length of the command group.
     *
     * @return The maximum required args length
     */
    public int getMaxArgsLength() {
        return maxArgsLength;
    }

    /**
     * Sets the argument range of the command group.
     *
     * @param minArgsLength The minimum required args length
     * @param maxArgsLength The maximum required args length. -1 for no max
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
     * @param playerOnly If the command group can only be run by a player
     */
    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }

    /**
     * Checks to see if the command group has the child command.
     *
     * @param name The name of the child command
     * @return {@code true} if the command group has the child command
     */
    public boolean hasChildCommand(String name) {
        return children.containsKey(name.toLowerCase());
    }

    /**
     * Gets a child command of the command group.
     *
     * @param name The name of the child command
     * @return The child command
     */
    public CommandGroup getChildCommand(String name) {
        return children.get(name.toLowerCase());
    }

    /**
     * Adds a child command to the command group.
     *
     * @param command The child command
     * @return The command group the child command was added to
     */
    public CommandGroup addChildCommand(CommandGroup command) {
        children.put(command.getName().toLowerCase(), command);
        if (command instanceof Command && ((Command) command).getVisible()) {
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
     * @param deep If the method should return all children, or only the command group's immediate children
     * @return The command group's children
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
     * @param command The command label
     * @param sender  The sender of the command
     * @param args    The arguments sent with the command
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
                        messenger.sendErrorMessage(sender, AIMessage.COMMAND_NOTAPLAYER);
                    }
                } else {
                    messenger.sendErrorMessage(sender, AIMessage.COMMAND_NOPERMISSION, command);
                }
            } else {
                messenger.sendErrorMessage(sender, AIMessage.COMMAND_USAGE, ((Command) entry).getCommandUsage());
            }
        } else {
            String subCommand = args.size() == 0 ? "" : args.get(0);
            if (entry.hasChildCommand(subCommand)) {
                if (!args.isEmpty()) {
                    args.remove(0);
                }
                entry.execute(subCommand, sender, args);
            } else {
                messenger.sendErrorMessage(sender, AIMessage.COMMAND_INVALID);
            }
        }
    }

    /**
     * Gets the tab completion list of the command group.
     *
     * @param args The args already entered
     * @return The tab completion list of the command group
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
     * @param arg         The string being tab completed
     * @param suggestions The initial list of suggestions
     * @return The list of possible tab completions
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
