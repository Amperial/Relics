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
package com.herocraftonline.items.api.item.attribute.attributes.gems;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeContainer;

/**
 * An attribute that can be infused into socket attributes.<br>
 * Gems can also contain attributes of their own, and remember an item representation of themselves.
 *
 * @author Austin Payne
 */
public interface Gem extends Attribute<Gem>, AttributeContainer {

    /**
     * Gets the display name of the gem.
     *
     * @return the gem's display name
     */
    String getDisplayName();

    /**
     * Gets the socket color of the gem.
     *
     * @return the gem's socket color
     */
    SocketColor getColor();

    /**
     * Sets the socket color of the gem.
     *
     * @param color the socket color
     */
    void setColor(SocketColor color);

    /**
     * Checks if the gem currently has an item representation.
     *
     * @return {@code true} if the gem has an item, else {@code false}
     */
    boolean hasItem();

    /**
     * Gets the item representation of the gem.
     *
     * @return the gem's item
     */
    Item getItem();

    /**
     * Sets the item representation of the gem.
     *
     * @param item the item
     */
    void setItem(Item item);

}
