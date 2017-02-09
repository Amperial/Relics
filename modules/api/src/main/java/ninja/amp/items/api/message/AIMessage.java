/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.message;

/**
 * Messages sent by the amp items plugin.
 *
 * @author Austin Payne
 */
public enum AIMessage implements Message {
    PREFIX("Prefix", "&8[&bAmpItems&8] &7"),
    PREFIX_SHORT("ShortPrefix", "&8[&bAI&8] &7"),
    PREFIX_ERROR("ErrorPrefix", "&8[&bAmpItems&8] &4"),
    PREFIX_ERROR_SHORT("ShortErrorPrefix", "&8[&bAI&8] &4"),
    RELOAD("Reload", "Reloaded %s."),

    COMMAND_NOTAPLAYER("Command.NotAPlayer", "You must be a player to use this command."),
    COMMAND_NOPERMISSION("Command.NoPermission", "You do not have permission to use this command."),
    COMMAND_INVALID("Command.Invalid", "Unknown commad. Type &b/aitem help&4 for help."),
    COMMAND_USAGE("Command.Usage", "Usage: %s"),

    ITEM_SPAWN("Item.Spawn", "Spawned item: %s&7!"),
    ITEM_RECEIVE("Item.Receive", "Received item: %s&7!"),
    ITEM_NOTONLINE("Item.NotOnline", "Player %s is not online!"),
    ITEM_DOESNTEXIST("Item.DoesntExist", "Item not found: %s"),
    ITEM_NOTCUSTOM("Item.NotCustom", "Item is not a custom item."),
    ITEM_NOTHOLDING("Item.NotHolding", "Not holding an item."),

    ITEM_EQUIPPED("Item.Equipped", "%s&7 equipped!"),
    ITEM_UNEQUIPPED("Item.UnEquipped", "%s&7 unequipped."),
    ITEM_NOTEQUIPPED("Item.NotEquipped", "Item is not equipped."),
    ITEM_CANTEQUIP("Item.CantEquip", "Cannot equip item."),
    ITEM_ALREADYEQUIPPED("Item.AlreadyEquipped", "Click again to replace equipped item"),

    SOCKET_NOTFOUND("Socket.NotFound", "Socket of name %s not found."),
    SOCKET_EMPTY("Socket.Empty", "Item doesn't have a gem to extract."),
    SOCKET_FULL("Socket.Full", "Item's doesn't have an empty socket."),
    SOCKET_NOACCEPT("Socket.NoAccept", "Socket can't accept the gem."),
    SOCKET_EXTRACT("Socket.Extract", "Extracted gem %s&7 from socket!"),
    SOCKET_INFUSE("Socket.Infuse", "Infused gem %s&7 into the socket!"),

    GEM_NOTHOLDING("Gem.NotHolding", "Not holding a gem in off hand."),

    SOULBOUND_MOVE("Soulbound.Move", "Cannot move a soulbound item out of inventory."),
    SOULBOUND_DROP("Soulbound.Drop", "Double-drop to destroy soulbound item."),
    SOULBOUND_DESTROY("Soulbound.Destroy", "Soulbound item destroyed!");

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
