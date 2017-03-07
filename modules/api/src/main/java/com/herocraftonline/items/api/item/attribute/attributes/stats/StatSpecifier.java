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

/**
 * A piece of information provided by stat attributes to determine whether the stat<br>
 * should be totalled with or apart from other attributes of the same type.
 *
 * @param <T> the type of stat
 * @author Austin Payne
 */
public interface StatSpecifier<T extends StatAttribute<T>> {

    /**
     * A stat specifier that tells the stat group to always total the stat with other stats of the same type.
     *
     * @param <T> the specifier's stat type
     */
    class All<T extends StatAttribute<T>> implements StatSpecifier<T> {
        @Override
        public boolean equals(Object obj) {
            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

}
