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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.Triggerable;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.LocationSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

public class SmiteAttribute extends BaseAttribute<SmiteAttribute> implements Triggerable<SmiteAttribute> {

    public SmiteAttribute(Item item, String name) {
        super(item, name, DefaultAttributes.SMITE);
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
            location.getWorld().strikeLightning(location);
            return TriggerResult.TRIGGERED;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    public static class Factory extends BaseAttributeFactory<SmiteAttribute> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public SmiteAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            return new SmiteAttribute(item, name);
        }

        @Override
        public SmiteAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            return new SmiteAttribute(item, name);
        }
    }

}
