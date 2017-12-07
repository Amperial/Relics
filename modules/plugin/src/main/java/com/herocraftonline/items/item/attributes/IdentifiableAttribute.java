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
import com.herocraftonline.items.api.item.attribute.attributes.Identifiable;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.HumanEntitySource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import com.herocraftonline.items.util.EncryptUtil;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IdentifiableAttribute extends BaseAttribute<Identifiable> implements Identifiable {

    private final String encryptedItem;

    public IdentifiableAttribute(Item item, String name, String encryptedItem) {
        super(item, name, DefaultAttributes.IDENTIFIABLE);

        this.encryptedItem = encryptedItem;
    }

    @Override
    public Optional<ItemStack> identifyItem() {
        return ItemUtil.deserialize(EncryptUtil.decrypt(encryptedItem));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("item", encryptedItem);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof HumanEntitySource && encryptedItem != null && !encryptedItem.isEmpty();
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<HumanEntitySource> humanEntitySource = source.ofType(HumanEntitySource.class);
        if (humanEntitySource.isPresent()) {
            Optional<ItemStack> identified = identifyItem();
            if (identified.isPresent()) {
                // TODO: We might want to have a way to replace item in inventory instead
                humanEntitySource.get().getEntity().getInventory().addItem(identified.get());
                // NMSHandler.instance().replaceNBT(identifiable, identified);
                return TriggerResult.CONSUME_ITEM;
            }
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    public static class Factory extends BaseAttributeFactory<Identifiable> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Identifiable loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load identifiable item
            String encryptedItem = null;
            if (config.isConfigurationSection("item")) {
                Item identifiable = getPlugin().getItemManager().getItem(config.getConfigurationSection("item"));
                encryptedItem = EncryptUtil.encrypt(ItemUtil.serialize(identifiable));
            }

            // Create identifiable attribute
            return new IdentifiableAttribute(item, name, encryptedItem);
        }

        @Override
        public Identifiable loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load identifiable item
            String encryptedItem = compound.getString("item");

            // Create identifiable attribute
            return new IdentifiableAttribute(item, name, encryptedItem);
        }
    }

}
