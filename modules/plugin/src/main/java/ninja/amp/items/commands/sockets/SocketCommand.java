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
import ninja.amp.items.api.command.ItemCommand;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.sockets.Socket;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Message;
import ninja.amp.items.api.message.Messenger;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class SocketCommand extends ItemCommand {

    private final int socketArg;
    private final Predicate<Socket> socketPredicate;
    private final Message predicateError;

    /**
     * Creates a new socket command.
     *
     * @param plugin The item plugin instance
     * @param name   The name of the command
     */
    public SocketCommand(ItemPlugin plugin, String name, int socketArg, Predicate<Socket> socketPredicate, Message predicateError) {
        super(plugin, name);

        this.socketArg = socketArg;
        this.socketPredicate = socketPredicate;
        this.predicateError = predicateError;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(String command, Player player, List<String> args, Item item) {
        Messenger messenger = plugin.getMessenger();

        // Load Socket from Item
        AttributeGroup attributes = item.getAttributes();
        Socket socket;
        if (args.size() > socketArg) {
            String name = args.get(socketArg);
            if (!attributes.hasAttribute(name, false)) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_NOTFOUND, name);
                return;
            }
            socket = (Socket) attributes.getAttribute(name, false);
            if (!socketPredicate.test(socket)) {
                messenger.sendErrorMessage(player, predicateError);
                return;
            }
        } else {
            Collection<Socket> sockets = (Collection<Socket>) attributes.getAttributes(DefaultAttributeType.SOCKET, false);
            if (sockets.isEmpty()) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_DOESNTEXIST);
                return;
            }
            Optional<Socket> s = sockets.stream().filter(socketPredicate).findFirst();
            if (s.isPresent()) {
                socket = s.get();
            } else {
                messenger.sendErrorMessage(player, predicateError);
                return;
            }
        }

        // Execute SocketCommand
        execute(command, player, args, item, socket);
    }

    public abstract void execute(String command, Player player, List<String> args, Item item, Socket socket);

}
