package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseEquipment {

    private final Map<String, Slot> slots;

    public BaseEquipment() {
        this.slots = new HashMap<>();
    }

    public boolean hasSlot(String name) {
        return slots.containsKey(name.toLowerCase());
    }

    public Slot getSlot(String name) {
        return slots.get(name.toLowerCase());
    }

    public Collection<? extends Slot> getSlots() {
        return Collections.unmodifiableCollection(slots.values());
    }

    public Collection<? extends Slot> getSlotsWithItems() {
        return getSlots().stream()
                .filter(Slot::hasItem)
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getSlotsWithoutItems() {
        return getSlots().stream()
                .filter(slot -> !slot.hasItem())
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getSlotsForType(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canContainType(itemType))
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getSlotsForItem(Item item) {
        return getSlots().stream()
                .filter(slot -> slot.canContainItem(item))
                .collect(Collectors.toList());
    }

    public boolean isEquipped(Item item) {
        if (item == null) return false;
        return getSlotsForItem(item).stream()
                .anyMatch(slot -> slot.containsItem(item));
    }

    public abstract class Slot {

        private final String name;
        private final ItemType type;
        private Item item;

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

        public boolean canContainType(ItemType itemType) {
            return itemType != null && itemType.isType(getType());
        }

        public boolean canContainItem(Item item) {
            return item == null || canContainType(item.getType());
        }

        public boolean hasItem() {
            return item != null;
        }

        public boolean containsItem(Item item) {
            return hasItem() && getItem().equals(item);
        }

        public Item getItem() {
            return item;
        }

        public boolean setItem(Item item) {
            if (canContainItem(item)) {
                this.item = item;
                return true;
            }
            return false;
        }
    }
}
