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
import com.herocraftonline.items.api.item.attribute.attributes.trigger.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.conditions.Chance;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ChanceCondition extends BaseTrigger<Chance> implements Chance {

    private final Value<Double> chance;

    public ChanceCondition(Item item, String name, List<String> targets, boolean separate, Value<Double> chance) {
        super(item, name, DefaultAttributes.CHANCE_CONDITION, targets, separate);

        this.chance = chance;
    }

    @Override
    public double getChance() {
        return chance.getValue();
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        return test(source) ? super.onTrigger(source) : TriggerResult.TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        chance.saveToNBT(compound);
    }

    public static class Factory extends BaseTriggerFactory<Chance> {
        private static final StoredValue<Double> CHANCE = new StoredValue<>("chance", StoredValue.DOUBLE, 1.0);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Chance loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            Value<Double> chance = CHANCE.loadFromConfig(item, config);

            return new ChanceCondition(item, name, targets, separate, chance);
        }

        @Override
        public Chance loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            Value<Double> chance = CHANCE.loadFromNBT(item, compound);

            return new ChanceCondition(item, name, targets, separate, chance);
        }
    }

}
