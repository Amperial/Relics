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
import com.herocraftonline.items.api.command.DualItemCommand;
import com.herocraftonline.items.api.equipment.EquipmentManager;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Socket;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Optional;

public class InfuseGemCommand extends DualItemCommand {

    public InfuseGemCommand(ItemPlugin plugin) {
        super(plugin, "infuse");
        setDescription("Infuses the gem in your off hand into a socket.");
        setCommandUsage("/rel socket infuse [socket]");
        setPermission(new Permission("relics.socket.infuse", PermissionDefault.TRUE));
        setArgumentRange(0, 1);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item mainHand, Item offHand) {
        Messenger messenger = plugin.getMessenger();

        // Load Gem from offhand Item
        Gem gem;
        Optional<Gem> gemOptional = offHand.getAttributeDeep(Gem.class);
        if (!gemOptional.isPresent()) {
            messenger.sendErrorMessage(player, RelMessage.GEM_NOTHOLDING);
            return;
        }
        gem = gemOptional.get();

        // Load Socket from mainhand Item
        Socket socket;
        if (args.size() > 0) {
            String name = args.get(0);
            Optional<Socket> socketOptional = mainHand.getAttributeDeep(Socket.class, name);
            if (!socketOptional.isPresent()) {
                messenger.sendErrorMessage(player, RelMessage.SOCKET_NOTFOUND, name);
                return;
            }
            socket = socketOptional.get();
            if (socket.hasGem()) {
                messenger.sendErrorMessage(player, RelMessage.SOCKET_FULL);
                return;
            } else if (!socket.acceptsGem(gem)) {
                messenger.sendErrorMessage(player, RelMessage.SOCKET_NOACCEPT);
                return;
            }
        } else {
            if (!mainHand.hasAttributeDeep(s -> s instanceof Socket && !((Socket) s).hasGem())) {
                messenger.sendErrorMessage(player, RelMessage.SOCKET_FULL);
                return;
            }
            Optional<Socket> socketOptional = mainHand.getAttributeDeep(Socket.class, s -> (!s.hasGem() && s.acceptsGem(gem)));
            if (!socketOptional.isPresent()) {
                messenger.sendErrorMessage(player, RelMessage.SOCKET_NOACCEPT);
                return;
            }
            socket = socketOptional.get();
        }

        // First make sure both items are unequipped
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, mainHand)) {
            equipManager.unEquip(player, mainHand, player.getInventory().getItemInMainHand());
        }
        if (equipManager.isEquipped(player, offHand)) {
            equipManager.unEquip(player, offHand, player.getInventory().getItemInOffHand());
        }

        // Remove gem from gem item
        offHand.removeAttribute(gem);

        // Add gem item to gem
        gem.setItem(offHand);

        // Add gem to socket
        socket.setGem(gem);

        // Update item in inventory
        player.getInventory().setItemInMainHand(mainHand.updateItem(player.getInventory().getItemInMainHand()));

        // Remove gem from inventory
        player.getInventory().setItemInOffHand(null);

        messenger.sendMessage(player, RelMessage.SOCKET_INFUSE, gem.getDisplayName());
    }

}
