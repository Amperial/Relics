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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerEquipment implements Equipment {

    private final ItemPlugin plugin;
    private final UUID playerId;
    private final Map<String, Slot> slotsByName;
    private final Map<ItemType, Collection<Slot>> slotsByType;

    public PlayerEquipment(ItemPlugin plugin, Player player) {
        this.plugin = plugin;
        this.playerId = player.getUniqueId();
        this.slotsByName = new HashMap<>();
        this.slotsByType = new HashMap<>();

        load();
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public boolean hasSlot(String name) {
        return slotsByName.containsKey(name);
    }

    @Override
    public boolean hasSlot(ItemType type) {
        return slotsByType.containsKey(type) || (type.hasParent() && hasSlot(type.getParent()));
    }

    @Override
    public Slot getSlot(String name) {
        return slotsByName.get(name);
    }

    @Override
    public Collection<Slot> getSlots(ItemType type) {
        return slotsByType.containsKey(type) ? slotsByType.get(type) : type.hasParent() ? getSlots(type.getParent()) : Collections.emptySet();
    }

    @Override
    public Collection<Slot> getSlots() {
        return slotsByName.values();
    }

    @Override
    public boolean isSlotOpen(String name) {
        checkSlots();
        return hasSlot(name) && getSlot(name).isOpen();
    }

    @Override
    public boolean isSlotOpen(ItemType type) {
        checkSlots();
        for (Slot slot : getSlots(type)) {
            if (slot.isOpen()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEquipped(Item item) {
        for (Slot slot : getSlots()) {
            if (slot.hasItem() && slot.getItemId().equals(item.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equip(Item item, ItemStack itemStack) {
        checkSlots();
        for (Slot slot : getSlots(item.getType())) {
            if (slot.isOpen()) {
                slot.setItemId(item.getId());
                Player player = getPlayer();
                if (item.onEquip(player)) {
                    updateItem(player, item, itemStack);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equip(Item item, ItemStack itemStack, Slot slot) {
        checkSlot(slot);
        if (slot.isOpen()) {
            slot.setItemId(item.getId());
            Player player = getPlayer();
            if (item.onEquip(player)) {
                updateItem(player, item, itemStack);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEquip(Item item, ItemStack itemStack) {
        checkSlots();
        if (!equip(item, itemStack)) {
            ItemManager itemManager = plugin.getItemManager();
            for (Slot slot : getSlots(item.getType())) {
                Player player = getPlayer();
                Optional<ItemStack> equippedStack = itemManager.findItemStack(player, slot.getItemId());
                slot.setItemId(null);
                if (equippedStack.isPresent()) {
                    Optional<Item> equippedItem = itemManager.getItem(equippedStack.get());
                    if (equippedItem.isPresent() && equippedItem.get().onUnEquip(player)) {
                        updateItem(player, equippedItem.get(), equippedStack.get());
                    }
                }
                slot.setItemId(item.getId());
                if (item.onEquip(player)) {
                    updateItem(player, item, itemStack);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unEquip(Item item, ItemStack itemStack) {
        checkSlots();
        for (Slot slot : getSlots()) {
            if (slot.hasItem() && slot.getItemId().equals(item.getId())) {
                slot.setItemId(null);
                Player player = getPlayer();
                if (item.onUnEquip(player)) {
                    updateItem(player, item, itemStack);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void unEquipAll() {
        ItemManager itemManager = plugin.getItemManager();
        Player player = getPlayer();
        for (Slot slot : getSlots()) {
            if (slot.hasItem()) {
                Optional<ItemStack> itemStack = itemManager.findItemStack(player, slot.getItemId());
                slot.setItemId(null);
                if (itemStack.isPresent()) {
                    Optional<Item> item = itemManager.getItem(itemStack.get());
                    if (item.isPresent()) {
                        if (item.get().onUnEquip(player)) {
                            updateItem(player, item.get(), itemStack.get());
                        }
                    }
                }
            }
        }
    }

    private void addSlot(Slot slot) {
        slotsByName.put(slot.getName(), slot);
        ItemType type = slot.getType();
        if (!slotsByType.containsKey(type)) {
            slotsByType.put(type, new HashSet<>());
        }
        slotsByType.get(type).add(slot);
    }

    private void checkSlot(Slot slot) {
        if (slot.hasItem() && plugin.getItemManager().findItem(getPlayer(), slot.getItemId()).isPresent()) {
            slot.setItemId(null);
        }
    }

    private void checkSlots() {
        ItemManager itemManager = plugin.getItemManager();
        Player player = getPlayer();
        for (Slot slot : getSlots()) {
            if (slot.hasItem()) {
                if (itemManager.findItem(player, slot.getItemId()) == null) {
                    slot.setItemId(null);
                }
            }
        }
    }

    private void updateItem(Player player, Item item, ItemStack itemStack) {
        ItemManager itemManager = plugin.getItemManager();
        PlayerInventory inventory = player.getInventory();

        // Find location of the item stack in player's inventory
        int slot = -1;
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            Optional<Item> invItem = itemManager.getItem(contents[i]);
            if (invItem.isPresent() && invItem.get().getId().equals(item.getId())) {
                slot = i;
                break;
            }
        }

        // Update item stack
        itemStack = item.updateItem(itemStack).orElse(itemStack);
        if (slot >= 0) {
            // Entirely replace item stack in inventory if possible
            inventory.setItem(slot, itemStack);
        }
    }

    @Override
    public void load() {
        Player player = getPlayer();

        // Clear existing slots
        slotsByName.clear();
        slotsByType.clear();

        // Get player config or default equipment
        ConfigManager configManager = plugin.getConfigManager();
        ConfigAccessor playerConfig = configManager.getPlayerConfigAccessor(player);
        ConfigurationSection slots = playerConfig.getConfig();
        if (playerConfig.exists() && slots.isConfigurationSection("slots")) {
            slots = slots.getConfigurationSection("slots");
        } else {
            slots = configManager.getConfig(DefaultConfig.EQUIPMENT).getConfigurationSection("slots");
        }

        // Load slots
        ItemManager itemManager = plugin.getItemManager();
        for (String name : slots.getKeys(false)) {
            ItemType type = itemManager.getItemType(slots.getString(name + ".type"));

            Slot slot = new Slot(name, type);
            if (slots.isString(name + ".item-id")) {
                UUID itemId = UUID.fromString(slots.getString(name + ".item-id"));
                Optional<Item> item = itemManager.findItem(player, itemId);
                if (item.isPresent()) {
                    slot.setItemId(itemId);
                    item.get().onEquip(player);
                }
            }
            addSlot(slot);
        }
    }

    @Override
    public void save() {
        // Get player config
        ConfigAccessor playerConfig = plugin.getConfigManager().getPlayerConfigAccessor(getPlayer());
        FileConfiguration config = playerConfig.getConfig();

        // Save slots
        ConfigurationSection slots;
        if (config.isConfigurationSection("slots")) {
            slots = config.getConfigurationSection("slots");
        } else {
            slots = config.createSection("slots");
        }
        for (Slot slot : getSlots()) {
            String name = slot.getName();
            slots.set(name + ".type", slot.getType().getName());
            if (slot.hasItem()) {
                slots.set(name + ".item-id", slot.getItemId().toString());
            }
        }

        // Save player config
        playerConfig.saveConfig();
    }

}
