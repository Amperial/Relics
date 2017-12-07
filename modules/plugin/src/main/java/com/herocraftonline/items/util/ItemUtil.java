/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.util;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.nms.NMSHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility methods for items.
 *
 * @author Austin Payne
 */
public final class ItemUtil {

    private ItemUtil() {
    }

    /**
     * Gets the display name of a given item stack.
     *
     * @param item the item stack
     * @return the item stack's display name
     */
    public static String getName(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : getName(item.getType());
    }

    /**
     * Gets the display name of a given material.
     *
     * @param material the material
     * @return the material's display name
     */
    public static String getName(Material material) {
        List<String> parts = new ArrayList<>();
        for (String s : material.name().split("_")) {
            parts.add(s.length() > 1 ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : s.toUpperCase());
        }
        return StringUtils.join(parts, ' ');
    }

    /**
     * Serializes the given item into a string.
     *
     * @param item the item
     * @return the serialized item string
     */
    public static String serialize(Item item) {
        return serialize(item.getItem());
    }

    /**
     * Serializes the given item stack into a string.
     *
     * @param itemStack the item stack
     * @return the serialized item string
     */
    public static String serialize(ItemStack itemStack) {
        return NMSHandler.instance().serializeItem(itemStack);
    }

    /**
     * Deserializes the given string into an item stack.
     *
     * @param itemStack the serialized item stack string
     * @return the deserialized item stack
     */
    public static Optional<ItemStack> deserialize(String itemStack) {
        try {
            return Optional.of(NMSHandler.instance().deserializeItem(itemStack));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

}
