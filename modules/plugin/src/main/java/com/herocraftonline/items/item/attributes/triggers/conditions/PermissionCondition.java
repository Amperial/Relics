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
package com.herocraftonline.items.item.attributes.triggers.conditions;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.BaseCondition;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.Permission;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class PermissionCondition extends BaseCondition<Permission> implements Permission {

    private final org.bukkit.permissions.Permission permission;

    public PermissionCondition(Item item, String name, List<String> targets, boolean separate, org.bukkit.permissions.Permission permission) {
        super(item, name, DefaultAttributes.PERMISSION_CONDITION, targets, separate);

        this.permission = permission;
        if (Bukkit.getServer().getPluginManager().getPermission(permission.getName()) == null) {
            Bukkit.getServer().getPluginManager().addPermission(permission);
        }
    }

    @Override
    public org.bukkit.permissions.Permission getPermission() {
        return permission;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("permission", getPermission().getName());
        compound.setString("default", getPermission().getDefault().name());
    }

    public static class Factory extends BaseTriggerFactory<Permission> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public PermissionCondition loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            String permissionName = config.getString("permission");
            PermissionDefault permissionDefault = PermissionDefault.valueOf(config.getString("default", "OP"));
            org.bukkit.permissions.Permission permission = new org.bukkit.permissions.Permission(permissionName, permissionDefault);

            return new PermissionCondition(item, name, targets, separate, permission);
        }

        @Override
        public PermissionCondition loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            String permissionName = compound.getString("permission");
            PermissionDefault permissionDefault = PermissionDefault.valueOf(compound.getString("default"));
            org.bukkit.permissions.Permission permission = new org.bukkit.permissions.Permission(permissionName, permissionDefault);

            return new PermissionCondition(item, name, targets, separate, permission);
        }
    }

}
