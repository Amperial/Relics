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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.crafting.recipe.ingredient.RelicReagent;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ReagentAttribute extends BaseAttribute<Reagent> implements Reagent {

    private final RelicReagent reagent;
    private final boolean showLore;

    public ReagentAttribute(String name, String text, boolean showLore, RelicReagent reagent) {
        super(name, DefaultAttributes.REAGENT);

        this.reagent = reagent;
        this.showLore = showLore;

        if (showLore) {
            setLore((lore, prefix) -> lore.add(prefix + text));
        }
    }

    @Override
    public RelicReagent getReagentType() {
        return reagent;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("reagent-type", getReagentType().getName());
        compound.setBoolean("lore", showLore);
    }

    public static class Factory extends BaseAttributeFactory<Reagent> {
        private final String text;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.REAGENT);
            text = ChatColor.translateAlternateColorCodes('&', config.getString("text", "&aCrafting Ingredient"));
        }

        @Override
        public Reagent loadFromConfig(String name, ConfigurationSection config) {
            // Load reagent type, name, and whether to show lore
            String reagent = config.getString("reagent-type");
            boolean lore = config.getBoolean("lore", true);

            // Create reagent attribute
            return new ReagentAttribute(name, text, lore, new RelicReagent(reagent));
        }

        @Override
        public Reagent loadFromNBT(String name, NBTTagCompound compound) {
            // Load reagent type, name, and whether to show lore
            String reagent = compound.getString("reagent-type");
            boolean lore = compound.getBoolean("lore");

            // Create reagent attribute
            return new ReagentAttribute(name, text, lore, new RelicReagent(reagent));
        }
    }

}
