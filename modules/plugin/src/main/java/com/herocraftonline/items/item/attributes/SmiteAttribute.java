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
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SmiteAttribute extends BaseAttribute<SmiteAttribute> implements Clickable {

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
    public void onClick(PlayerInteractEvent event, Item item) {
        if (item.isEquipped() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (canSmite(player)) {
                player.getWorld().strikeLightning(player.getTargetBlock(AIR, getRange()).getLocation());
            }
        }
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
