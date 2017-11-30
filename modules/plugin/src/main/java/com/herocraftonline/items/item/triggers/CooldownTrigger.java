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
package com.herocraftonline.items.item.triggers;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.Cooldown;
import com.herocraftonline.items.api.item.trigger.TriggerResult;
import com.herocraftonline.items.api.item.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

public class CooldownTrigger extends BaseTrigger<Cooldown> implements Cooldown {

    private long duration;
    private long lastUsed;

    public CooldownTrigger(Item item, String name, Set<String> targets, long duration, long lastUsed) {
        super(item, name, DefaultAttributes.COOLDOWN, targets);

        this.duration = duration;
        this.lastUsed = lastUsed;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public long getLastUsed() {
        return lastUsed;
    }

    @Override
    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean isOnCooldown() {
        return System.currentTimeMillis() - getLastUsed() < getDuration();
    }

    @Override
    public void setOnCooldown(boolean onCooldown) {
        setLastUsed(onCooldown ? System.currentTimeMillis() : 0);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return !isOnCooldown();
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        if (canTrigger(source)) {
            TriggerResult result = super.onTrigger(source);
            if (result != TriggerResult.NOT_TRIGGERED) {
                setOnCooldown(true);
            }
            return result;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setLong("duration", getDuration());
        compound.setLong("lastUsed", getLastUsed());
    }

    public static class Factory extends BaseTriggerFactory<Cooldown> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Cooldown loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load targets, duration, and lastUsed
            Set<String> targets = loadTargetsFromConfig(config);
            long duration = config.getLong("duration", 0);
            long lastUsed = System.currentTimeMillis();

            // Load cooldown trigger
            return new CooldownTrigger(item, name, targets, duration, lastUsed);
        }

        @Override
        public Cooldown loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load targets, duration, and lastUsed
            Set<String> targets = loadTargetsFromNBT(compound);
            long duration = compound.getLong("duration");
            long lastUsed = compound.getLong("lastUsed");

            // Load cooldown trigger
            return new CooldownTrigger(item, name, targets, duration, lastUsed);
        }
    }

}
