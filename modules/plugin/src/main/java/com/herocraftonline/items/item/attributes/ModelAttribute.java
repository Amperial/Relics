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
import com.herocraftonline.items.api.item.attribute.attributes.Model;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

public class ModelAttribute extends BasicAttribute implements Model {

    private short modelDamage;

    public ModelAttribute(String name, short modelDamage) {
        super(name, DefaultAttributeType.MODEL);

        this.modelDamage = modelDamage;
    }

    @Override
    public short getModelDamage() {
        return modelDamage;
    }

    @Override
    public void setModelDamage(short modelDamage) {
        this.modelDamage = modelDamage;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setShort("damage", getModelDamage());
    }

    public static class Factory extends BasicAttributeFactory<Model> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Model loadFromConfig(String name, ConfigurationSection config) {
            // Load model damage
            short modelDamage = (short) config.getInt("damage");

            // Create model attribute
            return new ModelAttribute(name, modelDamage);
        }

        @Override
        public Model loadFromNBT(String name, NBTTagCompound compound) {
            // Load model damage
            short modelDamage = compound.getShort("damage");

            // Create model attribute
            return new ModelAttribute(name, modelDamage);
        }

    }

}
