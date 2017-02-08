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

public interface StatSpecifier<T extends StatType<T>> {

    class ALL<T extends StatType<T>> implements StatSpecifier<T> {
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
