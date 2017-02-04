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

import java.util.Collection;
import java.util.UUID;

public interface Equipment {

    Player getPlayer();

    UUID getPlayerId();

    boolean hasSlot(String name);

    boolean hasSlot(ItemType type);

    Slot getSlot(String name);

    Collection<Slot> getSlots(ItemType type);

    boolean isSlotOpen(String name);

    boolean isSlotOpen(ItemType type);

    boolean canEquip(Item item);

    boolean canEquip(Item item, Slot slot);

    boolean equip(Item item);

    boolean equip(Item item, Slot slot);

    Collection<Slot> getSlots();

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
