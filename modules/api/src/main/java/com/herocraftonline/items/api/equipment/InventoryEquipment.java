package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface InventoryEquipment<T extends InventoryHolder> extends Equipment<T> {

    boolean hasSlot(int inventorySlot);

    default boolean hasSlot(T equipmentHolder, int inventorySlot) {
        return hasSlot(inventorySlot);
    }

    @Override
    Slot<T> getSlot(String name);

    @Override
    default Slot<T> getSlot(T equipmentHolder, String name) {
        return getSlot(name);
    }

    Slot<T> getSlot(int inventorySlot);

    default Slot<T> getSlot(T equipmentHolder, int inventorySlot) {
        return getSlot(inventorySlot);
    }

    @Override
    Collection<? extends Slot<T>> getSlots();

    @Override
    default Collection<? extends Slot<T>> getSlots(T equipmentHolder) {
        return getSlots();
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots(equipmentHolder).stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots(equipmentHolder).stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getInventorySlots() {
        return getSlots().stream()
                .filter(Slot::isInventorySlot)
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getInventorySlots(T equipmentHolder) {
        return getSlots(equipmentHolder).stream()
                .filter(Slot::isInventorySlot)
                .collect(Collectors.toList());
    }

    interface Slot<T extends InventoryHolder> extends Equipment.Slot<T> {

        boolean isInventorySlot();

        int getInventorySlot();
    }
}
