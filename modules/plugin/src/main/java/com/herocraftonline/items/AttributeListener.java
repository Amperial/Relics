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
import com.herocraftonline.items.api.item.attribute.attributes.projectiles.Velocity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class AttributeListener implements Listener {

    private final ItemPlugin plugin;

    public AttributeListener(ItemPlugin plugin) {
        this.plugin = plugin;

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBowShoot(EntityShootBowEvent event) {
        plugin.getItemManager().getItem(event.getBow()).ifPresent(bow -> {
            // Handle velocity attribute
            bow.getAttribute(Velocity.class).ifPresent(attribute -> {
                Entity projectile = event.getProjectile();
                Vector velocity = projectile.getVelocity();
                if (!attribute.isMultiplier()) {
                    velocity.normalize();
                }
                velocity.multiply(attribute.getValue());
                projectile.setVelocity(velocity);
            });
        });
    }

}
