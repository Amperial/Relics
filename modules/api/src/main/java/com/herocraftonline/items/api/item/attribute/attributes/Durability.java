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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An attribute that keeps track of an item's durability.
 *
 * @author Austin Payne
 */
public interface Durability extends Attribute<Durability> {

    /**
     * Gets the item's maximum durability.
     *
     * @return the max durability
     */
    int getMax();

    /**
     * Gets the item's current durability.
     *
     * @return the current durability
     */
    int getCurrent();

    /**
     * Checks if the item is usable.
     *
     * @return {@code true} if the item is usable
     */
    boolean isUsable();

    /**
     * Repairs the item's durability by a certain amount.
     *
     * @param amount the amount to add
     * @return {@code true} if the durability was changed
     */
    boolean repair(int amount);

    /**
     * Damages the item's durability by a certain amount.
     *
     * @param amount the amount to subtract
     * @return {@code true} if the durability was changed
     */
    boolean damage(int amount);

}
