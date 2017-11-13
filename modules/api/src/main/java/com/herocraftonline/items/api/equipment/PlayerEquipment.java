package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface PlayerEquipment {

    boolean hasSlot(Player player, String name);

    boolean hasSlot(Player player, int inventoryIndex);

    default boolean hasSlotForItem(Player player, ItemType itemType) {
        return itemType != null &&
                getSlots(player)
                .stream()
                .anyMatch(slot -> slot.canHoldItem(itemType));
    }

    default boolean hasSlotForItem(Player player, Item item) {
        return item != null && hasSlotForItem(player, item.getType());
    }

    Slot getSlot(Player player, String name);

    Slot getSlot(Player player, int inventoryIndex);

    Collection<? extends Slot> getSlots(Player player);

    default Collection<? extends Slot> getSlotsForItem(Player player, ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots(player)
                .stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot> getSlotsForItem(Player player, Item item) {
        if (item == null) return Collections.emptyList();
        return getSlotsForItem(player, item.getType());
    }

    default boolean isEquipped(Player player, Item item) {
        for (Slot slot : getSlotsForItem(player, item)) {
            if (slot.containsItem(player, item)) {
                return true;
            }
        }
        return false;
    }

    interface Slot {

        String getName();

        int getInventoryIndex();

        boolean canHoldItem(ItemType itemType);

        default boolean canHoldItem(Item item) {
            return item != null && canHoldItem(item.getType());
        }

        boolean hasItem(Player player);

        default boolean containsItem(Player player, Item item) {
            return item != null && item.equals(getItem(player));
        }

        Item getItem(Player player);
    }
}
