/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.Equippable;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.Soulbound;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SoulboundAttribute extends BasicAttribute implements Soulbound, Equippable {

    private boolean bound;

    public SoulboundAttribute(String name, String equip, String bound, boolean isBound) {
        super(name, DefaultAttributeType.SOULBOUND);

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

    public static class Factory extends BasicAttributeFactory<SoulboundAttribute> {

        private final String equip;
        private final String bound;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            // Load soulbound text
            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.SOULBOUND);
            equip = ChatColor.translateAlternateColorCodes('&', config.getString("equip", "&4Becomes soulbound on equip!"));
            bound = ChatColor.translateAlternateColorCodes('&', config.getString("bound", "&dThis item is soulbound!"));
        }

        @Override
        public SoulboundAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Create soulbound attribute
            return new SoulboundAttribute(name, equip, bound, false);
        }

        @Override
        public SoulboundAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load whether currently bound
            boolean isBound = compound.getBoolean("bound");

            // Create soulbound attribute
            return new SoulboundAttribute(name, equip, bound, isBound);
        }

    }

}
