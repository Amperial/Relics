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
package com.herocraftonline.items;

import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final ItemPlugin plugin;

    public PlayerListener(ItemPlugin plugin) {
        this.plugin = plugin;

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Call onPlayerJoin for all currently online players (handle reloading)
        Bukkit.getOnlinePlayers().forEach(this::onPlayerJoin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        onPlayerJoin(event.getPlayer());
    }

    public void onPlayerJoin(Player player) {
        plugin.getEquipmentManager().loadEquipment(player);
    }

}
