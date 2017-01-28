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

    private AmpItems plugin;
    private Set<CommandGroup> commands = new LinkedHashSet<>();
    private CommandPageList pageList = null;

    /**
     * Creates a new command controller.
     *
     * @param plugin The amp items plugin instance
     */
    public CommandController(AmpItems plugin) {
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
                    plugin.getMessenger().sendErrorMessage(sender, AIMessage.COMMAND_INVALID);
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
     * @return The commands
     */
    public Set<CommandGroup> getCommands() {
        return commands;
    }

    /**
     * Adds a command to the command controller.
     *
     * @param command The command to add
     */
    public void addCommand(CommandGroup command) {
        commands.add(command);

        String prefix = command.getPlugin().getDescription().getName().toLowerCase();
        String label = command.getName().toLowerCase();
        plugin.getServer().getPluginCommand(prefix + ":" + label).setExecutor(this);
    }

    /**
     * Removes a command from the command controller.
     *
     * @param command The command to remove
     */
    public void removeCommand(CommandGroup command) {
        commands.remove(command);

        String prefix = command.getPlugin().getDescription().getName().toLowerCase();
        String label = command.getName().toLowerCase();
        plugin.getServer().getPluginCommand(prefix + ":" + label).setExecutor(null);
    }

    /**
     * Removes the command controller from being the command executor for the commands.
     */
    public void unregisterCommands() {
        for (CommandGroup command : commands) {
            String prefix = command.getPlugin().getDescription().getName().toLowerCase();
            String label = command.getName().toLowerCase();
            plugin.getServer().getPluginCommand(prefix + ":" + label).setExecutor(null);
        }
    }

    /**
     * Gets the page list of commands in the command controller.
     *
     * @return The command page list
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
