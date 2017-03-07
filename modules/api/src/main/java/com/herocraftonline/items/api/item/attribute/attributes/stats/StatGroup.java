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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An attribute lore implementation that handles totalling stats of different types and specifiers,<br>
 * with the final goal of displaying the total stats of an item in one spot on the item lore.
 *
 * @param <T> the type of stat the stat group tracks
 * @author Austin Payne
 */
public class StatGroup<T extends StatAttribute<T>> implements AttributeLore {

    private final Map<StatType<T>, Map<StatSpecifier<T>, StatTotal<T>>> stats;

    public StatGroup() {
        stats = new HashMap<>();
    }

    /**
     * Adds a stat to the stat group.
     *
     * @param stat the stat to add
     */
    public void addStat(T stat) {
        StatType<T> type = stat.getStatType();
        StatSpecifier<T> specifier = stat.getStatSpecifier();

        if (!stats.containsKey(type)) {
            stats.put(type, new HashMap<>());
        }
        Map<StatSpecifier<T>, StatTotal<T>> totals = stats.get(type);
        if (!totals.containsKey(specifier)) {
            totals.put(specifier, type.newTotal(specifier));
        }
        totals.get(specifier).addStat(stat);
    }

    @Override
    public void addTo(List<String> lore, String prefix) {
        stats.entrySet().stream()
                .forEach(stat -> {
                    Map<StatSpecifier<T>, StatTotal<T>> totals = stat.getValue().entrySet().stream()
                            .filter(value -> value.getValue().shouldAddLore())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    stat.getKey().addTo(lore, totals);
                });
    }

}
