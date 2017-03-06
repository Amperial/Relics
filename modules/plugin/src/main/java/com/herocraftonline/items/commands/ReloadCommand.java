/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.commands;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.command.Command;
import com.herocraftonline.items.api.message.RelMessage;
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
        setDescription("Reloads the relics plugin.");
        setCommandUsage("/relics reload");
        setPermission(new Permission("relics.reload", PermissionDefault.OP));
        setPlayerOnly(false);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Bukkit.getPluginManager().disablePlugin(getPlugin());
        getPlugin().getPluginLoader().enablePlugin(getPlugin());
        plugin.getMessenger().sendMessage(sender, RelMessage.RELOAD, getPlugin().getName());
    }

}
