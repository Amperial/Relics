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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class GetItemCommand extends Command {

    public GetItemCommand(ItemPlugin plugin) {
        super(plugin, "get");
        setDescription("Spawn a custom item into your inventory.");
        setCommandUsage("/aitem item get <item> [args...]");
        setPermission(new Permission("ampitems.item.get", PermissionDefault.OP));
        setArgumentRange(1, -1);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

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

            player.getInventory().addItem(item.getItem());

            messenger.sendMessage(player, AIMessage.ITEM_SPAWN, item.getName());
        } else {
            messenger.sendErrorMessage(player, AIMessage.ITEM_DOESNTEXIST, itemName);
        }
    }

}
