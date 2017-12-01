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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.effects.HealEffect;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity.LivingEntitySource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Optional;

public class HealEffectAttribute extends BaseAttribute<HealEffect> implements HealEffect {

    private double heal;

    public HealEffectAttribute(Item item, String name, double heal) {
        super(item, name, DefaultAttributes.HEAL_EFFECT);

        setHeal(heal);
    }

    @Override
    public double getHeal() {
        return heal;
    }

    @Override
    public void setHeal(double heal) {
        this.heal = heal < 0 ? 0 : heal;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source.ofType(LivingEntitySource.class).map(LivingEntitySource::getEntity)
                .map(entity -> getHeal() > 0 && entity.getMaxHealth() > entity.getHealth()).orElse(false);
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<LivingEntitySource> livingEntitySource = source.ofType(LivingEntitySource.class);
        if (livingEntitySource.isPresent()) {
            LivingEntity entity = livingEntitySource.get().getEntity();
            if (getHeal() > 0 && entity.getMaxHealth() > entity.getHealth()) {
                entity.setHealth(Math.min(entity.getHealth() + getHeal(), entity.getMaxHealth()));
                return TriggerResult.SUCCESS;
            }
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setDouble("heal", getHeal());
    }

    public static class Factory extends BaseAttributeFactory<HealEffect> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public HealEffect loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load heal amount
            double heal = config.getDouble("heal", 0);

            // Load heal effect attribute
            return new HealEffectAttribute(item, name, heal);
        }

        @Override
        public HealEffect loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load heal amount
            double heal = compound.getDouble("heal");

            // Load heal effect attribute
            return new HealEffectAttribute(item, name, heal);
        }
    }

}
