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
package com.herocraftonline.items.item.attributes.triggers.conditions;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.BaseCondition;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.CommandSenderSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class PermissionCondition extends BaseCondition<PermissionCondition> {

    private final Permission permission;

    public PermissionCondition(Item item, String name, List<String> targets, boolean separate, Permission permission) {
        super(item, name, DefaultAttributes.PERMISSION_CONDITION, targets, separate);

        this.permission = permission;
        if (Bukkit.getServer().getPluginManager().getPermission(permission.getName()) == null) {
            Bukkit.getServer().getPluginManager().addPermission(permission);
        }
    }

    @Override
    public boolean test(TriggerSource triggerSource) {
        return triggerSource.ofType(CommandSenderSource.class).map(commandSender -> commandSender.getSender().hasPermission(permission)).orElse(false);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("permission", permission.getName());
        compound.setString("default", permission.getDefault().name());
    }

    public static class Factory extends BaseTriggerFactory<PermissionCondition> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public PermissionCondition loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            String permission = config.getString("permission");
            PermissionDefault permissionDefault = PermissionDefault.valueOf(config.getString("default", "OP"));

            return new PermissionCondition(item, name, targets, separate, new Permission(permission, permissionDefault));
        }

        @Override
        public PermissionCondition loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            String permission = compound.getString("permission");
            PermissionDefault permissionDefault = PermissionDefault.valueOf(compound.getString("default"));

            return new PermissionCondition(item, name, targets, separate, new Permission(permission, permissionDefault));
        }
    }

}
