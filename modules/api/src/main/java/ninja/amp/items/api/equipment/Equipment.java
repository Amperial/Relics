/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.equipment;

import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.UUID;

public interface Equipment {

    Player getPlayer();

    UUID getPlayerId();

    boolean hasSlot(String name);

    boolean hasSlot(ItemType type);

    Slot getSlot(String name);

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

        private final String name;
        private final ItemType type;
        private UUID itemId;

        public Slot(String name, ItemType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public ItemType getType() {
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
