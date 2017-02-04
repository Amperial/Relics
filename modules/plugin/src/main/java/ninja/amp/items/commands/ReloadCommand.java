/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.commands;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.message.AIMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * A command that reloads the plugin.
 *
 * @author Austin Payne
 */
public class ReloadCommand extends Command {

    public ReloadCommand(ItemPlugin plugin) {
        super(plugin, "reload");
        setDescription("Reloads the amp items plugin.");
        setCommandUsage("/aitem reload");
        setPermission(new Permission("ampitems.reload", PermissionDefault.OP));
        setPlayerOnly(false);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Bukkit.getPluginManager().disablePlugin(getPlugin());
        getPlugin().getPluginLoader().enablePlugin(getPlugin());
        plugin.getMessenger().sendMessage(sender, AIMessage.RELOAD, getPlugin().getName());
    }

}
