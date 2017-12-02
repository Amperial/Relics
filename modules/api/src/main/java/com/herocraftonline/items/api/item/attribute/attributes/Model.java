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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

/**
 * An attribute that displays the item with a custom model.
 *
 * @author Austin Payne
 */
public interface Model extends Attribute<Model> {

    /**
     * Applies the model to the given item.
     *
     * @param item the item stack
     */
    void apply(ItemStack item);

}
