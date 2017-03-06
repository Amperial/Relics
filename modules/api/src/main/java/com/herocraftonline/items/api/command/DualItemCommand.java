/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.command;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * A command that can only be executed if holding an Item in both main hand and off hand.
 *
 * @author Austin Payne
 */
public abstract class DualItemCommand extends Command {

    /**
     * Creates a new dual item command.
     *
     * @param plugin the item plugin instance
     * @param name   the name of the command
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
            messenger.sendErrorMessage(player, RelMessage.ITEM_NOTHOLDING);
        } else {
            Optional<Item> main = itemManager.getItem(mainHand);
            Optional<Item> off = itemManager.getItem(offHand);
            if (main.isPresent() && off.isPresent()) {
                // Execute DualItemCommand
                execute(command, player, args, main.get(), off.get());
            } else {
                messenger.sendErrorMessage(player, RelMessage.ITEM_NOTCUSTOM);
            }
        }
    }

    /**
     * Executes the dual item command.
     *
     * @param command  the command label
     * @param player   the sender of the command
     * @param args     the arguments sent with the command
     * @param mainHand the item held in main hand
     * @param offHand  the item held in off hand
     */
    public abstract void execute(String command, Player player, List<String> args, Item mainHand, Item offHand);

}
