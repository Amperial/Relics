/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.menu;

import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Passes inventory click events to their menus for handling.
 *
 * @author Austin Payne
 */
public class MenuListener implements Listener {

    /**
     * Creates a new menu listener.
     *
     * @param plugin the item plugin instance
     */
    public MenuListener(ItemPlugin plugin) {
        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof MenuHolder) {
            event.setCancelled(true);
            ((MenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }

    /**
     * Closes all item menus currently open.
     */
    public void closeOpenMenus() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory() != null)
                .forEach(player -> {
                    Inventory inventory = player.getOpenInventory().getTopInventory();
                    if (inventory.getHolder() instanceof MenuHolder) {
                        player.closeInventory();
                    }
                });
    }

}
