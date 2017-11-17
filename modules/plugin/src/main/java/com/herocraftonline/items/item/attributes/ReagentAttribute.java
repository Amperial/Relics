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
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class ReagentAttribute extends BaseAttribute<Reagent> implements Reagent {

    private final RelicReagent reagent;
    private final String reagentName;
    private final Material reagentMaterial;

    public ReagentAttribute(String name, RelicReagent reagent, String reagentName, Material reagentMaterial) {
        super(name, DefaultAttribute.REAGENT);

        this.reagent = reagent;
        this.reagentName = reagentName;
        this.reagentMaterial = reagentMaterial;
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
    public Material getDisplayMaterial() {
        return reagentMaterial;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("reagent-type", getReagentType().getName());
        compound.setString("reagent-name", getDisplayName());
        compound.setString("reagent-material", getDisplayMaterial().name());
    }

    public static class Factory extends BaseAttributeFactory<Reagent> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Reagent loadFromConfig(String name, ConfigurationSection config) {
            String reagent = config.getString("reagent-type");
            String reagentName = config.getString("reagent-name");
            String reagentMaterial = config.getString("reagent-material");

            return new ReagentAttribute(name, new RelicReagent(reagent), reagentName, Material.valueOf(reagentMaterial));
        }

        @Override
        public Reagent loadFromNBT(String name, NBTTagCompound compound) {
            String reagent = compound.getString("reagent-type");
            String reagentName = compound.getString("reagent-name");
            String reagentMaterial = compound.getString("reagent-material");

            return new ReagentAttribute(name, new RelicReagent(reagent), reagentName, Material.valueOf(reagentMaterial));
        }
    }

}
