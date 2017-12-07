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
import com.herocraftonline.items.api.item.attribute.attributes.trigger.conditions.BaseCondition;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.conditions.Cooldown;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CooldownCondition extends BaseCondition<Cooldown> implements Cooldown {

    private Value<Long> duration;
    private long lastUsed;

    public CooldownCondition(Item item, String name, List<String> targets, boolean separate, Value<Long> duration, long lastUsed) {
        super(item, name, DefaultAttributes.COOLDOWN, targets, separate);

        this.duration = duration;
        this.lastUsed = lastUsed;
    }

    @Override
    public long getDuration() {
        return duration.getValue();
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
        duration.saveToNBT(compound);
        compound.setLong("lastUsed", getLastUsed());
    }

    public static class Factory extends BaseTriggerFactory<Cooldown> {
        private static final StoredValue<Long> DURATION = new StoredValue<>("duration", StoredValue.LONG, 0L);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Cooldown loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            Value<Long> duration = DURATION.loadFromConfig(item, config);
            long lastUsed = System.currentTimeMillis();

            // Load cooldown trigger
            return new CooldownCondition(item, name, targets, separate, duration, lastUsed);
        }

        @Override
        public Cooldown loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            Value<Long> duration = DURATION.loadFromNBT(item, compound);
            long lastUsed = compound.getLong("lastUsed");

            // Load cooldown trigger
            return new CooldownCondition(item, name, targets, separate, duration, lastUsed);
        }
    }

}
