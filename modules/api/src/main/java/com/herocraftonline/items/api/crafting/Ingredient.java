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
package com.herocraftonline.items.api.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

/**
 * Defines a crafting ingredient.
 *
 * @author Austin Payne
 */
public interface Ingredient extends Predicate<ItemStack> {

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

}
