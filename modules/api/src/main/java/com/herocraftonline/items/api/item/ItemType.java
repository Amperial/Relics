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
package com.herocraftonline.items.api.item;

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
     * Checks if this item type is transient. A transient item type is created
     * when an item type is defined in an item or item config that isn't defined in
     * the items.yml. This is to prevent such items from causing problems with the
     * rest of the plugin. Transient item have no parent or children.
     *
     * @return {@code true} if this item type is transient, else {@code false}
     */
    boolean isTransient();

}
