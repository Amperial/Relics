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
import com.herocraftonline.items.api.item.attribute.attributes.triggers.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.Chance;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ChanceCondition extends BaseTrigger<Chance> implements Chance {

    private final double chance;

    public ChanceCondition(Item item, String name, List<String> targets, boolean separate, double chance) {
        super(item, name, DefaultAttributes.CHANCE_CONDITION, targets, separate);

        this.chance = chance;
    }

    @Override
    public double getChance() {
        return chance;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        return test(source) ? super.onTrigger(source) : TriggerResult.TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setDouble("chance", getChance());
    }

    public static class Factory extends BaseTriggerFactory<Chance> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Chance loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            double chance = config.getDouble("chance", 1);

            return new ChanceCondition(item, name, targets, separate, chance);
        }

        @Override
        public Chance loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            double chance = compound.getDouble("chance");

            return new ChanceCondition(item, name, targets, separate, chance);
        }
    }

}
