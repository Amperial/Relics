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
package ninja.amp.items.api.menu.items;

import ninja.amp.items.api.menu.ItemClickEvent;
import ninja.amp.items.api.menu.ItemMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * A menu item that opens a nested item menu.
 *
 * @author Austin Payne
 */
public class SubMenuItem extends StaticMenuItem {

    private final JavaPlugin plugin;
    private final ItemMenu menu;

    public SubMenuItem(JavaPlugin plugin, String displayName, ItemStack icon, ItemMenu menu, String... lore) {
        super(displayName, icon, lore);

        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
        final UUID playerId = event.getPlayer().getUniqueId();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Player p = Bukkit.getPlayer(playerId);
            if (p != null) {
                menu.open(p);
            }
        }, 3);
    }

}
