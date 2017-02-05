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

import java.util.List;
import java.util.Map;

public interface StatType<T extends StatType<T>> {

    StatTotal<T> newTotal(StatSpecifier<T> specifier);

    void addTo(List<String> lore, Map<StatSpecifier<T>, StatTotal<T>> stats);

}
