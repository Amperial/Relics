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

import com.herocraftonline.items.api.item.Equippable;
import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An attribute that makes an item "bind" to a player and become unable to be dropped unless destroyed.
 *
 * @author Austin Payne
 */
public interface Soulbound extends Attribute<Soulbound>, Equippable {

    /**
     * Checks if the soulbound item is bound.
     *
     * @return {@code true} if the item is bound, else {@code false}
     */
    boolean isBound();

    /**
     * Sets if the soulbound item is bound.
     *
     * @param bound if the item should be soulbound
     */
    void setBound(boolean bound);

}
