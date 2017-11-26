package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Equipment<T extends LivingEntity> {

    private final ItemPlugin plugin;
    private final T holder;
    private final Map<String, Slot> slots;

    public Equipment(ItemPlugin plugin, T holder) {
        this.plugin = plugin;
        this.holder = holder;
        this.slots = new HashMap<>();
    }

    public T getHolder() {
        return holder;
    }

    boolean hasSlot(String name) {
        return name != null && slots.containsKey(name.toLowerCase());
    }

    public Slot getSlot(String name) {
        if (name == null) return null;
        return slots.get(name.toLowerCase());
    }

    public Collection<Slot> getSlots() {
        return Collections.unmodifiableCollection(slots.values());
    }

    protected Stream<Slot> getSlotStream() {
        return slots.values().stream();
    }

    public Collection<Slot> getSlotsForType(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlotStream()
                .filter(slot -> slot.canContainType(itemType))
                .collect(Collectors.toList());
    }

    public Collection<Slot> getSlotsForItem(Item item) {
        if (item == null) return Collections.emptyList();
        return getSlotStream()
                .filter(slot -> slot.canContainItem(item))
                .collect(Collectors.toList());
    }

    public Collection<Slot> getFilledSlots() {
        return getSlotStream()
                .filter(Slot::hasItem)
                .collect(Collectors.toList());
    }

    public Collection<Slot> getFilledSlotsForType(ItemType itemType) {
        return getSlotStream()
                .filter(slot -> slot.hasItem() && slot.canContainType(itemType))
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getFilledSlotsForItem(Item item) {
        return getSlotStream()
                .filter(slot -> slot.hasItem() && slot.canContainItem(item))
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getEmptySlots() {
        return getSlotStream()
                .filter(slot -> !slot.hasItem())
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getEmptySlotsForType(ItemType itemType) {
        return getSlotStream()
                .filter(slot -> !slot.hasItem() && slot.canContainType(itemType))
                .collect(Collectors.toList());
    }

    public Collection<? extends Slot> getEmptySlotsForItem(Item item) {
        return getSlotStream()
                .filter(slot -> !slot.hasItem() && slot.canContainItem(item))
                .collect(Collectors.toList());
    }

    public boolean isEquipped(Item item) {
        return getSlotStream()
                .anyMatch(slot -> slot.containsItem(item));
    }

    default boolean equip(Item item) {

    }

    boolean unEquip(Item item);

    void unEquipAll();

    public final class Slot {

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
            return itemType != null && itemType.isType(type);
        }

        public boolean canContainItem(Item item) {
            return item != null && item.getType().isType(type) && item.canUse(holder);
        }

        public boolean hasItem() {
            return item != null;
        }

        public boolean containsItem(Item item) {
            return hasItem() && getItem().getId().equals(item.getId());
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

        public boolean removeItem() {
            if (hasItem()) {
                this.item = null;
                return true;
            }
            return false;
        }

        public boolean checkItem() {
            if (hasItem() && !canContainItem(item)) {
                removeItem();
                return true;
            }
            return false;
        }
    }
}
