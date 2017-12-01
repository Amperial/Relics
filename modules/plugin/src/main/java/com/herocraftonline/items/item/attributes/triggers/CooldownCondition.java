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
package com.herocraftonline.items.item.attributes.triggers;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.BaseCondition;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.Cooldown;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CooldownCondition extends BaseCondition<Cooldown> implements Cooldown {

    private long duration;
    private long lastUsed;

    public CooldownCondition(Item item, String name, List<String> targets, boolean separate, long duration, long lastUsed) {
        super(item, name, DefaultAttributes.COOLDOWN, targets, separate);

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
    public TriggerResult onTrigger(TriggerSource source) {
        TriggerResult result = super.onTrigger(source);
        if (result != TriggerResult.NOT_TRIGGERED) {
            setOnCooldown(true);
        }
        return result;
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
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            long duration = config.getLong("duration", 0);
            long lastUsed = System.currentTimeMillis();

            // Load cooldown trigger
            return new CooldownCondition(item, name, targets, separate, duration, lastUsed);
        }

        @Override
        public Cooldown loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            long duration = compound.getLong("duration");
            long lastUsed = compound.getLong("lastUsed");

            // Load cooldown trigger
            return new CooldownCondition(item, name, targets, separate, duration, lastUsed);
        }
    }

}
