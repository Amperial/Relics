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
import com.herocraftonline.items.crafting.ingredients.reagents.RelicReagent;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.configuration.ConfigurationSection;

public class ReagentAttribute extends BaseAttribute<Reagent> implements Reagent {

    private final RelicReagent reagent;
    private final String reagentName;

    public ReagentAttribute(String name, RelicReagent reagent, String reagentName) {
        super(name, DefaultAttribute.REAGENT);

        this.reagent = reagent;
        this.reagentName = reagentName;

        setLore(((lore, prefix) -> lore.add(prefix + "Crafting Reagent: " + getDisplayName()))); // TODO configurable
    }

    @Override
    public RelicReagent getReagentType() {
        return reagent;
    }

    @Override
    public String getDisplayName() {
        return reagentName;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("reagent-type", getReagentType().getName());
        compound.setString("reagent-name", getDisplayName());
    }

    public static class Factory extends BaseAttributeFactory<Reagent> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Reagent loadFromConfig(String name, ConfigurationSection config) {
            String reagent = config.getString("reagent-type");
            String reagentName = config.getString("reagent-name");

            return new ReagentAttribute(name, new RelicReagent(reagent), reagentName);
        }

        @Override
        public Reagent loadFromNBT(String name, NBTTagCompound compound) {
            String reagent = compound.getString("reagent-type");
            String reagentName = compound.getString("reagent-name");

            return new ReagentAttribute(name, new RelicReagent(reagent), reagentName);
        }
    }

}
