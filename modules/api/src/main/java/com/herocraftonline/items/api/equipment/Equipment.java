package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface Equipment<T> {

    boolean hasSlot(String name);

    default boolean hasSlot(T equipmentHolder, String name) {
        return hasSlot(name);
    }

    default boolean hasSlotForItem(ItemType itemType) {
        return itemType != null && getSlots().stream()
                .anyMatch(slot -> slot.canHoldItem(itemType));
    }

    default boolean hasSlotForItem(T equipmentHolder, ItemType itemType) {
        return hasSlotForItem(itemType);
    }

    default boolean hasSlotForItem(Item item) {
        return item != null && getSlots().stream()
                .anyMatch(slot -> slot.canHoldItem(item));
    }

    default boolean hasSlotForItem(T equipmentHolder, Item item) {
        return hasSlotForItem(item);
    }

    Slot<T> getSlot(String name);

    default Slot<T> getSlot(T equipmentHolder, String name) {
        return getSlot(name);
    }

    Collection<? extends Slot<T>> getSlots();

    default Collection<? extends Slot<T>> getSlots(T equipmentHolder) {
        return getSlots();
    }

    default Collection<? extends Slot<T>> getSlotsForItem(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, ItemType itemType) {
        return getSlotsForItem(itemType);
    }

    default Collection<? extends Slot<T>> getSlotsForItem(Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, Item item) {
        return getSlotsForItem(item);
    }

    default boolean isEquipped(T equipmentHolder, Item item) {
        for (Slot<T> slot : getSlotsForItem(item)) {
            if (slot.containsItem(equipmentHolder, item)) {
                return true;
            }
        }
        return false;
    }

    interface Slot<T> {

        String getName();

        boolean canHoldItem(ItemType itemType);

        default boolean canHoldItem(Item item) {
            return item != null && canHoldItem(item.getType());
        }

        boolean hasItem(T equipmentHolder);

        default boolean containsItem(T equipmentHolder, Item item) {
            return item != null && item.equals(getItem(equipmentHolder));
        }

        Item getItem(T equipmentHolder);
    }
}
