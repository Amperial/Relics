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
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.config.ConfigManager;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class GetItemCommand extends Command {

    public GetItemCommand(ItemPlugin plugin) {
        super(plugin, "get");
        setDescription("Spawn a custom attribute item into your inventory.");
        setCommandUsage("/aitem item get <item>");
        setPermission(new Permission("ampitems.item.get", PermissionDefault.OP));
        setArgumentRange(1, 1);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        ConfigManager configManager = plugin.getConfigManager();
        ItemManager itemManager = plugin.getItemManager();

        String itemName = args.get(0);
        if (itemManager.hasItemConfig(itemName)) {
            Item item = itemManager.getItem(itemName);

            player.getInventory().addItem(item.getItem());

            // TODO: Item spawned
        } else {
            // TODO: Item not registered
        }
    }

}
