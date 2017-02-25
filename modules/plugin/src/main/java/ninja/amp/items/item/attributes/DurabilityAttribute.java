/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.Durability;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class DurabilityAttribute extends BasicAttribute implements Durability {

    private int max;
    private int current;

    public DurabilityAttribute(String name, int max, int current, Map<Double, String> levels) {
        super(name, DefaultAttributeType.DURABILITY);

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

    public static class Factory extends BasicAttributeFactory<DurabilityAttribute> {

        private final Map<Double, String> levels = new HashMap<>();

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.DURABILITY);
            ConfigurationSection percents = config.getConfigurationSection("percents");
            for (String key : percents.getKeys(false)) {
                String text = ChatColor.translateAlternateColorCodes('&', percents.getString(key));
                levels.put(Double.valueOf(key) / 100.0, text);
            }
        }

        @Override
        public DurabilityAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load max durability
            int max = config.getInt("max");

            // Create durability attribute
            return new DurabilityAttribute(name, max, max, levels);
        }

        @Override
        public DurabilityAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load max and current durability
            int max = compound.getInt("max");
            int current = compound.getInt("current");

            // Create durability attribute
            return new DurabilityAttribute(name, max, current, levels);
        }

    }

}
