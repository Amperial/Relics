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
package ninja.amp.items.commands.misc;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class TokenCommand extends Command {

    public TokenCommand(ItemPlugin plugin) {
        super(plugin, "token");
        setDescription("Spawn a token item into a player's inventory.");
        setCommandUsage("/aitem token <player>");
        setPermission(new Permission("ampitems.token", PermissionDefault.OP));
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
            messenger.sendErrorMessage(player, AIMessage.ITEM_NOTONLINE, args.get(0));
            return;
        }

        String itemName = "token/" + player.getName();
        if (itemManager.hasItemConfig(itemName)) {
            Item item = itemManager.getItem(itemName);

            receiving.getInventory().addItem(item.getItem());

            messenger.sendMessage(player, AIMessage.ITEM_SPAWN, item.getName());
            messenger.sendMessage(receiving, AIMessage.ITEM_RECEIVE, item.getName());
        } else {
            messenger.sendErrorMessage(player, AIMessage.ITEM_DOESNTEXIST, itemName);
        }
    }

}
