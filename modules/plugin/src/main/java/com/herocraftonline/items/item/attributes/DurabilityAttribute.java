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
import com.herocraftonline.items.api.item.attribute.attributes.Durability;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class DurabilityAttribute extends BaseAttribute<Durability> implements Durability {

    private int max;
    private int current;

    public DurabilityAttribute(Item item, String name, int max, int current, Map<Double, String> levels) {
        super(item, name, DefaultAttributes.DURABILITY);

        this.max = max;
        this.current = current;

        setLore((lore, prefix) -> {
            double percent = (double) getCurrent() / (double) getMax();
            double highest = 0;
            for (double level : levels.keySet()) {
                if (percent >= level && level > highest) {
                    highest = level;
                }
            }
            String text;
            if (levels.containsKey(highest)) {
                text = levels.get(highest);
            } else {
                text = ChatColor.GRAY + "Durability: " + getCurrent() + " / " + getMax();
            }
            lore.add(prefix + text);
        });
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCurrent() {
        return current;
    }

    @Override
    public boolean isUsable() {
        return getCurrent() > 0;
    }

    @Override
    public boolean repair(int amount) {
        if (amount > 0) {
            int old = current;
            current += amount;
            if (current > max) {
                current = max;
            }
            return current != old;
        }
        return false;
    }

    @Override
    public boolean damage(int amount) {
        if (amount > 0) {
            int old = current;
            current -= amount;
            if (current < 0) {
                current = 0;
            }
            return current != old;
        }
        return false;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<Durability> {
        private final Map<Double, String> levels = new HashMap<>();

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.DURABILITY);
            ConfigurationSection percents = config.getConfigurationSection("percents");
            for (String key : percents.getKeys(false)) {
                String text = ChatColor.translateAlternateColorCodes('&', percents.getString(key));
                levels.put(Double.valueOf(key) / 100.0, text);
            }
        }

        @Override
        public DurabilityAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load max durability
            int max = Math.max(config.getInt("max", 1), 1);

            // Create durability attribute
            return new DurabilityAttribute(item, name, max, max, levels);
        }

        @Override
        public DurabilityAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load max and current durability
            int max = compound.getInt("max");
            int current = compound.getInt("current");

            // Create durability attribute
            return new DurabilityAttribute(item, name, max, current, levels);
        }
    }

}
