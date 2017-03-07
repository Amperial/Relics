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

import com.herocraftonline.items.api.item.attribute.Requirement;
import com.herocraftonline.items.api.item.attribute.attributes.Level;

import java.util.List;
import java.util.Map;

public interface LevelRequirement extends StatAttribute<LevelRequirement.LevelRequirementStatType>, Level, Requirement {

    class LevelRequirementStatType implements StatType<LevelRequirementStatType> {

        private final String text;

        public LevelRequirementStatType(String text) {
            this.text = text;
        }

        @Override
        public Position getPosition() {
            return Position.FARTHEST_BOTTOM;
        }

        @Override
        public StatTotal<LevelRequirementStatType> newTotal(StatSpecifier<LevelRequirementStatType> specifier) {
            return new StatTotal<LevelRequirementStatType>(specifier) {
                private int level;

                @Override
                public void addStat(StatAttribute<LevelRequirementStatType> stat) {
                    level += ((LevelRequirement) stat).getLevel();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    lore.add(prefix + text + " " + level);
                }
            };
        }

        @Override
        public void addTo(List<String> lore, Map<StatSpecifier<LevelRequirementStatType>, StatTotal<LevelRequirementStatType>> stats) {
            stats.forEach((specifier, total) -> total.addTo(lore, ""));
        }

    }

}
