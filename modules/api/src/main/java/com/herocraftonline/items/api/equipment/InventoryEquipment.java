package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface InventoryEquipment<T extends InventoryHolder> extends Equipment<T> {

    boolean hasSlot(T equipmentHolder, int inventoryIndex);

    @Override
    Slot<? extends T> getSlot(T equipmentHolder, String name);

    Slot<? extends T> getSlot(T equipmentHolder, int inventoryIndex);

    @Override
    Collection<? extends Slot<T>> getSlots(T equipmentHolder);

    @Override
    default Collection<? extends Slot<T>> getSlots(T equipmentHolder, ItemType itemType) {
        return getSlots(equipmentHolder)
                .stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlots(T equipmentHolder, Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots(equipmentHolder, item.getType());
    }

    interface Slot<T extends InventoryHolder> extends Equipment.Slot<T> {

        int getInventorySlot(T equipmentHolder);
    }
}
