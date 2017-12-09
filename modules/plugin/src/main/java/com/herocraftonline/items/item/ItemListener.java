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
package com.herocraftonline.items.item;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.equipment.EquipmentManager;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.attributes.Durability;
import com.herocraftonline.items.api.item.attribute.attributes.Soulbound;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggers.AttackEntity;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggers.PlayerInteract;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import com.herocraftonline.items.api.storage.config.ConfigAccessor;
import com.herocraftonline.items.item.attributes.triggers.sources.event.EntityDamageByEntitySource;
import com.herocraftonline.items.item.attributes.triggers.sources.event.PlayerInteractSource;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemListener implements Listener {

    private final ItemPlugin plugin;
    private final Random random;
    private final Map<UUID, Long> soulbound;

    public ItemListener(ItemPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
        this.soulbound = new HashMap<>();

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean handleItemUse(Player player, ItemStack itemStack) {
        Optional<Item> item = plugin.getItemManager().getItem(itemStack);
        return !item.isPresent() || handleItemUse(player, item.get(), itemStack);
    }

    private boolean handleItemUse(Player player, Item item, ItemStack itemStack) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (equipManager.isEquipped(player, item)) {
            return true;
        } else {
            equipManager.equip(player, item, itemStack);
            return false;
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        Optional<Item> itemOptional = plugin.getItemManager().getItem(itemStack);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            // TODO: Use refactored equipment system to handle this better...
            if (handleItemUse(event.getPlayer(), item, itemStack)) {
                // item.onClick(event, item);
            } else {
                // item.onClick(event, item);
                event.setCancelled(true);
            }
            TriggerSource source = new PlayerInteractSource(item, event);
            TriggerResult result = item.getAttributesDeep(PlayerInteract.class).stream()
                    .filter(attribute -> attribute.canTrigger(source))
                    .map(attribute -> attribute.onTrigger(source))
                    .reduce(TriggerResult.COMBINED).orElse(TriggerResult.NOT_TRIGGERED);
            // TODO: Handle trigger results
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!handleItemUse(player, player.getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        } else if (!handleItemUse(player, player.getInventory().getItemInOffHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        LivingEntity damager = getLivingDamager(event);
        if (damager != null) {
            ItemManager itemManager = plugin.getItemManager();
            ItemStack itemStack = damager.getEquipment().getItemInMainHand();
            Optional<Item> itemOptional = itemManager.getItem(itemStack);
            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();
                if (damager instanceof Player && !handleItemUse((Player) damager, item, itemStack)) {
                    event.setCancelled(true);
                    return;
                }
                Optional<Durability> durability = item.getAttribute(Durability.class);
                if (durability.isPresent() && !durability.get().isUsable()) {
                    if (damager instanceof Player) {
                        plugin.getEquipmentManager().unEquip((Player) damager, item, itemStack);
                    }
                    event.setCancelled(true);
                    return;
                }
                TriggerSource source = new EntityDamageByEntitySource(item, event);
                TriggerResult result = item.getAttributesDeep(AttackEntity.class).stream()
                        .filter(attribute -> attribute.canTrigger(source))
                        .map(attribute -> attribute.onTrigger(source))
                        .reduce(TriggerResult.COMBINED).orElse(TriggerResult.NOT_TRIGGERED);
                // TODO: Handle trigger results
                if (durability.isPresent() && durability.get().damage(1)) {
                    item.updateItem(itemStack).ifPresent(updatedItem -> damager.getEquipment().setItemInMainHand(updatedItem));
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

    private boolean handleInventory(Player player, Inventory inventory, Item item, ItemStack itemStack, InventoryType.SlotType slot) {
        EquipmentManager equipManager = plugin.getEquipmentManager();
        if (slot == InventoryType.SlotType.OUTSIDE) {
            // Handled by item drop event
            return true;
        }
        switch (inventory.getType()) {
            case CHEST:
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
                if (equipManager.isEquipped(player, item)) {
                    equipManager.unEquip(player, item, itemStack);
                }
            case PLAYER:
                // Equip armor items
                if (slot == InventoryType.SlotType.ARMOR) {
                    equipManager.replaceEquip(player, item, itemStack);
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

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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

        // Attempt to handle dropping all items, remove all items that can't be dropped from drops
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

        // Add items that weren't dropped to player config to restore later
        if (!items.isEmpty()) {
            ConfigAccessor playerAccessor = plugin.getConfigManager().getPlayerConfigAccessor(player);
            ConfigurationSection playerConfig = playerAccessor.getConfig();
            List<String> restoreList = playerConfig.getStringList("give-items");
            for (Item item : items) {
                restoreList.add(ItemUtil.serialize(item));
            }
            playerConfig.set("give-items", restoreList);
            playerAccessor.saveConfig();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        // Restore any items in give-items section of player config
        ConfigAccessor playerAccessor = plugin.getConfigManager().getPlayerConfigAccessor(player);
        ConfigurationSection playerConfig = playerAccessor.getConfig();
        List<String> restoreList = playerConfig.getStringList("give-items");
        if (!restoreList.isEmpty()) {
            ItemStack[] restore = restoreList.stream()
                    .map(ItemUtil::deserialize).filter(Optional::isPresent).map(Optional::get).toArray(ItemStack[]::new);
            restoreList = player.getInventory().addItem(restore).values().stream()
                    .map(ItemUtil::serialize).collect(Collectors.toList());
            playerConfig.set("give-items", restoreList);
            playerAccessor.saveConfig();
        }
    }

}
