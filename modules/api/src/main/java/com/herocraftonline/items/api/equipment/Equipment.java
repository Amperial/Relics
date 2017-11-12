/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public interface Equipment<T> {

    boolean hasSlot(T equipmentHolder, String name);

    default boolean hasSlot(T equipmentHolder, ItemType itemType) {
        return getSlots(equipmentHolder)
                .stream()
                .anyMatch(slot -> slot.canHoldItem(itemType));
    }

    default boolean hasSlot(T equipmentHolder, Item item) {
        return item != null && hasSlot(equipmentHolder, item.getType());
    }

    Slot<? extends T> getSlot(T equipmentHolder, String name);

    Collection<? extends Slot<T>> getSlots(T equipmentHolder);

    default Collection<? extends Slot<T>> getSlots(T equipmentHolder, ItemType itemType) {
        return getSlots(equipmentHolder)
                .stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getSlots(T equipmentHolder, Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots(equipmentHolder, item.getType());
    }

    interface Slot<T> {

        String getName();

        boolean canHoldItem(ItemType itemType);

        default boolean canHoldItem(Item item) {
            return item != null && canHoldItem(item.getType());
        }

        boolean hasItem(T equipmentHolder);

        Item getItem(T equipmentHolder);

        boolean setItem(Item item, T equipmentHolder);
    }
}
