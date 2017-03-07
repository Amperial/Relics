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
package com.herocraftonline.items.commands.sockets;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.equipment.EquipmentManager;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Socket;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class ExtractGemCommand extends SocketCommand {

    public ExtractGemCommand(ItemPlugin plugin) {
        super(plugin, "extract", 0, Socket::hasGem, RelMessage.SOCKET_EMPTY);
        setDescription("Extracts the gem from a socket into your inventory.");
        setCommandUsage("/rel socket extract [socket]");
        setPermission(new Permission("relics.socket.extract", PermissionDefault.TRUE));
        setArgumentRange(0, 1);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item item, Socket socket) {
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        // First make sure item is unequipped
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, item)) {
            equipManager.unEquip(player, item, player.getInventory().getItemInMainHand());
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

        messenger.sendMessage(player, RelMessage.SOCKET_EXTRACT, gem.getDisplayName());
    }

}
