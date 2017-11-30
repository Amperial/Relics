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
import com.herocraftonline.items.api.item.trigger.TriggerResult;
import com.herocraftonline.items.api.item.trigger.Triggerable;
import com.herocraftonline.items.api.item.trigger.source.LocationSource;
import com.herocraftonline.items.api.item.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.trigger.source.entity.LivingEntitySource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SmiteAttribute extends BaseAttribute<SmiteAttribute> implements Triggerable {

    private static final Set<Material> AIR;

    static {
        AIR = new HashSet<>();
        AIR.add(Material.AIR);
    }

    private final Permission permission;
    private final Set<UUID> gods;
    private final int range;

    public SmiteAttribute(String name, Permission permission, Set<UUID> gods, int range) {
        super(name, DefaultAttributes.SMITE);

        this.permission = permission;
        this.gods = gods;
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public boolean canSmite(Player player) {
        return gods.contains(player.getUniqueId()) || player.hasPermission(permission);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof LocationSource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        // Get location source to be used for lightning strike location
        Optional<LocationSource> locationSource = source.ofType(LocationSource.class);
        if (locationSource.isPresent()) {
            Location location = locationSource.get().getLocation();

            // If source is a living entity, update location to where they're looking
            // TODO: Move this code to a trigger that converts living entity source to location source of target block
            Optional<LivingEntitySource> livingEntitySource = source.ofType(LivingEntitySource.class);
            if (livingEntitySource.isPresent()) {
                LivingEntity entity = livingEntitySource.get().getEntity();
                if (entity instanceof Player && !canSmite((Player) entity)) {
                    return TriggerResult.NOT_TRIGGERED;
                }
                location = entity.getTargetBlock(AIR, getRange()).getLocation();
            }
            location.getWorld().strikeLightning(location);
            return TriggerResult.SUCCESS;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("range", getRange());
    }

    public static class Factory extends BaseAttributeFactory<SmiteAttribute> {
        private final Permission permission = new Permission("relics.attribute.smite", PermissionDefault.FALSE);
        private final Set<UUID> gods = new HashSet<>();

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.SMITE);
            gods.addAll(config.getStringList("gods").stream().map(UUID::fromString).collect(Collectors.toList()));
        }

        @Override
        public SmiteAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load range
            int range = Math.abs(config.getInt("range", 64));

            // Create smite attribute
            return new SmiteAttribute(name, permission, gods, range);
        }

        @Override
        public SmiteAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load range
            int range = compound.getInt("range");

            // Create smite attribute
            return new SmiteAttribute(name, permission, gods, range);
        }
    }

}
