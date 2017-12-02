/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.crafting;

import com.herocraftonline.items.api.crafting.Recipe;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.Triggerable;
import org.bukkit.inventory.ItemStack;

/**
 * An attribute that holds a crafting recipe.
 *
 * @author Austin Payne
 */
public interface Blueprint extends Triggerable<Blueprint> {

    /**
     * Gets the recipe held by the blueprint.
     *
     * @return the blueprint's recipe
     */
    Recipe getRecipe();

    /**
     * Applies the recipe's map renderer to the given item if it is a map.
     *
     * @param item the item stack
     */
    void apply(ItemStack item);

}
