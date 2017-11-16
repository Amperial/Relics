package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public interface PlayerEquipment {

    boolean hasSlot(String name);

    boolean isSlot(int inventoryIndex);

    default boolean hasSlotForItem(ItemType itemType) {
        return itemType != null && getSlots().stream()
                .anyMatch(slot -> slot.canHoldItem(itemType));
    }

    default boolean hasSlotForItem(Item item) {
        return item != null && getSlots().stream()
                .anyMatch(slot -> slot.canHoldItem(item));
    }

    Slot getSlot(String name);

    Collection<? extends Slot> getSlots();

    default Collection<? extends Slot> getSlotsForItem(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot> getSlotsForItem(Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    default boolean isEquipped(Player player, Item item) {
        for (Slot slot : getSlotsForItem(item)) {
            if (slot.containsItem(player, item)) {
                return true;
            }
        }
        return false;
    }

    interface Slot {

        String getName();

        boolean canHoldItem(ItemType itemType);

        default boolean canHoldItem(Item item) {
            return item != null && canHoldItem(item.getType());
        }

        Optional<Item> getItem(Player player);

        default boolean containsItem(Player player, Item item) {
            return item != null && getItem(player).filter(slotItem -> slotItem.equals(item)).isPresent();
        }
    }
}
