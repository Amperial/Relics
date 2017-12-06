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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.projectiles.Velocity;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

public class VelocityAttribute extends BaseAttribute<Velocity> implements Velocity {

    private double value;
    private boolean multiply;

    public VelocityAttribute(Item item, String name, double value, boolean multiply) {
        super(item, name, DefaultAttributes.VELOCITY);

        this.value = value;
        this.multiply = multiply;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean isMultiplier() {
        return multiply;
    }

    @Override
    public void setMultiplier(boolean multiply) {
        this.multiply = multiply;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setDouble("value", getValue());
        compound.setBoolean("multiply", isMultiplier());
    }

    public static class Factory extends BaseAttributeFactory<Velocity> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Velocity loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load velocity details
            double value = config.getDouble("value", 1);
            boolean multiply = config.getBoolean("multiply", true);

            // Load velocity attribute
            return new VelocityAttribute(item, name, value, multiply);
        }

        @Override
        public Velocity loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load velocity details
            double value = compound.getDouble("value");
            boolean multiply = compound.getBoolean("multiply");

            // Load velocity attribute
            return new VelocityAttribute(item, name, value, multiply);
        }
    }

}
