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
package com.herocraftonline.items.api.item;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a type of custom item.
 *
 * @author Austin Payne
 */
public interface ItemType {

    /**
     * Gets the name of this item type
     *
     * @return the name
     */
    String getName();

    /**
     * Checks if this item has a parent item type
     *
     * @return {@code true} if a parent exists, {@code false} otherwise
     */
    boolean hasParent();

    /**
     * Gets the parent of this item type
     *
     * @return the parent
     */
    ItemType getParent();

    /**
     * Checks if this item type is a child of the given item type
     *
     * @param itemType the item type to test for
     * @return {@code true} if this item type is a child of the given item type, {@code false} otherwise
     */
    boolean isType(ItemType itemType);

    /**
     * Checks if this item type is abstract. An abstract item type exists
     * for utility purposes and can not be applied to an item.
     *
     * @return {@code true} if item type is abstract, {@code false} otherwise
     */
    boolean isAbstract();
}
