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
import ninja.amp.items.api.command.ItemCommand;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class ItemInfoCommand extends ItemCommand {

    public ItemInfoCommand(ItemPlugin plugin) {
        super(plugin, "info");
        setDescription("Prints information about the currently held custom item.");
        setCommandUsage("/aitem item info");
        setPermission(new Permission("ampitems.item.info", PermissionDefault.TRUE));
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, Player player, List<String> args, Item item) {
        Messenger messenger = plugin.getMessenger();

    }

}
