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

import com.herocraftonline.items.api.item.attribute.ItemAttribute;

/**
 * An item attribute that displays the item with a set durability, used in resource packs with custom item models.
 *
 * @author Austin Payne
 */
public interface Model extends ItemAttribute {

    /**
     * Gets the damage value of the custom model.
     *
     * @return the item's damage value
     */
    short getModelDamage();

    /**
     * Sets the damage value of the custom model.
     *
     * @param damage the damage value
     */
    void setModelDamage(short damage);

}
