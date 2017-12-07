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
package com.herocraftonline.items.item.attributes.triggers.effects;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.LivingEntitySource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.effects.HealEffect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Optional;

public class HealEffectAttribute extends BaseAttribute<HealEffect> implements HealEffect {

    private Value<Double> heal;

    public HealEffectAttribute(Item item, String name, Value<Double> heal) {
        super(item, name, DefaultAttributes.HEAL_EFFECT);

        this.heal = heal;
    }

    @Override
    public double getHeal() {
        double amount = heal.getValue();
        return amount > 0 ? amount : 0;
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
                return TriggerResult.TRIGGERED;
            }
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        heal.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<HealEffect> {
        private static final StoredValue<Double> HEAL = new StoredValue<>("heal", StoredValue.DOUBLE, 0.0);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public HealEffect loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load heal amount
            Value<Double> heal = HEAL.loadFromConfig(item, config);

            // Load heal effect attribute
            return new HealEffectAttribute(item, name, heal);
        }

        @Override
        public HealEffect loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load heal amount
            Value<Double> heal = HEAL.loadFromNBT(item, compound);

            // Load heal effect attribute
            return new HealEffectAttribute(item, name, heal);
        }
    }

}
