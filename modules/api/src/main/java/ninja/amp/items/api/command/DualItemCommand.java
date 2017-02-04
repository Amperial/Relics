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
package ninja.amp.items.api.command;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class DualItemCommand extends Command {

    /**
     * Creates a new dual item command.
     *
     * @param plugin The item plugin instance
     * @param name   The name of the command
     */
    public DualItemCommand(ItemPlugin plugin, String name) {
        super(plugin, name);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (mainHand == null || offHand == null) {
            messenger.sendErrorMessage(player, AIMessage.ITEM_NOTHOLDING);
        } else {
            if (itemManager.isItem(mainHand) && itemManager.isItem(offHand)) {
                Item main = itemManager.getItem(mainHand);
                Item off = itemManager.getItem(offHand);

                // Execute DualItemCommand
                execute(command, player, args, main, off);
            } else {
                messenger.sendErrorMessage(player, AIMessage.ITEM_NOTCUSTOM);
            }
        }
    }

    public abstract void execute(String command, Player player, List<String> args, Item mainHand, Item offHand);

}
