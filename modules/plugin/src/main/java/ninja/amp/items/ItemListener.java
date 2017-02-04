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
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.Damage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Optional;
import java.util.Random;

public class ItemListener implements Listener {

    private final ItemPlugin plugin;
    private final Random random;

    public ItemListener(ItemPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Optional<Item> itemUsed = getItemUsed(event);
        if (itemUsed.isPresent()) {
            Item item = itemUsed.get();
            item.forEachDeep(attribute -> {
                event.setDamage(event.getDamage() + ((Damage) attribute).getVariation() * ((random.nextDouble() * 2) - 1));
            }, ItemAttribute.type(Damage.class));
        }
    }

    private Optional<Item> getItemUsed(EntityDamageByEntityEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (!(cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || cause == EntityDamageEvent.DamageCause.PROJECTILE)) {
            return Optional.empty();
        }
        Entity damager = event.getDamager();
        if (damager instanceof Projectile) {
            ProjectileSource source = ((Projectile) damager).getShooter();
            if (source instanceof LivingEntity) {
                damager = (LivingEntity) source;
            }
        }
        if (damager instanceof LivingEntity) {
            ItemStack itemStack = ((LivingEntity) damager).getEquipment().getItemInMainHand();
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                ItemManager itemManager = plugin.getItemManager();
                if (itemManager.isItem(itemStack)) {
                    return Optional.of(itemManager.getItem(itemStack));
                }
            }
        }
        return Optional.empty();
    }

}
