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
package ninja.amp.items.commands.items;

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

public class GiveItemCommand extends Command {

    public GiveItemCommand(ItemPlugin plugin) {
        super(plugin, "give");
        setDescription("Spawn a custom item into a player's inventory.");
        setCommandUsage("/aitem item give <player> <item> [args...]");
        setPermission(new Permission("ampitems.item.give", PermissionDefault.OP));
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
            messenger.sendErrorMessage(sender, AIMessage.ITEM_NOTONLINE, receivingName);
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

            messenger.sendMessage(sender, AIMessage.ITEM_SPAWN, item.getName());
            messenger.sendMessage(receiving, AIMessage.ITEM_RECEIVE, item.getName());
        } else {
            messenger.sendErrorMessage(sender, AIMessage.ITEM_DOESNTEXIST, itemName);
        }
    }

}
