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
import com.herocraftonline.items.api.item.attribute.attributes.Identifiable;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import com.herocraftonline.items.util.EncryptUtil;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class IdentifiableAttribute extends BaseAttribute<Identifiable> implements Identifiable {

    private final String encryptedItem;

    public IdentifiableAttribute(String name, String encryptedItem) {
        super(name, DefaultAttribute.IDENTIFIABLE);

        this.encryptedItem = encryptedItem;
    }

    @Override
    public ItemStack identifyItem() {
        return ItemUtil.deserialize(EncryptUtil.decrypt(encryptedItem));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("item", encryptedItem);
    }

    public static class Factory extends BaseAttributeFactory<Identifiable> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Identifiable loadFromConfig(String name, ConfigurationSection config) {
            // Load identifiable item
            String encryptedItem = null;
            if (config.isConfigurationSection("item")) {
                Item item = getPlugin().getItemManager().getItem(config.getConfigurationSection("item"));
                encryptedItem = EncryptUtil.encrypt(ItemUtil.serialize(item));
            }

            // Create identifiable attribute
            return new IdentifiableAttribute(name, encryptedItem);
        }

        @Override
        public Identifiable loadFromNBT(String name, NBTTagCompound compound) {
            // Load identifiable item
            String encryptedItem = compound.getString("item");

            // Create identifiable attribute
            return new IdentifiableAttribute(name, encryptedItem);
        }
    }

}