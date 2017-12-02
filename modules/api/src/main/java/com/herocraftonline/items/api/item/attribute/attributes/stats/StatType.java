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
package com.herocraftonline.items.api.item.attribute.attributes.stats;

import java.util.List;
import java.util.Map;

/**
 * Handles creating stat totals and adding the totals to an item's lore.
 *
 * @param <T> the type of stat
 * @author Austin Payne
 */
public interface StatType<T extends StatAttribute<T>> {

    /**
     * Gets the stat position of the stat attribute.
     *
     * @return the stat's position
     */
    Position getPosition();

    /**
     * Creates a new stat total for the given stat specifier.
     *
     * @param specifier the specifier
     * @return the stat total
     */
    StatTotal<T> newTotal(StatSpecifier<T> specifier);

    /**
     * Adds all of the given stat totals to the list of lore strings.
     *
     * @param lore  the lore
     * @param stats the stat totals
     */
    void addTo(List<String> lore, Map<StatSpecifier<T>, StatTotal<T>> stats);

    /**
     * Possible stat positions on item lore.
     */
    enum Position {
        FARTHEST_TOP,
        TOP,
        BOTTOM,
        FARTHEST_BOTTOM
    }

}
