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
 * Represents a type of reagant for use in crafting recipes.
 *
 * @author Austin Payne
 */
public interface ReagentType extends Predicate<ItemStack> {

    /**
     * Gets the reagent type's display icon.
     *
     * @return the display icon name
     */
    String getDisplayIcon();

}
