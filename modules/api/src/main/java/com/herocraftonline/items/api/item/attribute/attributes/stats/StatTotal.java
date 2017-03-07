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

import com.herocraftonline.items.api.item.attribute.AttributeLore;

/**
 * An item lore implementation that handles totalling stats of the same type and specifier.
 *
 * @param <T> the type of stat to be totalled
 * @author Austin Payne
 */
public abstract class StatTotal<T extends StatAttribute<T>> implements AttributeLore {

    private final StatSpecifier<T> specifier;

    public StatTotal(StatSpecifier<T> specifier) {
        this.specifier = specifier;
    }

    /**
     * Gets the stat specifier of the stat total.
     *
     * @return the stat total's specifier
     */
    public StatSpecifier<T> getStatSpecifier() {
        return specifier;
    }

    /**
     * Adds a stat to the stat total.
     *
     * @param stat the stat to add
     */
    public abstract void addStat(T stat);

    /**
     * Checks if the stat total should add its lore to the item lore.
     *
     * @return {@code true} if the stat total should add its lore, else {@code false}
     */
    public boolean shouldAddLore() {
        return true;
    }

}
