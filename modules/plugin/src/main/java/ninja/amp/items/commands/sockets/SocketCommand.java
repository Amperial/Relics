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
import ninja.amp.items.api.command.ItemCommand;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.attribute.attributes.sockets.Socket;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Message;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.entity.Player;

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
        Socket socket;
        if (args.size() > socketArg) {
            String name = args.get(socketArg);
            Optional<Socket> optional = item.getAttributeDeep(name, Socket.class);
            if (!optional.isPresent()) {
                messenger.sendErrorMessage(player, AIMessage.SOCKET_NOTFOUND, name);
                return;
            }
            socket = optional.get();
            if (!socketPredicate.test(socket)) {
                messenger.sendErrorMessage(player, predicateError);
                return;
            }
        } else {
            Optional<Socket> optional = item.getAttributeDeep(socketPredicate, Socket.class);
            if (!optional.isPresent()) {
                messenger.sendErrorMessage(player, predicateError);
                return;
            }
            socket = optional.get();
        }

        // Execute SocketCommand
        execute(command, player, args, item, socket);

    }

    public abstract void execute(String command, Player player, List<String> args, Item item, Socket socket);

}
