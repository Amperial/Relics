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

import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent.ReagentType;
import org.bukkit.inventory.ItemStack;

/**
 * Defines a crafting ingredient.
 *
 * @author Austin Payne
 */
public interface Ingredient {

    /**
     * The required reagent type of the crafting ingredient.
     *
     * @return the ingredient's reagent type
     */
    ReagentType getType();

    /**
     * The required stack size of the crafting ingredient.
     *
     * @return the ingredient's amount
     */
    int getAmount();

    /**
     * Checks if a given item matches the crafting ingredient.
     *
     * @param item the item
     * @return {@code true} if the item matches the ingredient, else {@code false}
     */
    boolean matches(ItemStack item);

}
