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
import ninja.amp.items.api.item.attribute.attributes.Model;
import ninja.amp.items.nms.nbt.NBTTagCompound;
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
        compound.setShort("model-damage", getModelDamage());
    }

    public static class Factory extends BasicAttributeFactory<Model> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Model loadFromConfig(String name, ConfigurationSection config) {
            // Load model damage
            short modelDamage = (short) config.getInt("model-damage");

            // Create model attribute
            return new ModelAttribute(name, modelDamage);
        }

        @Override
        public Model loadFromNBT(String name, NBTTagCompound compound) {
            // Load model damage
            short modelDamage = compound.getShort("model-damage");

            // Create model attribute
            return new ModelAttribute(name, modelDamage);
        }

    }

}
