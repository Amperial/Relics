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
 * An attribute that indicates the level of an item.
 *
 * @author Austin Payne
 */
public interface Level extends Attribute<Level> {

    /**
     * Gets the level of the item.
     *
     * @return the item's level
     */
    int getLevel();

    /**
     * Sets the level of the item.
     *
     * @param level the level
     */
    void setLevel(int level);

}
