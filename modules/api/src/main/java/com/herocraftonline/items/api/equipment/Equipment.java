/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.UUID;

public interface Equipment {

    LivingEntity getHolder();

    UUID getHolderId();

    boolean hasSlot(String name);

    boolean hasSlot(int index);

    boolean hasSlot(ItemType type);

    Slot getSlot(String name);

    Slot getSlot(int index);

    Collection<Slot> getSlots(ItemType type);

    Collection<Slot> getSlots();

    boolean isSlotOpen(String name);

    boolean isSlotOpen(ItemType type);

    boolean isEquipped(Item item);

    boolean equip(Item item, ItemStack itemStack);

    boolean equip(Item item, ItemStack itemStack, Slot slot);

    boolean replaceEquip(Item item, ItemStack itemStack);

    boolean unEquip(Item item, ItemStack itemStack);

    void unEquipAll();

    void load();

    void save();

    class Slot {

        private SlotType type;
        private UUID itemId;

        public Slot(SlotType type) {
            this.type = type;
        }

        public SlotType getType() {
            return type;
        }

        public boolean isOpen() {
            return itemId == null;
        }

        public boolean hasItem() {
            return !isOpen();
        }

        public UUID getItemId() {
            return itemId;
        }

        public void setItemId(UUID itemId) {
            this.itemId = itemId;
        }

    }

}
