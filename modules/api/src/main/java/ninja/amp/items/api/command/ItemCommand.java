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

public abstract class ItemCommand extends Command {

    /**
     * Creates a new item command.
     *
     * @param plugin The item plugin instance
     * @param name   The name of the command
     */
    public ItemCommand(ItemPlugin plugin, String name) {
        super(plugin, name);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Messenger messenger = plugin.getMessenger();
        ItemManager itemManager = plugin.getItemManager();

        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem == null) {
            messenger.sendErrorMessage(player, AIMessage.ITEM_NOTHOLDING);
        } else {
            if (itemManager.isItem(heldItem)) {
                Item item = itemManager.getItem(heldItem);

                // Execute ItemCommand
                execute(command, player, args, item);
            } else {
                messenger.sendErrorMessage(player, AIMessage.ITEM_NOTCUSTOM);
            }
        }
    }

    public abstract void execute(String command, Player player, List<String> args, Item item);

}
