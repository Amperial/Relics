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
import com.herocraftonline.items.api.item.Clickable;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LaunchEntityAttribute extends BaseAttribute<LaunchEntityAttribute> implements Clickable {

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
    public void onClick(PlayerInteractEvent event, Item item) {
        if (item.isEquipped() && event.getAction() == Action.LEFT_CLICK_AIR) {
            Location loc = event.getPlayer().getEyeLocation();
            loc.getWorld().spawnEntity(loc, getEntity()).setVelocity(loc.getDirection().normalize().multiply(velocity));
        }
    }

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
        public LaunchEntityAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(config.getString("entity", "PRIMED_TNT"));
            double velocity = config.getDouble("velocity", 1);

            // Create launch entity attribute
            return new LaunchEntityAttribute(name, entity, velocity);
        }

        @Override
        public LaunchEntityAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load entity and velocity
            EntityType entity = EntityType.valueOf(compound.getString("entity"));
            double velocity = compound.getDouble("velocity");

            // Create launch entity attribute
            return new LaunchEntityAttribute(name, entity, velocity);
        }
    }

}
