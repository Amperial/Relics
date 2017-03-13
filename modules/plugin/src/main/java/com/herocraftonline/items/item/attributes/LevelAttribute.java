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
import com.herocraftonline.items.api.item.attribute.attributes.Level;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LevelAttribute extends BaseAttribute<Level> implements Level {

    private int level;

    public LevelAttribute(String name, String text, int level) {
        super(name, DefaultAttribute.LEVEL);

        this.level = level;

        setLore((lore, prefix) -> lore.add(prefix + text + " " + getValue()));
    }

    @Override
    public Integer getValue() {
        return level;
    }

    @Override
    public void setValue(Integer value) {
        level = value;
    }

    @Override
    public int compareTo(Level o) {
        return getValue() - o.getValue();
    }

    @Override
    public Integer sum(Integer value) {
        return getValue() + value;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("level", getValue());
    }

    public static class Factory extends BaseAttributeFactory<Level> {
        private final String text;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttribute.LEVEL);
            text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&eItem Level"));
        }

        @Override
        public LevelAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load level
            int level = Math.max(config.getInt("level", 1), 1);

            // Create level attribute
            return new LevelAttribute(name, text, level);
        }

        @Override
        public LevelAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load level
            int level = compound.getInt("level");

            // Create level attribute
            return new LevelAttribute(name, text, level);
        }
    }

}
