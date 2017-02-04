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
package ninja.amp.items.equipment;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.config.ConfigAccessor;
import ninja.amp.items.api.config.DefaultConfig;
import ninja.amp.items.api.config.PlayerConfig;
import ninja.amp.items.api.equipment.Equipment;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        return slotsByType.containsKey(type);
    }

    @Override
    public Slot getSlot(String name) {
        return slotsByName.get(name);
    }

    @Override
    public Collection<Slot> getSlots(ItemType type) {
        return slotsByType.containsKey(type) ? slotsByType.get(type) : Collections.emptySet();
    }

    @Override
    public boolean isSlotOpen(String name) {
        return hasSlot(name) && getSlot(name).isOpen();
    }

    @Override
    public boolean isSlotOpen(ItemType type) {
        for (Slot slot : getSlots(type)) {
            if (slot.isOpen()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canEquip(Item item) {
        return isSlotOpen(item.getType());
    }

    @Override
    public boolean canEquip(Item item, Slot slot) {
        return slot.isOpen() && slot.getType().equals(item.getType());
    }

    @Override
    public boolean equip(Item item) {
        for (Slot slot : getSlots(item.getType())) {
            if (slot.isOpen()) {
                slot.setItemId(item.getId());
                item.onEquip(getPlayer());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equip(Item item, Slot slot) {
        if (slot.isOpen()) {
            slot.setItemId(item.getId());
            item.onEquip(getPlayer());
            return true;
        }
        return false;
    }

    @Override
    public Collection<Slot> getSlots() {
        return slotsByName.values();
    }

    private void addSlot(Slot slot) {
        slotsByName.put(slot.getName(), slot);
        ItemType type = slot.getType();
        if (!slotsByType.containsKey(type)) {
            slotsByType.put(type, new HashSet<>());
        }
        slotsByType.get(type).add(slot);
    }

    @Override
    public void load() {
        // Get player config or default equipment
        ConfigAccessor playerConfig = new ConfigAccessor(plugin, new PlayerConfig(getPlayer()));
        FileConfiguration config;
        if (playerConfig.exists()) {
            config = playerConfig.getConfig();
        } else {
            config = plugin.getConfigManager().getConfig(DefaultConfig.EQUIPMENT);
        }

        // Clear existing slots
        slotsByName.clear();
        slotsByType.clear();

        // Load slots
        if (config.isConfigurationSection("slots")) {
            ItemManager itemManager = plugin.getItemManager();
            ConfigurationSection slots = config.getConfigurationSection("slots");
            for (String name : slots.getKeys(false)) {
                String type = slots.getString(name + ".type");
                if (itemManager.hasItemType(type)) {
                    Slot slot = new Slot(name, itemManager.getItemType(type));
                    if (slots.isString(name + ".item-id")) {
                        slot.setItemId(UUID.fromString(slots.getString(name + ".item-id")));
                    }
                    addSlot(slot);
                }
            }
        }
    }

    @Override
    public void save() {
        // Get player config
        ConfigAccessor playerConfig = new ConfigAccessor(plugin, new PlayerConfig(getPlayer()));
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
