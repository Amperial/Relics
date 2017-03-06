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
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains and controls commands.
 *
 * @author Austin Payne
 */
public class CommandController implements TabExecutor {

    private ItemPlugin plugin;
    private Set<CommandGroup> commands = new LinkedHashSet<>();
    private CommandPageList pageList = null;

    /**
     * Creates a new command controller.
     *
     * @param plugin the item plugin instance
     */
    public CommandController(ItemPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        for (CommandGroup command : commands) {
            if (command.getName().equalsIgnoreCase(cmd.getName())) {
                String subCommand = args.length > 0 ? args[0] : "";
                if (command.hasChildCommand(subCommand)) {
                    List<String> argsList = new ArrayList<>(Arrays.asList(args));
                    if (!subCommand.isEmpty()) {
                        argsList.remove(0);
                    }
                    command.execute(subCommand, sender, argsList);
                } else {
                    plugin.getMessenger().sendErrorMessage(sender, RelMessage.COMMAND_INVALID);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        for (CommandGroup command : commands) {
            if (command.getName().equalsIgnoreCase(cmd.getName()) && args.length > 0) {
                int commandAmount = 0;
                for (String arg : args) {
                    if (!arg.isEmpty() && command.hasChildCommand(arg)) {
                        command = command.getChildCommand(arg);
                        commandAmount++;
                    }
                }
                if ((!(command instanceof Command) || args.length - commandAmount <= command.getMaxArgsLength()) && args.length > commandAmount) {
                    return command.tabComplete(Arrays.asList(args).subList(commandAmount, args.length));
                }
            }
        }
        return CommandGroup.EMPTY_LIST;
    }

    /**
     * Gets the commands in the command controller.
     *
     * @return the commands
     */
    public Set<CommandGroup> getCommands() {
        return commands;
    }

    /**
     * Adds a command to the command controller.
     *
     * @param command the command to add
     */
    public void addCommand(CommandGroup command) {
        commands.add(command);

        String prefix = command.getPlugin().getDescription().getName().toLowerCase();
        String label = command.getName().toLowerCase();
        Bukkit.getServer().getPluginCommand(prefix + ":" + label).setExecutor(this);
    }

    /**
     * Removes a command from the command controller.
     *
     * @param command the command to remove
     */
    public void removeCommand(CommandGroup command) {
        commands.remove(command);

        String prefix = command.getPlugin().getDescription().getName().toLowerCase();
        String label = command.getName().toLowerCase();
        Bukkit.getServer().getPluginCommand(prefix + ":" + label).setExecutor(null);
    }

    /**
     * Removes the command controller from being the command executor for the commands.
     */
    public void unregisterCommands() {
        for (CommandGroup command : commands) {
            String prefix = command.getPlugin().getDescription().getName().toLowerCase();
            String label = command.getName().toLowerCase();
            Bukkit.getServer().getPluginCommand(prefix + ":" + label).setExecutor(null);
        }
    }

    /**
     * Gets the page list of commands in the command controller.
     *
     * @return the command page list
     */
    public CommandPageList getPageList() {
        return pageList;
    }

    /**
     * Updates the page list of commands in the command controller.
     */
    public void updatePageList() {
        pageList = new CommandPageList(plugin);
    }

}
