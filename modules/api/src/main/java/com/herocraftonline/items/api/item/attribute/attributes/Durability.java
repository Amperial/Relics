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
 * An item attribute that keeps track of an item's durability.
 *
 * @author Austin Payne
 */
public interface Durability extends ItemAttribute {

    int getMax();

    int getCurrent();

    boolean isUsable();

    boolean repair(int amount);

    boolean damage(int amount);

}
