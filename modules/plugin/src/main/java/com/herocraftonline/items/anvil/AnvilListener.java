/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.anvil;

import com.herocraftonline.items.anvil.AnvilMenu.AnvilHolder;
import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class AnvilListener implements Listener {

    private final ItemPlugin plugin;

    public AnvilListener(ItemPlugin plugin) {
        this.plugin = plugin;

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAnvilInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null && block.getType() == Material.ANVIL) {
            AnvilMenu.open(plugin, event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof AnvilHolder &&
                event.getWhoClicked() instanceof Player) {
            ((AnvilHolder) event.getInventory().getHolder()).getMenu().onClick(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof AnvilHolder &&
                event.getWhoClicked() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof AnvilHolder) {
            ((AnvilHolder) event.getInventory().getHolder()).getMenu().onClose(event);
        }
    }

    public void closeOpenMenus() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory() != null)
                .forEach(player -> {
                    Inventory inventory = player.getOpenInventory().getTopInventory();
                    if (inventory.getHolder() instanceof AnvilHolder) {
                        player.closeInventory();
                    }
                });
    }

}
