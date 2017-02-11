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
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
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

public class ItemListener implements Listener {

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

    private boolean handleItemUse(Player player, ItemStack itemStack) {
        Optional<Item> item = plugin.getItemManager().getItem(itemStack);
        return !item.isPresent() || handleItemUse(player, item.get());
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
        Optional<Item> item = plugin.getItemManager().getItem(event.getItem());
        if (item.isPresent()) {
            if (handleItemUse(event.getPlayer(), item.get())) {
                item.get().onClick(event, true);
            } else {
                item.get().onClick(event, false);
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
            Optional<Item> item = itemManager.getItem(itemStack);
            if (item.isPresent()) {
                if (damager instanceof Player && !handleItemUse((Player) damager, item.get())) {
                    event.setCancelled(true);
                } else {
                    item.get().forEachDeep(attribute -> {
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
                    plugin.getMessenger().sendShortErrorMessage(player, AIMessage.SOULBOUND_MOVE);
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
        Optional<Item> item = itemManager.getItem(itemStack);
        if (item.isPresent() && !handleInventory(player, inventory, item.get(), event.getSlotType())) {
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
        Optional<Soulbound> soulbound = item.getAttributeDeep(Soulbound.class);
        if (soulbound.isPresent() && soulbound.get().isBound()) {
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
        Optional<Item> itemOptional = itemManager.getItem(itemStack);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            if (!handleItemDrop(event.getPlayer(), item)) {
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
                            equipManager.unEquip(player, item);
                        }

                        messenger.sendShortMessage(player, AIMessage.SOULBOUND_DESTROY);
                        return;
                    }
                }
                soulbound.put(itemId, System.currentTimeMillis());
                messenger.sendShortErrorMessage(player, AIMessage.SOULBOUND_DROP);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        ItemManager itemManager = plugin.getItemManager();
        Player player = event.getEntity();
        List<Item> items = new ArrayList<>();
        Iterator<ItemStack> drops = event.getDrops().iterator();
        while (drops.hasNext()) {
            ItemStack itemStack = drops.next();
            Optional<Item> item = itemManager.getItem(itemStack);
            if (item.isPresent() && !handleItemDrop(player, item.get())) {
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

}
