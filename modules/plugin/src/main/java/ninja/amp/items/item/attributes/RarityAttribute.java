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
import ninja.amp.items.api.item.attribute.attributes.Rarity;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class RarityAttribute extends BasicAttribute implements Rarity {

    private int rarity;

    public RarityAttribute(String name, int rarity, String text) {
        super(name, DefaultAttributeType.RARITY);

        this.rarity = rarity;

        setLore((lore, prefix) -> lore.add(prefix + text));
    }

    @Override
    public int getRarity() {
        return rarity;
    }

    @Override
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("tier", getRarity());
    }

    public static class Factory extends BasicAttributeFactory<Rarity> {

        private final List<String> tiers;
        private final String unknown;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.RARITY);
            tiers = config.getStringList("tiers");
            tiers.replaceAll(tier -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', tier));
            unknown = ChatColor.translateAlternateColorCodes('&', config.getString("unknown", "&8Unknown"));
        }

        @Override
        public Rarity loadFromConfig(String name, ConfigurationSection config) {
            // Load rarity
            int rarity = config.getInt("tier");
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }

        @Override
        public Rarity loadFromNBT(String name, NBTTagCompound compound) {
            // Load rarity
            int rarity = compound.getInt("tier");
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }

    }

}
