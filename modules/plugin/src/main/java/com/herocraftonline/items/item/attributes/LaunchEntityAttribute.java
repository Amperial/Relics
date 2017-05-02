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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.projectiles.LaunchEntity;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.TriggerableAttribute;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LaunchEntityAttribute extends TriggerableAttribute<LaunchEntity> implements LaunchEntity {

    private final EntityType entity;
    private final double velocity;

    public LaunchEntityAttribute(String name, EntityType entity, double velocity) {
        super(name, DefaultAttribute.LAUNCH_ENTITY);

        this.entity = entity;
        this.velocity = velocity;
    }

    public EntityType getEntity() {
        return entity;
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public TriggerResult execute(Entity entity) {
        Location loc = entity instanceof LivingEntity ? ((LivingEntity) entity).getEyeLocation() : entity.getLocation();
        Vector dir = loc.getDirection().normalize();
        loc.getWorld().spawnEntity(loc.add(dir), getEntity()).setVelocity(dir.multiply(getVelocity()));
        return TriggerResult.NONE;
    }

    @Override
    public TriggerResult execute(Entity entity, Entity target) {
        return execute(entity);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("entity", getEntity().name());
        compound.setDouble("velocity", getVelocity());
    }

    public static class Factory extends BaseAttributeFactory<LaunchEntity> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public LaunchEntity loadFromConfig(String name, ConfigurationSection config) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(config.getString("entity", "PRIMED_TNT"));
            double velocity = config.getDouble("velocity", 1);

            // Create launch entity attribute
            return new LaunchEntityAttribute(name, entity, velocity);
        }

        @Override
        public LaunchEntity loadFromNBT(String name, NBTTagCompound compound) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(compound.getString("entity"));
            double velocity = compound.getDouble("velocity");

            // Create launch entity attribute
            return new LaunchEntityAttribute(name, entity, velocity);
        }
    }

}
