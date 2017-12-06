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
import com.herocraftonline.items.api.item.attribute.attributes.Model;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.model.ModelManager;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ModelAttribute extends BaseAttribute<Model> implements Model {

    private final com.herocraftonline.items.api.item.model.Model model;
    private final int priority;

    public ModelAttribute(Item item, String name, com.herocraftonline.items.api.item.model.Model model, int priority) {
        super(item, name, DefaultAttributes.MODEL);

        this.model = model;
        this.priority = priority;
    }

    @Override
    public void apply(ItemStack item) {
        model.apply(item);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("model", model.getName());
        compound.setInt("priority", getPriority());
    }

    public static class Factory extends BaseAttributeFactory<Model> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Model loadFromConfig(Item item, String name, ConfigurationSection config) {
            ModelManager modelManager = getPlugin().getModelManager();

            // Load model
            String modelName = config.getString("model");
            com.herocraftonline.items.api.item.model.Model model = modelManager.getModel(modelName);
            int priority = config.getInt("priority", 0);

            // Create model attribute
            return new ModelAttribute(item, name, model, priority);
        }

        @Override
        public Model loadFromNBT(Item item, String name, NBTTagCompound compound) {
            ModelManager modelManager = getPlugin().getModelManager();

            // Load model
            String modelName = compound.getString("model");
            com.herocraftonline.items.api.item.model.Model model = modelManager.getModel(modelName);
            int priority = compound.getInt("priority");

            // Create model attribute
            return new ModelAttribute(item, name, model, priority);
        }
    }

}
