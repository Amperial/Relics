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
package ninja.amp.items.commands.sockets;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.equipment.EquipmentManager;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.attributes.sockets.Gem;
import ninja.amp.items.api.item.attribute.attributes.sockets.Socket;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class ExtractGemCommand extends SocketCommand {

    public ExtractGemCommand(ItemPlugin plugin) {
        super(plugin, "extract", 0, Socket::hasGem, AIMessage.SOCKET_EMPTY);
        setDescription("Extracts the gem from a socket into your inventory.");
        setCommandUsage("/aitem socket extract [socket]");
        setPermission(new Permission("ampitems.socket.extract", PermissionDefault.OP));
        setArgumentRange(0, 1);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item item, Socket socket) {
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        // First make sure item is unequipped
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, item)) {
            equipManager.unEquip(player, item);
        }

        // Get gem item
        Gem gem = socket.getGem();
        Item gemItem = gem.getItem();

        // Remove gem item from gem
        gem.setItem(null);

        // Add gem to gem item attributes
        gemItem.addAttribute(gem);

        // Add gem to inventory
        player.getInventory().addItem(gemItem.getItem());

        // Remove gem from socket
        socket.setGem(null);

        // Update item in inventory
        player.getInventory().setItemInMainHand(item.updateItem(player.getInventory().getItemInMainHand()));

        messenger.sendMessage(player, AIMessage.SOCKET_EXTRACT, gem.getDisplayName());
    }

}
