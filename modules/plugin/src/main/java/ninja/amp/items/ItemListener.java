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
import ninja.amp.items.api.equipment.EquipmentManager;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.Soulbound;
import ninja.amp.items.api.item.attribute.attributes.stats.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Iterator;
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

    private boolean handleItemUse(Player player, ItemStack itemStack) {
        Item item = plugin.getItemManager().getItem(itemStack);
        return item == null || handleItemUse(player, item);
    }

    private boolean handleItemUse(Player player, Item item) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, item)) {
            return true;
        } else {
            if (equipManager.canEquip(player, item)) {
                equipManager.equip(player, item);
            }
            return false;
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Item item = plugin.getItemManager().getItem(event.getItem());
        if (item != null) {
            if (handleItemUse(event.getPlayer(), item)) {
                item.onClick(event, true);
            } else {
                item.onClick(event, false);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!handleItemUse(player, player.getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        } else if (!handleItemUse(player, player.getInventory().getItemInOffHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        LivingEntity damager = getLivingDamager(event);
        if (damager != null) {
            ItemManager itemManager = plugin.getItemManager();
            ItemStack itemStack = damager.getEquipment().getItemInMainHand();
            Item item = itemManager.getItem(itemStack);
            if (item != null) {
                if (damager instanceof Player && !handleItemUse((Player) damager, item)) {
                    event.setCancelled(true);
                } else {
                    item.forEachDeep(attribute -> {
                        event.setDamage(event.getDamage() + ((Damage) attribute).getVariation() * ((random.nextDouble() * 2) - 1));
                    }, ItemAttribute.type(Damage.class));
                }
            }
        }
    }

    private LivingEntity getLivingDamager(EntityDamageByEntityEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || cause == EntityDamageEvent.DamageCause.PROJECTILE) {
            Entity damager = event.getDamager();
            if (damager instanceof Projectile) {
                ProjectileSource source = ((Projectile) damager).getShooter();
                if (source instanceof LivingEntity) {
                    damager = (LivingEntity) source;
                }
            }
            if (damager instanceof LivingEntity) {
                return (LivingEntity) damager;
            }
        }
        return null;
    }

    private boolean handleInventory(Player player, Inventory inventory, Item item, InventoryType.SlotType slot) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        switch (inventory.getType()) {
            case CHEST:
            case DISPENSER:
            case DROPPER:
            case HOPPER:
                if (item.hasAttributeDeep(Soulbound.class)) {
                    return false;
                }
            case CREATIVE:
            case ENDER_CHEST:
                // UnEquip item if equipped
                if (equipManager.isEquipped(player, item)) {
                    equipManager.unEquip(player, item);
                }
            case PLAYER:
                // Equip armor items
                if (slot == InventoryType.SlotType.ARMOR) {
                    equipManager.replaceEquip(player, item);
                }
                return true;
            case ANVIL:
            case BEACON:
            case BREWING:
            case CRAFTING:
            case ENCHANTING:
            case FURNACE:
            case MERCHANT:
            case WORKBENCH:
            default:
                return false;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ItemManager itemManager = plugin.getItemManager();
        ItemStack itemStack = event.getCursor();
        Inventory inventory = event.getClickedInventory();
        if (event.isShiftClick()) {
            itemStack = event.getCurrentItem();
            Inventory top = event.getView().getTopInventory();
            if (top != null) {
                if (inventory == top) {
                    return;
                } else {
                    inventory = top;
                }
            }
        }
        Item item = itemManager.getItem(itemStack);
        if (item != null && !handleInventory(player, inventory, item, event.getSlotType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        if (itemManager.isItem(event.getOldCursor()) || itemManager.isItem(event.getCursor())) {
            event.setCancelled(true);
        }
    }

    private boolean handleItemDrop(Player player, Item item) {
        if (item.hasAttributeDeep(Soulbound.class)) {
            return false;
        } else {
            EquipmentManager equipManager = plugin.getEquipmentManager();
            if (equipManager.isEquipped(player, item)) {
                equipManager.unEquip(player, item);
            }
            return true;
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        ItemStack itemStack = event.getItemDrop().getItemStack();
        Item item = itemManager.getItem(itemStack);
        if (item != null && !handleItemDrop(event.getPlayer(), item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        Player player = event.getEntity();
        Iterator<ItemStack> drops = event.getDrops().iterator();
        while (drops.hasNext()) {
            ItemStack itemStack = drops.next();
            Item item = itemManager.getItem(itemStack);
            if (item != null && !handleItemDrop(player, item)) {
                drops.remove();
            }
        }
    }

}
