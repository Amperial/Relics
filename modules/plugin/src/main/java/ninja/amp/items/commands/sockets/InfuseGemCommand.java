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
import ninja.amp.items.api.command.DualItemCommand;
import ninja.amp.items.api.equipment.EquipmentManager;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.attribute.attributes.sockets.Gem;
import ninja.amp.items.api.item.attribute.attributes.sockets.Socket;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Optional;

public class InfuseGemCommand extends DualItemCommand {

    public InfuseGemCommand(ItemPlugin plugin) {
        super(plugin, "infuse");
        setDescription("Infuses the gem in your off hand into a socket.");
        setCommandUsage("/aitem socket infuse [socket]");
        setPermission(new Permission("ampitems.socket.infuse", PermissionDefault.OP));
        setArgumentRange(0, 1);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item mainHand, Item offHand) {
        Messenger messenger = plugin.getMessenger();

        // Load Gem from offhand Item
        Gem gem;
        Optional<Gem> gemOptional = offHand.getAttributeDeep(Gem.class);
        if (!gemOptional.isPresent()) {
            messenger.sendErrorMessage(player, AIMessage.GEM_NOTHOLDING);
            return;
        }
        gem = gemOptional.get();

        // Load Socket from mainhand Item
        Socket socket;
        if (args.size() > 0) {
            String name = args.get(0);
            Optional<Socket> socketOptional = mainHand.getAttributeDeep(name, Socket.class);
            if (!socketOptional.isPresent()) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_NOTFOUND, name);
                return;
            }
            socket = socketOptional.get();
            if (socket.hasGem()) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_FULL);
                return;
            } else if (!socket.acceptsGem(gem)) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_NOACCEPT);
                return;
            }
        } else {
            if (!mainHand.hasAttributeDeep(s -> s instanceof Socket && !((Socket) s).hasGem())) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_FULL);
                return;
            }
            Optional<Socket> socketOptional = mainHand.getAttributeDeep(s -> (!s.hasGem() && s.acceptsGem(gem)), Socket.class);
            if (!socketOptional.isPresent()) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_NOACCEPT);
                return;
            }
            socket = socketOptional.get();
        }

        // First make sure both items are unequipped
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, mainHand)) {
            equipManager.unEquip(player, mainHand);
        }
        if (equipManager.isEquipped(player, offHand)) {
            equipManager.unEquip(player, offHand);
        }

        // Remove gem from gem item
        offHand.removeAttribute(gem);

        // Add gem item to gem
        gem.setItem(offHand);

        // Add gem to socket
        socket.setGem(gem);

        // Update item in inventory
        mainHand.updateItem(player.getInventory().getItemInMainHand());

        // Remove gem from inventory
        player.getInventory().setItemInOffHand(null);

        messenger.sendMessage(player, AIMessage.SOCKET_INFUSE, gem.getDisplayName());
    }

}
