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
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public final class PlayerEquipment implements com.herocraftonline.items.api.equipment.PlayerEquipment {

    private final ItemPlugin plugin;
    private final Map<String, Slot> slotsByName;
    private final Map<Integer, Slot> slotsByInventoryIndex;

    public PlayerEquipment(ItemPlugin plugin) {
        this.plugin = plugin;
        this.slotsByName = new HashMap<>();
        this.slotsByInventoryIndex = new HashMap<>();
        loadSlots();
    }

    @Override
    public boolean hasSlot(Player player, String name) {
        return slotsByName.containsKey(name.toLowerCase());
    }

    @Override
    public boolean hasSlot(Player player, int inventoryIndex) {
        return slotsByInventoryIndex.containsKey(inventoryIndex);
    }

    @Override
    public Slot getSlot(Player player, String name) {
        return slotsByName.get(name.toLowerCase());
    }

    @Override
    public Slot getSlot(Player player, int inventoryIndex) {
        return slotsByInventoryIndex.get(inventoryIndex);
    }

    @Override
    public Collection<Slot> getSlots(Player player) {
        return Collections.unmodifiableCollection(slotsByName.values());
    }

    private void loadSlots() {
        FileConfiguration config = plugin.getConfigManager().getConfig(DefaultConfig.EQUIPMENT);
        List<Map<?, ?>> slotMaps = config.getMapList("slots");
        for (Map<?, ?> slotMap : slotMaps) {

            String name = null;
            int inventoryIndex = -1;
            ItemType itemType = null;

            Object nameObj = slotMap.get("name");
            if (nameObj instanceof String) {
                name = (String) nameObj;
            }
            else {
                continue;
            }

            Object inventoryIndexObj = slotMap.get("inventory-index");
            if (inventoryIndexObj instanceof Integer) {
                inventoryIndex = (Integer) inventoryIndexObj;
                if (inventoryIndex < 0 || inventoryIndex > 40) {
                    continue;
                }
            }
            else {
                continue;
            }

            Object itemTypeObj = slotMap.get("item-type");
            if (itemTypeObj instanceof String) {
                String itemTypeName = (String) itemTypeObj;
                if (plugin.getItemManager().hasItemType(itemTypeName)) {
                    itemType = plugin.getItemManager().getItemType(itemTypeName);
                }
                else {
                    continue;
                }
            } else {
                continue;
            }

            if (slotsByName.containsKey(name.toLowerCase()) || slotsByInventoryIndex.containsKey(inventoryIndex)) {
                continue;
            }

            Slot slot = new Slot(name, inventoryIndex, itemType);
            slotsByName.put(name.toLowerCase(), slot);
            slotsByInventoryIndex.put(inventoryIndex, slot);
        }
    }

    public class Slot implements com.herocraftonline.items.api.equipment.PlayerEquipment.Slot {

        private final String name;
        private final int inventoryIndex;
        private final ItemType itemType;

        public Slot(String name, int inventoryIndex, ItemType itemType) {
            this.name = name;
            this.inventoryIndex = inventoryIndex;
            this.itemType = itemType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getInventoryIndex() {
            return inventoryIndex;
        }

        @Override
        public boolean canHoldItem(ItemType itemType) {
            return itemType != null && itemType.isType(this.itemType);
        }

        @Override
        public boolean hasItem(Player player) {
            return PlayerEquipment.this.plugin.getItemManager().isItem(player.getInventory().getItem(inventoryIndex));
        }

        @Override
        public Item getItem(Player player) {
            return PlayerEquipment.this.plugin.getItemManager().getItem(player.getInventory().getItem(inventoryIndex)).orElse(null);
        }
    }
}
