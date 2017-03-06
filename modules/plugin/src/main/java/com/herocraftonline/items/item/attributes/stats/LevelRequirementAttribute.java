/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes.stats;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.stats.BasicStatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.LevelRequirement;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatSpecifier;
import com.herocraftonline.items.item.attributes.DefaultAttributeType;
import com.herocraftonline.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LevelRequirementAttribute extends BasicStatAttribute<LevelRequirement.LevelRequirementStatType> implements LevelRequirement {

    private int level;

    public LevelRequirementAttribute(String name, LevelRequirementStatType statType, int level) {
        super(name, DefaultAttributeType.LEVEL_REQUIREMENT, statType);

        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean canEquip(Player player) {
        return true;
    }

    @Override
    public boolean onEquip(Player player) {
        return false;
    }

    @Override
    public boolean onUnEquip(Player player) {
        return false;
    }

    @Override
    public StatSpecifier<LevelRequirementStatType> getStatSpecifier() {
        return new StatSpecifier.All<>();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("level", getLevel());
    }

    public static class Factory extends BasicAttributeFactory<LevelRequirementAttribute> {

        private final LevelRequirementStatType statType;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.LEVEL_REQUIREMENT);
            String text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&eLevel Requirement:"));
            statType = new LevelRequirementStatType(text);
        }

        @Override
        public LevelRequirementAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load level
            int level = config.getInt("level", 1);

            // Create level requirement attribute
            return new LevelRequirementAttribute(name, statType, level);
        }

        @Override
        public LevelRequirementAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load level
            int level = compound.getInt("level");

            // Create level requirement attribute
            return new LevelRequirementAttribute(name, statType, level);
        }

    }

}
