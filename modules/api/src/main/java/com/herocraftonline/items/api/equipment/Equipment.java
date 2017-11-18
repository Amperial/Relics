package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface Equipment<T extends LivingEntity> {

    boolean hasSlot(String name);

    Slot getSlot(String name);

    boolean hasSlotForItem(ItemType itemType);

    boolean hasSlotForItem(Item item);

    Collection<? extends Slot> getSlots();

    Collection<? extends Slot> getSlotsForItem(ItemType itemType);

    Collection<? extends Slot> getSlotsForItem(Item item);

    boolean isEquipped(Item item);

    void unEquipAll();

    interface Slot {

        String getName();

        boolean canContainItem(ItemType itemType);

        boolean canContainItem(Item item);

        boolean hasItem();

        Item getItem();

        boolean setItem(Item item);

        void removeItem();

        default boolean containsItem(Item item) {
            return item != null && item.equals(getItem());
        }
    }
}
