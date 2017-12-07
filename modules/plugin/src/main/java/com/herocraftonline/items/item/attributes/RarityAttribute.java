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
import com.herocraftonline.items.api.item.attribute.AttributeLore;
import com.herocraftonline.items.api.item.attribute.attributes.Rarity;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.function.Function;

public class RarityAttribute extends BaseAttribute<Rarity> implements Rarity {

    private Value<Integer> rarity;

    public RarityAttribute(Item item, String name, Value<Integer> rarity) {
        super(item, name, DefaultAttributes.RARITY);

        this.rarity = rarity;
    }

    @Override
    public int getRarity() {
        return rarity.getValue();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        rarity.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<Rarity> {
        private static final StoredValue<Integer> RARITY = new StoredValue<>("tier", StoredValue.INTEGER, 0);

        private final List<String> tiers;
        private final String unknown;
        private final Function<RarityAttribute, AttributeLore> lore;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.RARITY);
            tiers = config.getStringList("tiers");
            tiers.replaceAll(tier -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', tier));
            unknown = ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', config.getString("unknown", "&8Unknown"));
            lore = (rarity) -> (lore, prefix) -> {
                int value = rarity.getRarity();
                lore.add(prefix + (value >= 0 && value < tiers.size() ? tiers.get(value) : unknown));
            };
        }

        @Override
        public Rarity loadFromConfig(Item item, String name, ConfigurationSection config) {
            Value<Integer> rarity = RARITY.loadFromConfig(item, config);

            RarityAttribute attribute = new RarityAttribute(item, name, rarity);
            attribute.setLore(lore.apply(attribute));
            return attribute;
        }

        @Override
        public Rarity loadFromNBT(Item item, String name, NBTTagCompound compound) {
            Value<Integer> rarity = RARITY.loadFromNBT(item, compound);

            RarityAttribute attribute = new RarityAttribute(item, name, rarity);
            attribute.setLore(lore.apply(attribute));
            return attribute;
        }
    }

}
