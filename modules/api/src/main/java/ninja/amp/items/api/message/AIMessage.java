/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.api.message;

/**
 * Messages sent by the amp items plugin.
 *
 * @author Austin Payne
 */
public enum AIMessage implements Message {
    PREFIX("Prefix", "&8[&bAmpItems&8] &7"),
    PREFIX_ERROR("ErrorPrefix", "&8[&bAmpItems&8] &4"),
    RELOAD("Reload", "Reloaded %s."),

    COMMAND_NOTAPLAYER("Command.NotAPlayer", "You must be a player to use this command."),
    COMMAND_NOPERMISSION("Command.NoPermission", "You do not have permission to use this command."),
    COMMAND_INVALID("Command.Invalid", "Unknown commad. Type &b/aitem help&4 for help."),
    COMMAND_USAGE("Command.Usage", "Usage: %s"),

    ITEM_SPAWN("Item.Spawn", "Spawned item: %s&7!"),
    ITEM_DOESNTEXIST("Item.DoesntExist", "Item not found: %s"),
    ITEM_NOTCUSTOM("Item.NotCustom", "Item is not a custom item."),
    ITEM_NOTHOLDING("Item.NotHolding", "Not holding an item."),

    SOCKET_DOESNTEXIST("Socket.DoesntExist", "Item doesn't have a socket."),
    SOCKET_NOTFOUND("Socket.NotFound", "Socket of name %s not found."),
    SOCKET_NOGEMS("Socket.NoGems", "Item doesn't have a gem to extract."),
    SOCKET_FULL("Socket.Full", "Item's sockets are full."),
    SOCKET_NOACCEPT("Socket.NoAccept", "Item can't accept the gem."),
    SOCKET_EXTRACT("Socket.Extract", "Extracted gem %s&7 from socket!");

    private final String path;
    private final String defaultMessage;
    private String message;

    AIMessage(String path, String defaultMessage) {
        this.message = defaultMessage;
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefault() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return message;
    }

}
