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

import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An item attribute that represents a stat which can be totalled and displayed in a single spot on the item lore.
 *
 * @param <T> the type of stat
 * @author Austin Payne
 */
public interface StatAttribute<T extends StatAttribute<T>> extends Attribute<T> {

    /**
     * Gets the type of stat tracked.
     *
     * @return the stat's type
     */
    StatType<T> getStatType();

    /**
     * Gets the stat specifier of the stat.
     *
     * @return the stat's specifier
     */
    StatSpecifier<T> getStatSpecifier();

}
