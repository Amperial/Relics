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
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public class LaunchEntityAttribute extends BaseAttribute<LaunchEntityAttribute> {

    private final EntityType entity;
    private final double velocity;

    public LaunchEntityAttribute(Item item, String name, EntityType entity, double velocity) {
        super(item, name, DefaultAttributes.LAUNCH_ENTITY);

        this.entity = entity;
        this.velocity = velocity;
    }

    public EntityType getEntity() {
        return entity;
    }

    public double getVelocity() {
        return velocity;
    }

    /* TODO
    @Override
    public void onClick(PlayerInteractEvent event, Item item) {
        if (item.isEquipped() && event.getAction() == Action.LEFT_CLICK_AIR) {
            Location loc = event.getPlayer().getEyeLocation();
            Vector direction = loc.getDirection().normalize();
            loc.getWorld().spawnEntity(loc.add(direction), getEntity()).setVelocity(direction.multiply(getVelocity()));
        }
    }
    */

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("entity", getEntity().name());
        compound.setDouble("velocity", getVelocity());
    }

    public static class Factory extends BaseAttributeFactory<LaunchEntityAttribute> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public LaunchEntityAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(config.getString("entity", "PRIMED_TNT"));
            double velocity = config.getDouble("velocity", 1);

            // Create launch entity attribute
            return new LaunchEntityAttribute(item, name, entity, velocity);
        }

        @Override
        public LaunchEntityAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(compound.getString("entity"));
            double velocity = compound.getDouble("velocity");

            // Create launch entity attribute
            return new LaunchEntityAttribute(item, name, entity, velocity);
        }
    }

}
