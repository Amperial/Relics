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
package com.herocraftonline.items.api.item.attribute.attributes.crafting;

import com.herocraftonline.items.api.util.InventoryUtil.Position;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Defines a crafting recipe.
 *
 * @author Austin Payne
 */
public interface Recipe {

    /**
     * Gets the resulting item of crafting the recipe.
     *
     * @return the recipe's result
     */
    ItemStack getResult();

    /**
     * Checks if the given item configuration matches the crafting recipe.
     *
     * @param items the item ingredients
     * @return {@code true} if the items matches the recipe, else {@code false}
     */
    boolean matches(Map<Position, ItemStack> items);

}
