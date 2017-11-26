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

import org.bukkit.inventory.ItemStack;

/**
 * Defines a crafting result.
 *
 * @author Austin Payne
 */
public interface Result {

    /**
     * Gets the resulting item stack.
     *
     * @return the result item
     */
    ItemStack getItem();

    /**
     * Gets the result's display icon.
     *
     * @return the display icon name
     */
    String getDisplayIcon();

}
