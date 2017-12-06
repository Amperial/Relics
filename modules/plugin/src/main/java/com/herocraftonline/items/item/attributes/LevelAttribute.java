/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.Level;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LevelAttribute extends BaseAttribute<Level> implements Level {

    private Value<Integer> level;

    public LevelAttribute(Item item, String name, String text, Value<Integer> level) {
        super(item, name, DefaultAttributes.LEVEL);

        this.level = level;

        setLore((lore, prefix) -> lore.add(prefix + text + " " + getLevel()));
    }

    @Override
    public int getLevel() {
        return level.getValue();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        level.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<Level> {
        private static final StoredValue<Integer> LEVEL = new StoredValue<>("level", StoredValue.INTEGER, 1);
        private final String text;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.LEVEL);
            text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&eItem Level"));
        }

        @Override
        public LevelAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load level
            Value<Integer> level = LEVEL.loadFromConfig(item, config);

            // Create level attribute
            return new LevelAttribute(item, name, text, level);
        }

        @Override
        public LevelAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load level
            Value<Integer> level = LEVEL.loadFromNBT(item, compound);

            // Create level attribute
            return new LevelAttribute(item, name, text, level);
        }
    }

}
