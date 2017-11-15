/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.util;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.nms.NMSHandler;
import org.bukkit.inventory.ItemStack;

/**
 * Utility methods for items.
 *
 * @author Austin Payne
 */
public final class ItemUtil {

    private ItemUtil() {
    }

    /**
     * Serializes the given item into a string.
     *
     * @param item the item object
     * @return the serialized item string
     */
    public static String serialize(Item item) {
        return serialize(item.getItem());
    }

    /**
     * Serializes the given item stack into a string.
     *
     * @param itemStack the item stack object
     * @return the serialized item string
     */
    public static String serialize(ItemStack itemStack) {
        return NMSHandler.getInterface().serializeItem(itemStack);
    }

    /**
     * Deserializes the given string into an item stack.
     *
     * @param itemStack the serialized item stack string
     * @return the deserialized item stack
     */
    public static ItemStack deserialize(String itemStack) {
        return NMSHandler.getInterface().deserializeItem(itemStack);
    }

}
