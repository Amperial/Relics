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
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.requirements.LevelRequirement;
import com.herocraftonline.items.api.item.attribute.attributes.stats.BaseStatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatSpecifier;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LevelRequirementAttribute extends BaseStatAttribute<LevelRequirement> implements LevelRequirement {

    private int level;

    public LevelRequirementAttribute(Item item, String name, LevelRequirementStatType statType, int level) {
        super(item, name, DefaultAttributes.LEVEL_REQUIREMENT, statType);

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
    public StatSpecifier<LevelRequirement> getStatSpecifier() {
        return new StatSpecifier.All<>();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("level", getLevel());
    }

    @Override
    public boolean test(Player player, Item item) {
        return player.getLevel() >= item.getAttributesDeep(LevelRequirement.class).stream().mapToInt(LevelRequirement::getLevel).sum();
    }

    public static class Factory extends BaseAttributeFactory<LevelRequirement> {
        private final LevelRequirementStatType statType;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.LEVEL_REQUIREMENT);
            String text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&eLevel Requirement:"));
            statType = new LevelRequirementStatType(text);
        }

        @Override
        public LevelRequirementAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load level
            int level = Math.max(config.getInt("level", 0), 0);

            // Create level requirement attribute
            return new LevelRequirementAttribute(item, name, statType, level);
        }

        @Override
        public LevelRequirementAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load level
            int level = compound.getInt("level");

            // Create level requirement attribute
            return new LevelRequirementAttribute(item, name, statType, level);
        }
    }

}
