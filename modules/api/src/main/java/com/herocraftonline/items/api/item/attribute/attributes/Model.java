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

import java.util.function.BinaryOperator;

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

    /**
     * Gets the model's priority to choose a single model to apply to the item.
     *
     * @return the priority of the model
     */
    int getPriority();

    /**
     * Used in choosing the model with the highest priority.
     */
    BinaryOperator<Model> PRIORITY = (r1, r2) -> r2.getPriority() > r1.getPriority() ? r2 : r1;

}
