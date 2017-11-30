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
import com.herocraftonline.items.api.item.trigger.TriggerResult;
import com.herocraftonline.items.api.item.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.trigger.source.entity.HumanEntitySource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import com.herocraftonline.items.util.EncryptUtil;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IdentifiableAttribute extends BaseAttribute<Identifiable> implements Identifiable {

    private final String encryptedItem;

    public IdentifiableAttribute(String name, String encryptedItem) {
        super(name, DefaultAttributes.IDENTIFIABLE);

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

    /* TODO
    @Override
    public void onClick(PlayerInteractEvent event, Item item) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            // Replace item with identified item
            ItemStack identifiable = event.getItem();
            NBTTagCompound identified = NMSHandler.instance().toNBT(identifyItem());
            Optional<ItemStack> updated = NMSHandler.instance().replaceNBT(identifiable, identified);

            // Update item in player inventory
            Player player = event.getPlayer();
            updated.ifPresent(updatedItem -> player.getEquipment().setItemInMainHand(updatedItem));
            player.updateInventory();
        }
    }
    */

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof HumanEntitySource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<HumanEntitySource> humanEntitySource = source.ofType(HumanEntitySource.class);
        if (humanEntitySource.isPresent()) {
            humanEntitySource.get().getEntity().getInventory().addItem(identifyItem());
            return TriggerResult.CONSUME_ITEM;
        }
        return TriggerResult.NOT_TRIGGERED;
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
