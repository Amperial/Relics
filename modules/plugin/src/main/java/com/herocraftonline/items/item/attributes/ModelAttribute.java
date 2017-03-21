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
import com.herocraftonline.items.api.item.attribute.attributes.Model;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.model.ModelManager;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ModelAttribute extends BaseAttribute<Model> implements Model {

    private com.herocraftonline.items.api.item.model.Model model;

    public ModelAttribute(String name, com.herocraftonline.items.api.item.model.Model model) {
        super(name, DefaultAttribute.MODEL);

        this.model = model;
    }

    @Override
    public void apply(ItemStack item) {
        model.apply(item);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("model", model.getName());
    }

    public static class Factory extends BaseAttributeFactory<Model> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Model loadFromConfig(String name, ConfigurationSection config) {
            ModelManager modelManager = getPlugin().getModelManager();

            // Load model
            String modelName = config.getString("model");
            com.herocraftonline.items.api.item.model.Model model = modelManager.getModel(modelName);

            // Create model attribute
            return new ModelAttribute(name, model);
        }

        @Override
        public Model loadFromNBT(String name, NBTTagCompound compound) {
            ModelManager modelManager = getPlugin().getModelManager();

            // Load model
            String modelName = compound.getString("model");
            com.herocraftonline.items.api.item.model.Model model = modelManager.getModel(modelName);

            // Create model attribute
            return new ModelAttribute(name, model);
        }
    }

}
