/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item.attribute.attributes.stats;

import ninja.amp.items.api.item.attribute.ItemLore;

public abstract class StatTotal<T extends StatType<T>> implements ItemLore {

    private final StatSpecifier<T> specifier;

    public StatTotal(StatSpecifier<T> specifier) {
        this.specifier = specifier;
    }

    public StatSpecifier<T> getStatSpecifier() {
        return specifier;
    }

    public abstract void addStat(StatAttribute<T> stat);

    public boolean shouldAddLore() {
        return true;
    }

}
