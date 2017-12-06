/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.commands.misc;

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
import java.util.Optional;

public class TokenCommand extends Command {

    public TokenCommand(ItemPlugin plugin) {
        super(plugin, "token");
        setDescription("Spawn a token item into a player's inventory.");
        setCommandUsage("/rel token <player>");
        setPermission(new Permission("relics.token", PermissionDefault.TRUE));
        setArgumentRange(1, 1);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        // Get receiving player
        Player receiving = Bukkit.getPlayerExact(args.get(0));
        if (receiving == null) {
            messenger.sendErrorMessage(player, RelMessage.ITEM_NOTONLINE, args.get(0));
            return;
        }

        String itemName = "token/" + player.getName();
        Optional<Item> itemOptional = itemManager.getItem(itemName);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();

            receiving.getInventory().addItem(item.getItem());

            messenger.sendMessage(player, RelMessage.ITEM_SPAWN, item.getName());
            messenger.sendMessage(receiving, RelMessage.ITEM_RECEIVE, item.getName());
        } else {
            messenger.sendErrorMessage(player, RelMessage.ITEM_DOESNTEXIST, itemName);
        }
    }

}
