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
package ninja.amp.items;

import ninja.amp.items.api.ItemPlugin;
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
