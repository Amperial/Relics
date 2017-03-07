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
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.Level;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LevelAttribute extends BasicAttribute implements Level {

    private int level;

    public LevelAttribute(String name, String text, int level) {
        super(name, DefaultAttributeType.LEVEL);

        this.level = level;

        setLore((lore, prefix) -> lore.add(prefix + text + " " + getLevel()));
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
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("level", getLevel());
    }

    public static class Factory extends BasicAttributeFactory<LevelAttribute> {

        private final String text;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.LEVEL);
            text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&eItem Level"));
        }

        @Override
        public LevelAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load level
            int level = config.getInt("level", 1);

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
