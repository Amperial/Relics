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
