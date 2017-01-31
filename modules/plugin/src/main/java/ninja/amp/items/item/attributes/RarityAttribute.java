/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
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
        compound.setInt("tier", rarity);
    }

    public static class RarityAttributeFactory extends BasicAttributeFactory<RarityAttribute> {

        private final List<String> tiers;
        private final String unknown;

        public RarityAttributeFactory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.RARITY);
            tiers = config.getStringList("tiers");
            tiers.replaceAll(tier -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', tier));
            unknown = ChatColor.translateAlternateColorCodes('&', config.getString("unknown", "&8Unknown"));
        }

        @Override
        public RarityAttribute loadFromConfig(ConfigurationSection config) {
            // Load name and rarity
            String name = config.getName();
            int rarity = config.getInt("tier");
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }

        @Override
        public RarityAttribute loadFromNBT(NBTTagCompound compound) {
            // Load name and rarity
            String name = compound.getString("name");
            int rarity = compound.getInt("tier");
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }

    }

}
