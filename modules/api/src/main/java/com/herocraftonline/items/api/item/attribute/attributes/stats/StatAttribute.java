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
package com.herocraftonline.items.api.item.attribute.attributes.stats;

import com.herocraftonline.items.api.item.attribute.ItemAttribute;

/**
 * An item attribute that represents a stat which can be summed and displayed in a single spot on the item lore.
 *
 * @param <T> the type of stat the attribute tracks
 * @author Austin Payne
 */
public interface StatAttribute<T extends StatType<T>> extends ItemAttribute {

    /**
     * Gets the type of stat tracked.
     *
     * @return the stat's type
     */
    T getStatType();

    /**
     * Gets the stat specifier of the stat.
     *
     * @return the stat's specifier
     */
    StatSpecifier<T> getStatSpecifier();

}
