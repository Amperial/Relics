package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEquipment<T extends LivingEntity> implements Equipment<T> {

    private final ItemPlugin plugin;
    private final Map<String, Slot> slots;

    public BaseEquipment(ItemPlugin plugin) {
        this.plugin = plugin;
        this.slots = new HashMap<>();
    }

    @Override
    public boolean hasSlot(String name) {
        return slots.containsKey(name.toLowerCase());
    }

    @Override
    public Slot getSlot(String name) {
        return slots.get(name.toLowerCase());
    }

    @Override
    public boolean hasSlotForItem(ItemType itemType) {
        return false;
    }

    @Override
    public boolean hasSlotForItem(Item item) {
        return false;
    }

    @Override
    public Collection<? extends Slot> getSlots() {
        return Collections.unmodifiableCollection(slots.values());
    }

    @Override
    public Collection<? extends Slot> getSlotsForItem(ItemType itemType) {
        return null;
    }

    @Override
    public Collection<? extends Slot> getSlotsForItem(Item item) {
        return null;
    }

    @Override
    public boolean isEquipped(Item item) {
        return false;
    }

    @Override
    public void unEquipAll() {

    }

    public abstract class Slot implements Equipment.Slot {

        private final String name;

        public Slot(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public abstract boolean canContainItem(ItemType itemType);

        @Override
        public boolean canContainItem(Item item) {
            if (item == null) return false;
            if (!canContainItem(item.getType())) return false;
        }

        @Override
        public boolean hasItem() {
            return false;
        }

        @Override
        public Item getItem() {
            return null;
        }

        @Override
        public boolean setItem(Item item) {
            return false;
        }

        @Override
        public void removeItem() {

        }
    }
}
