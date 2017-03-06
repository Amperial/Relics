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
import com.herocraftonline.items.api.command.Command;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class GiveItemCommand extends Command {

    public GiveItemCommand(ItemPlugin plugin) {
        super(plugin, "give");
        setDescription("Spawn a custom item into a player's inventory.");
        setCommandUsage("/rel give <player> <item> [args...]");
        setPermission(new Permission("relics.item.give", PermissionDefault.OP));
        setArgumentRange(2, -1);
        setPlayerOnly(false);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        // Get receiving player
        String receivingName = args.remove(0);
        Player receiving = Bukkit.getPlayerExact(receivingName);
        if (receiving == null) {
            messenger.sendErrorMessage(sender, RelMessage.ITEM_NOTONLINE, receivingName);
            return;
        }

        String itemName = args.remove(0);
        if (itemManager.hasItemConfig(itemName)) {
            Object[] itemArgs = args.toArray();

            // Replace string arguments with numbers where possible
            for (int i = 0; i < itemArgs.length; i++) {
                try {
                    String itemArg = (String) itemArgs[i];
                    double value = Double.valueOf(itemArg);
                    if (itemArg.contains(".")) {
                        itemArgs[i] = value;
                    } else {
                        itemArgs[i] = (int) value;
                    }
                } catch (NumberFormatException e) {
                    // Arg isn't a number
                }
            }

            Item item = itemManager.getItem(itemName, itemArgs);

            receiving.getInventory().addItem(item.getItem());

            messenger.sendMessage(sender, RelMessage.ITEM_SPAWN, item.getName());
            messenger.sendMessage(receiving, RelMessage.ITEM_RECEIVE, item.getName());
        } else {
            messenger.sendErrorMessage(sender, RelMessage.ITEM_DOESNTEXIST, itemName);
        }
    }

}
