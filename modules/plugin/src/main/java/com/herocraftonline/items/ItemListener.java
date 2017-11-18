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
import com.herocraftonline.items.api.equipment.EquipmentManager;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.attributes.Damage;
import com.herocraftonline.items.api.item.attribute.attributes.Durability;
import com.herocraftonline.items.api.item.attribute.attributes.Soulbound;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class ItemListener implements Listener {

    private static final String FIRED_BOW_META_KEY = "relics:bow-fired";

    private final ItemPlugin plugin;
    private final Random random;
    private final Map<UUID, Long> soulbound;
    private final Map<UUID, List<Item>> deathItems;

    public ItemListener(ItemPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
        this.soulbound = new HashMap<>();
        this.deathItems = new HashMap<>();

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler
//    public void test(InventoryClickEvent event) {
//        event.getWhoClicked().sendMessage("-----INVENTORY CLICK-----");
//        event.getWhoClicked().sendMessage("ACTION: " + event.getAction());
//        event.getWhoClicked().sendMessage("CLICK: " + event.getClick());
//        event.getWhoClicked().sendMessage("ITEM: " + event.getCurrentItem());
//        event.getWhoClicked().sendMessage("SLOT TYPE: " + event.getSlotType());
//        event.getWhoClicked().sendMessage("SLOT INDEX: " + event.getSlot());
//        event.getWhoClicked().sendMessage("RAW SLOT INDEX: " + event.getRawSlot());
//    }
//
//    @EventHandler
//    public void test(InventoryDragEvent event) {
//        event.getWhoClicked().sendMessage("-----INVENTORY DRAG-----");
//        event.getWhoClicked().sendMessage("CURSOR: " + event.getCursor());
//        event.getWhoClicked().sendMessage("OLD CURSOR: " + event.getOldCursor());
//        event.getWhoClicked().sendMessage("SLOTS: " + event.getInventorySlots());
//        event.getWhoClicked().sendMessage("NEW ITEMS: " + event.getNewItems());
//        event.getWhoClicked().sendMessage("DRAG TYPE: " + event.getType());
//        event.getWhoClicked().sendMessage("RAW SLOTS: " + event.getRawSlots());
//    }
//
//    @EventHandler
//    public void test(InventoryCreativeEvent event) {
//        event.getWhoClicked().sendMessage("-----INVENTORY INTERACT-----");
//        event.getWhoClicked().sendMessage("ACTION: " + event.getAction());
//        event.getWhoClicked().sendMessage("CLICK: " + event.getClick());
//        event.getWhoClicked().sendMessage("ITEM: " + event.getCurrentItem());
//        event.getWhoClicked().sendMessage("SLOT TYPE: " + event.getSlotType());
//        event.getWhoClicked().sendMessage("SLOT INDEX: " + event.getSlot());
//        event.getWhoClicked().sendMessage("RAW SLOT INDEX: " + event.getRawSlot());
//        event.getWhoClicked().sendMessage("CURSOR: " + event.getCursor());
//    }
//
//    @EventHandler
//    public void test(CraftItemEvent event) {
//        event.getWhoClicked().sendMessage("-----INVENTORY INTERACT-----");
//        event.getWhoClicked().sendMessage("ACTION: " + event.getAction());
//        event.getWhoClicked().sendMessage("CLICK: " + event.getClick());
//        event.getWhoClicked().sendMessage("ITEM: " + event.getCurrentItem());
//        event.getWhoClicked().sendMessage("SLOT TYPE: " + event.getSlotType());
//        event.getWhoClicked().sendMessage("SLOT INDEX: " + event.getSlot());
//        event.getWhoClicked().sendMessage("RAW SLOT INDEX: " + event.getRawSlot());
//        event.getWhoClicked().sendMessage("CURSOR: " + event.getCursor());
//    }

    /*
    private boolean handleItemUse(Player player, ItemStack itemStack) {
        Optional<Item> item = plugin.getItemManager().getItem(itemStack);
        return !item.isPresent() || handleItemUse(player, item.get(), itemStack);
    }

    private boolean handleItemUse(Player player, Item item, ItemStack itemStack) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        return equipManager.getPlayerEquipment().isEquipped(player, item);
    }
    */


    /*
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) return;
        ItemStack itemStack = event.getItem();
        Optional<Item> itemOptional = plugin.getItemManager().getItem(itemStack);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.onClick(event, item);
        }
    }
    */

    /*
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack;
        switch (event.getHand()) {
            case HAND:
                itemStack = player.getInventory().getItemInMainHand();
                break;
            case OFF_HAND:
                itemStack = player.getInventory().getItemInOffHand();
                break;
            default:
                return;
        }
        Optional<Item> itemOptional = plugin.getItemManager().getItem(itemStack);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.onClick(event, item);
        }
    }
    */


    /*
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityShootBow(EntityShootBowEvent event) {

        if (!(event.getProjectile() instanceof Arrow)) return;

        LivingEntity shooter = event.getEntity();
        Arrow arrow = (Arrow) event.getProjectile();

        ItemManager itemManager = plugin.getItemManager();
        ItemStack itemStack = event.getBow();
        Optional<Item> itemOptional = itemManager.getItem(itemStack);

        if (itemOptional.isPresent()) {

            Item item = itemOptional.get();

            if (shooter instanceof  Player && !handleItemUse((Player) shooter, item, itemStack)) {
                event.setCancelled(true);
                return;
            }

            Optional<Durability> durability = item.getAttribute(Durability.class);
            if (durability.isPresent() && !durability.get().isUsable()) {
                if (shooter instanceof Player) {
                    plugin.getEquipmentManager().unEquip((Player) shooter, item, itemStack);
                }
                event.setCancelled(true);
                return;
            }

            double damage = 0;
            double variation = 0;
            for (Damage attribute : item.getAttributes(Damage.class)) {
                damage += attribute.getDamage();
                variation += attribute.getVariation();
            }

            arrow.spigot().setDamage(damage + (variation * ((random.nextDouble() * 2) - 1)));

//            if (event.getProjectile().getType() == EntityType.ARROW) {
//                Arrow arrow = (Arrow) event.getProjectile();
//                arrow.setMetadata(FIRED_BOW_META_KEY, new FixedMetadataValue(plugin, item));
//            }

            if (durability.isPresent() && durability.get().damage(1)) {
                shooter.getEquipment().setItemInMainHand(item.updateItem(itemStack));
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (event.getDamager() instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) event.getDamager();
                ItemManager itemManager = plugin.getItemManager();
                ItemStack itemStack = damager.getEquipment().getItemInMainHand();
                Optional<Item> itemOptional = itemManager.getItem(itemStack);
                if (itemOptional.isPresent()) {
                    Item item = itemOptional.get();
                    if (damager instanceof Player && !handleItemUse((Player) damager, item, itemStack)) {
                        event.setCancelled(true);
                        return;
                    }
                    Optional<Durability> durabilityOptional = item.getAttribute(Durability.class);
                    if (durabilityOptional.isPresent() && !durabilityOptional.get().isUsable()) {
                        if (damager instanceof Player) {
                            plugin.getEquipmentManager().unEquip((Player) damager, item, itemStack);
                        }
                        event.setCancelled(true);
                        return;
                    }

//                    item.forEachDeep(Damage.class, attribute -> {
//                        event.setDamage(event.getDamage() + attribute.getVariation() * ((random.nextDouble() * 2) - 1));
//                    });

//                    Optional<Damage> damageOptional = item.getAttribute(Damage.class);
//                    if (damageOptional.isPresent()) {
//                        Damage damage = damageOptional.get();
//                        event.setDamage(event.getDamage() + (damage.getVariation() * ((random.nextDouble() * 2) - 1)));
//                    }

                    double variation = 0;
                    for (Damage attribute : item.getAttributes(Damage.class)) {
                        variation += attribute.getVariation();
                    }
                    event.setDamage(event.getDamage() + variation * ((random.nextDouble() * 2) - 1));

                    if (durabilityOptional.isPresent() && durabilityOptional.get().damage(1)) {
                        damager.getEquipment().setItemInMainHand(item.updateItem(itemStack));
                    }
                }
            }
        }
//        else if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
//            if (event.getDamager() instanceof Arrow) {
//                Arrow arrow = (Arrow) event.getDamager();
//
//                if (arrow.hasMetadata(FIRED_BOW_META_KEY)) {
//                    Item item = (Item) arrow.getMetadata(FIRED_BOW_META_KEY).get(0).value();
//                    Optional<Damage> damageOptional = item.getAttribute(Damage.class);
//                    if (damageOptional.isPresent()) {
//                        Damage damage = damageOptional.get();
//                        event.setDamage(damage.getDamage() + (damage.getVariation() * ((random.nextDouble() * 2) - 1)));
//                    }
//                }
//            }
//        }
    }

//    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
//    public void onEntityDamageByEntity1(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage LOWEST: " + event.getDamage());
//    }
//
//    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
//    public void onEntityDamageByEntity2(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage LOW: " + event.getDamage());
//    }
//
//    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
//    public void onEntityDamageByEntity3(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage NORMAL: " + event.getDamage());
//    }
//
//    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
//    public void onEntityDamageByEntity4(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage HIGH: " + event.getDamage());
//    }
//
//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    public void onEntityDamageByEntity5(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage HIGHEST: " + event.getDamage());
//    }
//
//    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//    public void onEntityDamageByEntity6(EntityDamageByEntityEvent event) {
//        Bukkit.broadcastMessage("Arrow Damage MONITOR: " + event.getDamage());
//    }

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

    private boolean handleInventory(Player player, Inventory inventory, Item item, ItemStack itemStack, InventoryType.SlotType slot) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (slot == InventoryType.SlotType.OUTSIDE) {
            // Handled by item drop event
            return true;
        }
        switch (inventory.getType()) {
            case CHEST:
            case ANVIL:
            case DISPENSER:
            case DROPPER:
            case HOPPER:
                Optional<Soulbound> soulbound = item.getAttributeDeep(Soulbound.class);
                if (soulbound.isPresent() && soulbound.get().isBound()) {
                    plugin.getMessenger().sendShortErrorMessage(player, RelMessage.SOULBOUND_MOVE);
                    return false;
                }
            case CREATIVE:
            case ENDER_CHEST:
                // UnEquip item if equipped
                if (equipManager.getPlayerEquipment().isEquipped(player, item)) {
                    equipManager.unEquip(player, item, itemStack);
                }
            case PLAYER:
                // Equip armor items
                if (slot == InventoryType.SlotType.ARMOR) {
                    equipManager.replaceEquip(player, item, itemStack);
                }
                return true;
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
        Optional<Item> item = itemManager.getItem(itemStack);
        if (item.isPresent() && !handleInventory(player, inventory, item.get(), itemStack, event.getSlotType())) {
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

    private boolean handleItemDrop(Player player, Item item, ItemStack itemStack) {
        Optional<Soulbound> soulbound = item.getAttributeDeep(Soulbound.class);
        if (soulbound.isPresent() && soulbound.get().isBound()) {
            return false;
        } else {
            EquipmentManager equipManager = plugin.getEquipmentManager();
            if (equipManager.isEquipped(player, item)) {
                equipManager.unEquip(player, item, itemStack);
            }
            return true;
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        ItemStack itemStack = event.getItemDrop().getItemStack();
        Optional<Item> itemOptional = itemManager.getItem(itemStack);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            if (!handleItemDrop(event.getPlayer(), item, itemStack)) {
                Messenger messenger = plugin.getMessenger();
                Player player = event.getPlayer();

                // Handle soulbound attribute
                UUID itemId = item.getId();
                if (soulbound.containsKey(itemId)) {
                    long lastDrop = soulbound.get(itemId);
                    if (System.currentTimeMillis() - lastDrop < 1000) {
                        // Destroy item
                        event.getItemDrop().remove();

                        // Make sure to unequip item
                        EquipmentManager equipManager = plugin.getEquipmentManager();
                        if (equipManager.isEquipped(player, item)) {
                            equipManager.unEquip(player, item, itemStack);
                        }

                        messenger.sendShortMessage(player, RelMessage.SOULBOUND_DESTROY);
                        return;
                    }
                }
                soulbound.put(itemId, System.currentTimeMillis());
                messenger.sendShortErrorMessage(player, RelMessage.SOULBOUND_DROP);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        Player player = event.getEntity();
        List<Item> items = new ArrayList<>();
        Iterator<ItemStack> drops = event.getDrops().iterator();
        while (drops.hasNext()) {
            ItemStack itemStack = drops.next();
            Optional<Item> item = itemManager.getItem(itemStack);
            if (item.isPresent() && !handleItemDrop(player, item.get(), itemStack)) {
                items.add(item.get());
                drops.remove();
            }
        }
        deathItems.put(player.getUniqueId(), items);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (deathItems.containsKey(playerId)) {
            ItemManager itemManager = plugin.getItemManager();
            List<Item> items = deathItems.get(playerId);
            Inventory inventory = player.getInventory();
            for (Item item : items) {
                inventory.addItem(item.getItem());
            }
            deathItems.remove(playerId);
        }
    }
    */
}
