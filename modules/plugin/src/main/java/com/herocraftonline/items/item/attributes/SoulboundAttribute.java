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
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.Soulbound;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SoulboundAttribute extends BaseAttribute<Soulbound> implements Soulbound {

    private boolean bound;

    public SoulboundAttribute(Item item, String name, String equip, String bound, boolean isBound) {
        super(item, name, DefaultAttributes.SOULBOUND);

        this.bound = isBound;

        setLore((lore, prefix) -> lore.add(prefix + (isBound() ? bound : equip)));
    }

    @Override
    public boolean isBound() {
        return bound;
    }

    @Override
    public void setBound(boolean bound) {
        this.bound = bound;
    }

    @Override
    public boolean canEquip(Player player) {
        return true;
    }

    @Override
    public boolean onEquip(Player player) {
        if (!isBound()) {
            setBound(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onUnEquip(Player player) {
        return false;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setBoolean("bound", isBound());
    }

    public static class Factory extends BaseAttributeFactory<Soulbound> {
        private final String equip;
        private final String bound;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            // Load soulbound text
            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.SOULBOUND);
            equip = ChatColor.translateAlternateColorCodes('&', config.getString("equip", "&4Becomes soulbound on equip!"));
            bound = ChatColor.translateAlternateColorCodes('&', config.getString("bound", "&dThis item is soulbound!"));
        }

        @Override
        public SoulboundAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Create soulbound attribute
            return new SoulboundAttribute(item, name, equip, bound, false);
        }

        @Override
        public SoulboundAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load whether currently bound
            boolean isBound = compound.getBoolean("bound");

            // Create soulbound attribute
            return new SoulboundAttribute(item, name, equip, bound, isBound);
        }
    }

}
