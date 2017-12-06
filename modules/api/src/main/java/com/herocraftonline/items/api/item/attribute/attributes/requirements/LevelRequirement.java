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
package com.herocraftonline.items.api.item.attribute.attributes.requirements;

import com.herocraftonline.items.api.item.attribute.attributes.stats.StatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatSpecifier;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatTotal;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatType;

import java.util.List;
import java.util.Map;

/**
 * An attribute that defines a required level for a player to equip an item.<br>
 * In one possible implementation, this level could be the player's experience level.
 *
 * @author Austin Payne
 */
public interface LevelRequirement extends EquipRequirement<LevelRequirement>, StatAttribute<LevelRequirement> {

    /**
     * Gets the level requirement.
     *
     * @return the required level
     */
    int getLevel();

    /**
     * Sets the level requirement.
     *
     * @param level the required level
     */
    void setLevel(int level);

    /**
     * Handles totalling the level requirements of an item and adding the total to the item's lore.
     */
    class LevelRequirementStatType implements StatType<LevelRequirement> {
        private final String text;

        public LevelRequirementStatType(String text) {
            this.text = text;
        }

        @Override
        public Position getPosition() {
            return Position.FARTHEST_BOTTOM;
        }

        @Override
        public StatTotal<LevelRequirement> newTotal(StatSpecifier<LevelRequirement> specifier) {
            return new StatTotal<LevelRequirement>(specifier) {
                private int level;

                @Override
                public void addStat(LevelRequirement stat) {
                    level += stat.getLevel();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    lore.add(prefix + text + " " + level);
                }
            };
        }

        @Override
        public void addTo(List<String> lore, Map<StatSpecifier<LevelRequirement>, StatTotal<LevelRequirement>> stats) {
            stats.forEach((specifier, total) -> total.addTo(lore, ""));
        }
    }

}
