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
package com.herocraftonline.items.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.equipment.Equipment;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.ItemType;
import com.herocraftonline.items.api.storage.config.ConfigAccessor;
import com.herocraftonline.items.api.storage.config.ConfigManager;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public final class PlayerEquipment {

//    private final ItemPlugin plugin;
//    private final Map<String, Slot> slotsByName;
//    private final Map<Integer, Slot> slotsByInventoryIndex;
//
//    public PlayerEquipment(ItemPlugin plugin) {
//        this.plugin = plugin;
//        this.slotsByName = new HashMap<>();
//        this.slotsByInventoryIndex = new HashMap<>();
//        loadSlots();
//    }
//
//    @Override
//    public boolean hasSlot(Player player, String name) {
//        return slotsByName.containsKey(name.toLowerCase());
//    }
//
//    @Override
//    public boolean hasSlot(Player player, int inventoryIndex) {
//        return slotsByInventoryIndex.containsKey(inventoryIndex);
//    }
//
//    @Override
//    public Slot getSlot(Player player, String name) {
//        return slotsByName.get(name.toLowerCase());
//    }
//
//    @Override
//    public Slot getSlot(Player player, int inventoryIndex) {
//        return slotsByInventoryIndex.get(inventoryIndex);
//    }
//
//    @Override
//    public Collection<Slot> getSlots(Player player) {
//        return Collections.unmodifiableCollection(slotsByName.values());
//    }
//
//    private void loadSlots() {
//        FileConfiguration config = plugin.getConfigManager().getConfig(DefaultConfig.EQUIPMENT);
//        List<Map<?, ?>> slotMaps = config.getMapList("slots");
//        for (Map<?, ?> slotMap : slotMaps) {
//
//            String name = null;
//            int inventoryIndex = -1;
//            ItemType itemType = null;
//
//            Object nameObj = slotMap.get("name");
//            if (nameObj instanceof String) {
//                name = (String) nameObj;
//            }
//            else {
//                continue;
//            }
//
//            Object inventoryIndexObj = slotMap.get("inventory-index");
//            if (inventoryIndexObj instanceof Integer) {
//                inventoryIndex = (Integer) inventoryIndexObj;
//                if (inventoryIndex < 0 || inventoryIndex > 40) {
//                    continue;
//                }
//            }
//            else {
//                continue;
//            }
//
//            Object itemTypeObj = slotMap.get("item-type");
//            if (itemTypeObj instanceof String) {
//                String itemTypeName = (String) itemTypeObj;
//                if (plugin.getItemManager().hasItemType(itemTypeName)) {
//                    itemType = plugin.getItemManager().getItemType(itemTypeName);
//                }
//                else {
//                    continue;
//                }
//            } else {
//                continue;
//            }
//
//            if (slotsByName.containsKey(name.toLowerCase()) || slotsByInventoryIndex.containsKey(inventoryIndex)) {
//                continue;
//            }
//
//            Slot slot = new Slot(name, inventoryIndex, itemType);
//            slotsByName.put(name.toLowerCase(), slot);
//            slotsByInventoryIndex.put(inventoryIndex, slot);
//        }
//    }
//
//    public class Slot implements com.herocraftonline.items.api.equipment.PlayerEquipment.Slot {
//
//        private final String name;
//        private final int inventoryIndex;
//        private final ItemType itemType;
//
//        public Slot(String name, int inventoryIndex, ItemType itemType) {
//            this.name = name;
//            this.inventoryIndex = inventoryIndex;
//            this.itemType = itemType;
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public int getInventoryIndex() {
//            return inventoryIndex;
//        }
//
//        @Override
//        public boolean canHoldItem(ItemType itemType) {
//            return itemType != null && itemType.isType(this.itemType);
//        }
//
//        @Override
//        public boolean hasItem(Player player) {
//            return PlayerEquipment.this.plugin.getItemManager().isItem(player.getInventory().getItem(inventoryIndex));
//        }
//
//        @Override
//        public Item getItem(Player player) {
//            return PlayerEquipment.this.plugin.getItemManager().getItem(player.getInventory().getItem(inventoryIndex)).orElse(null);
//        }
//    }

//    private final ItemPlugin plugin;
//    private final UUID playerId;
//    private final Map<String, Equipment.Slot> slotsByName;
//    private final Map<ItemType, Collection<Equipment.Slot>> slotsByType;
//
//    public PlayerEquipment(ItemPlugin plugin, Player player) {
//        this.plugin = plugin;
//        this.playerId = player.getUniqueId();
//        this.slotsByName = new HashMap<>();
//        this.slotsByType = new HashMap<>();
//
//        load();
//    }
//
//    @Override
//    public Player getPlayer() {
//        return Bukkit.getPlayer(playerId);
//    }
//
//    @Override
//    public UUID getPlayerId() {
//        return playerId;
//    }
//
//    @Override
//    public boolean hasSlot(String name) {
//        return slotsByName.containsKey(name);
//    }
//
//    @Override
//    public boolean hasSlot(ItemType type) {
//        return slotsByType.containsKey(type);
//    }
//
//    @Override
//    public Equipment.Slot getSlot(String name) {
//        return slotsByName.get(name);
//    }
//
//    @Override
//    public Collection<Equipment.Slot> getSlots(ItemType type) {
//        return slotsByType.containsKey(type) ? slotsByType.get(type) : Collections.emptySet();
//    }
//
//    @Override
//    public Collection<Equipment.Slot> getSlots() {
//        return slotsByName.values();
//    }
//
//    @Override
//    public boolean isSlotOpen(String name) {
//        checkSlots();
//        return hasSlot(name) && getSlot(name).isOpen();
//    }
//
//    @Override
//    public boolean isSlotOpen(ItemType type) {
//        checkSlots();
//        for (Equipment.Slot slot : getSlots(type)) {
//            if (slot.isOpen()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isEquipped(Item item) {
//        for (Equipment.Slot slot : getSlots()) {
//            if (slot.hasItem() && slot.getItemId().equals(item.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean equip(Item item, ItemStack itemStack) {
//        checkSlots();
//        for (Equipment.Slot slot : getSlots(item.getType())) {
//            if (slot.isOpen()) {
//                slot.setItemId(item.getId());
//                Player player = getPlayer();
//                if (item.onEquip(player)) {
//                    updateItem(player, item, itemStack);
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean equip(Item item, ItemStack itemStack, Equipment.Slot slot) {
//        checkSlot(slot);
//        if (slot.isOpen()) {
//            slot.setItemId(item.getId());
//            Player player = getPlayer();
//            if (item.onEquip(player)) {
//                updateItem(player, item, itemStack);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean replaceEquip(Item item, ItemStack itemStack) {
//        checkSlots();
//        if (!equip(item, itemStack)) {
//            ItemManager itemManager = plugin.getItemManager();
//            for (Equipment.Slot slot : getSlots(item.getType())) {
//                Player player = getPlayer();
//                Optional<ItemStack> equippedStack = itemManager.findItemStack(player, slot.getItemId());
//                slot.setItemId(null);
//                if (equippedStack.isPresent()) {
//                    Optional<Item> equippedItem = itemManager.getItem(equippedStack.get());
//                    if (equippedItem.isPresent() && equippedItem.get().onUnEquip(player)) {
//                        updateItem(player, equippedItem.get(), equippedStack.get());
//                    }
//                }
//                slot.setItemId(item.getId());
//                if (item.onEquip(player)) {
//                    updateItem(player, item, itemStack);
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean unEquip(Item item, ItemStack itemStack) {
//        checkSlots();
//        for (Equipment.Slot slot : getSlots()) {
//            if (slot.hasItem() && slot.getItemId().equals(item.getId())) {
//                slot.setItemId(null);
//                Player player = getPlayer();
//                if (item.onUnEquip(player)) {
//                    updateItem(player, item, itemStack);
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void unEquipAll() {
//        ItemManager itemManager = plugin.getItemManager();
//        Player player = getPlayer();
//        for (Equipment.Slot slot : getSlots()) {
//            if (slot.hasItem()) {
//                Optional<ItemStack> itemStack = itemManager.findItemStack(player, slot.getItemId());
//                slot.setItemId(null);
//                if (itemStack.isPresent()) {
//                    Optional<Item> item = itemManager.getItem(itemStack.get());
//                    if (item.isPresent()) {
//                        if (item.get().onUnEquip(player)) {
//                            updateItem(player, item.get(), itemStack.get());
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void addSlot(Equipment.Slot slot) {
//        slotsByName.put(slot.getName(), slot);
//        ItemType type = slot.getType();
//        if (!slotsByType.containsKey(type)) {
//            slotsByType.put(type, new HashSet<>());
//        }
//        slotsByType.get(type).add(slot);
//    }
//
//    private void checkSlot(Equipment.Slot slot) {
//        if (slot.hasItem() && plugin.getItemManager().findItem(getPlayer(), slot.getItemId()).isPresent()) {
//            slot.setItemId(null);
//        }
//    }
//
//    private void checkSlots() {
//        ItemManager itemManager = plugin.getItemManager();
//        Player player = getPlayer();
//        for (Equipment.Slot slot : getSlots()) {
//            if (slot.hasItem()) {
//                if (itemManager.findItem(player, slot.getItemId()) == null) {
//                    slot.setItemId(null);
//                }
//            }
//        }
//    }
//
//    private void updateItem(Player player, Item item, ItemStack itemStack) {
//        ItemManager itemManager = plugin.getItemManager();
//        PlayerInventory inventory = player.getInventory();
//
//        // Find location of the item stack in player's inventory
//        int slot = -1;
//        ItemStack[] contents = inventory.getContents();
//        for (int i = 0; i < contents.length; i++) {
//            Optional<Item> invItem = itemManager.getItem(contents[i]);
//            if (invItem.isPresent() && invItem.get().getId().equals(item.getId())) {
//                slot = i;
//                break;
//            }
//        }
//
//        // Update item stack
//        itemStack = item.updateItem(itemStack);
//        if (slot >= 0) {
//            // Entirely replace item stack in inventory if possible
//            inventory.setItem(slot, itemStack);
//        }
//    }
//
//    @Override
//    public void load() {
//        Player player = getPlayer();
//
//        // Get player config or default equipment
//        ConfigManager configManager = plugin.getConfigManager();
//        ConfigAccessor playerConfig = configManager.getPlayerConfigAccessor(player);
//        FileConfiguration config;
//        if (playerConfig.exists()) {
//            config = playerConfig.getConfig();
//        } else {
//            config = configManager.getConfig(DefaultConfig.EQUIPMENT);
//        }
//
//        // Clear existing slots
//        slotsByName.clear();
//        slotsByType.clear();
//
//        // Load slots
//        if (config.isConfigurationSection("slots")) {
//            ItemManager itemManager = plugin.getItemManager();
//            ConfigurationSection slots = config.getConfigurationSection("slots");
//            for (String name : slots.getKeys(false)) {
//                Equipment.Slot slot = new Equipment.Slot(name, new ItemType(slots.getString(name + ".type")));
//                if (slots.isString(name + ".item-id")) {
//                    UUID itemId = UUID.fromString(slots.getString(name + ".item-id"));
//                    Optional<Item> item = itemManager.findItem(player, itemId);
//                    if (item.isPresent()) {
//                        slot.setItemId(itemId);
//                        item.get().onEquip(player);
//                    }
//                }
//                addSlot(slot);
//            }
//        }
//    }
//
//    @Override
//    public void save() {
//        // Get player config
//        ConfigAccessor playerConfig = plugin.getConfigManager().getPlayerConfigAccessor(getPlayer());
//        FileConfiguration config = playerConfig.getConfig();
//
//        // Save slots
//        ConfigurationSection slots;
//        if (config.isConfigurationSection("slots")) {
//            slots = config.getConfigurationSection("slots");
//        } else {
//            slots = config.createSection("slots");
//        }
//        for (Equipment.Slot slot : getSlots()) {
//            String name = slot.getName();
//            slots.set(name + ".type", slot.getType().getName());
//            if (slot.hasItem()) {
//                slots.set(name + ".item-id", slot.getItemId().toString());
//            }
//        }
//
//        // Save player config
//        playerConfig.saveConfig();
//    }

}
