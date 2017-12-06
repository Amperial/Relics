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
package com.herocraftonline.items.api.item.model;

import org.bukkit.inventory.ItemStack;

/**
 * Represents a resource pack model that can be applied to an item.
 *
 * @author Austin Payne
 */
public interface Model {

    /**
     * Gets the name of the model.
     *
     * @return the model's name
     */
    String getName();

    /**
     * Gets the path to the model.
     *
     * @return the model's path
     */
    String getPath();

    /**
     * Applies the model to an item.
     *
     * @param item the item stack
     */
    void apply(ItemStack item);

}
