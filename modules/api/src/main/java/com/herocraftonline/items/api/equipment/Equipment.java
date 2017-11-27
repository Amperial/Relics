package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.events.equipment.EquipmentChangedEvent;
import com.herocraftonline.items.api.events.equipment.EquipmentSlotChangeEvent;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class Equipment {

    private final ItemPlugin plugin;
    private final LivingEntity holder;
    private final Map<String, Slot> slots;

    public Equipment(ItemPlugin plugin, LivingEntity holder) {
        this.plugin = plugin;
        this.holder = holder;
        this.slots = new HashMap<>();
    }

    public LivingEntity getHolder() {
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

    public void forEachSlot(Consumer<Slot> consumer) {
        slots.values().forEach(consumer);
    }

    public Stream<Slot> getSlotStream() {
        return slots.values().stream();
    }

    public Optional<Slot> findSlotWithItem(Item item) {
        if (item == null) return Optional.empty();
        return getSlotStream()
                .filter(slot -> slot.containsItem(item))
                .findFirst();
    }

    public boolean isEquipped(Item item) {
        return item != null && getSlotStream().anyMatch(slot -> slot.containsItem(item));
    }

    public boolean equip(Item item) {
        if (isEquipped(item)) return false;
        Optional<Slot> slotOptional = getSlotStream()
                .filter(slot -> !slot.hasItem() && slot.canContainItem(item))
                .findFirst();
        return slotOptional.isPresent() && slotOptional.get().setItem(item);
    }

    public boolean unEquip(Item item) {
        Optional<Slot> slotOptional = findSlotWithItem(item);
        return slotOptional.isPresent() && slotOptional.get().removeItem();
    }

    public boolean unEquipAll() {
        boolean changed = false;
        for (Slot slot : slots.values()) {
            if (slot.removeItemInternal()) {
                changed = true;
            }
        }
        if (changed) {
            EquipmentChangedEvent event = new EquipmentChangedEvent(Equipment.this);
            Bukkit.getPluginManager().callEvent(event);
            return true;
        }
        return false;
    }

    public final class Slot {

        private final String name;
        private final ItemType type;
        private Item item;

        private Slot(String name, ItemType type) {
            this.name = name;
            this.type = type;
        }

        private Slot(String name, ItemType type, Item item) {
            this(name, type);
            this.item = item;
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
            if (setItemInternal(item)) {
                EquipmentChangedEvent event = new EquipmentChangedEvent(Equipment.this);
                Bukkit.getPluginManager().callEvent(event);
                return true;
            }
            return false;
        }

        private boolean setItemInternal(Item item) {
            if (this.item.getId().equals(item.getId())) return false;
            if (canContainItem(item)) {
                EquipmentSlotChangeEvent event = new EquipmentSlotChangeEvent(Equipment.this, this, item);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    this.item = item;
                    return true;
                }
            }
            return false;
        }

        public boolean removeItem() {
            if (removeItemInternal()) {
                EquipmentChangedEvent event = new EquipmentChangedEvent(Equipment.this);
                Bukkit.getPluginManager().callEvent(event);
                return true;
            }
            return false;
        }

        private boolean removeItemInternal() {
            if (hasItem()) {
                EquipmentSlotChangeEvent event = new EquipmentSlotChangeEvent(Equipment.this, this, null);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    item = null;
                    return true;
                }
            }
            return false;
        }
    }
}
