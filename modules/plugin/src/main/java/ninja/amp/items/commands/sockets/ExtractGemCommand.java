/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.commands.sockets;

import ninja.amp.items.api.ItemPlugin;
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
        super(plugin, "extract", 0, Socket::hasGem, AIMessage.SOCKET_NOGEMS);
        setDescription("Extracts the gem from a socket into your inventory.");
        setCommandUsage("/aitem socket extract [name]");
        setPermission(new Permission("ampitems.socket.extract", PermissionDefault.OP));
        setArgumentRange(0, 1);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item item, Socket socket) {
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        // Get gem item
        Gem gem = socket.getGem();
        Item gemItem = gem.getItem();

        // Remove gem item from gem
        gem.setItem(null);

        // Add gem to gem item attributes
        gemItem.getAttributes().addAttribute(gem);

        // Add gem to inventory
        player.getInventory().addItem(gemItem.getItem());

        // Remove gem from socket
        socket.setGem(null);

        // Update item in inventory
        item.updateItem(player.getInventory().getItemInMainHand());

        messenger.sendMessage(player, AIMessage.SOCKET_EXTRACT, gem.getName());
    }

}
