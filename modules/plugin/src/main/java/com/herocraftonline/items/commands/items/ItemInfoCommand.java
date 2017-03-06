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
package com.herocraftonline.items.commands.items;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.command.ItemCommand;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.message.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class ItemInfoCommand extends ItemCommand {

    public ItemInfoCommand(ItemPlugin plugin) {
        super(plugin, "info");
        setDescription("Prints information about the currently held custom item.");
        setCommandUsage("/rel item info");
        setPermission(new Permission("relics.item.info", PermissionDefault.TRUE));
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item item) {
        Messenger messenger = plugin.getMessenger();

    }

}
