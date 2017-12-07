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
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.effects.PotionEffect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class PotionEffectAttribute extends BaseAttribute<PotionEffect> implements PotionEffect {

    private final PotionEffectType type;
    private final Value<Integer> duration;
    private final Value<Integer> amplifier;
    private final boolean ambient;
    private final boolean particles;
    private final Color color;

    public PotionEffectAttribute(Item item, String name, PotionEffectType type, Value<Integer> duration, Value<Integer> amplifier, boolean ambient, boolean particles, Color color) {
        super(item, name, DefaultAttributes.POTION_EFFECT);

        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
        this.color = color;
    }

    @Override
    public org.bukkit.potion.PotionEffect getEffect() {
        return new org.bukkit.potion.PotionEffect(type, duration.getValue(), amplifier.getValue(), ambient, particles, color);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof LivingEntitySource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<LivingEntitySource> livingEntitySource = source.ofType(LivingEntitySource.class);
        if (livingEntitySource.isPresent()) {
            getEffect().apply(livingEntitySource.get().getEntity());
            return TriggerResult.TRIGGERED;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        org.bukkit.potion.PotionEffect effect = getEffect();
        compound.setString("potion-type", effect.getType().getName());
        duration.saveToNBT(compound);
        amplifier.saveToNBT(compound);
        compound.setBoolean("ambient", effect.isAmbient());
        compound.setBoolean("particles", effect.hasParticles());
        if (effect.getColor() != null) {
            compound.setInt("color", effect.getColor().asRGB());
        }
    }

    public static class Factory extends BaseAttributeFactory<PotionEffect> {
        private static final StoredValue<Integer> DURATION = new StoredValue<>("duration", StoredValue.INTEGER, 200);
        private static final StoredValue<Integer> AMPLIFIER = new StoredValue<>("amplifier", StoredValue.INTEGER, 1);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public PotionEffect loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load potion effect
            PotionEffectType type = PotionEffectType.getByName(config.getString("potion-type"));
            Value<Integer> duration = DURATION.loadFromConfig(item, config);
            Value<Integer> amplifier = AMPLIFIER.loadFromConfig(item, config);
            boolean ambient = config.getBoolean("ambient", true);
            boolean particles = config.getBoolean("particles", true);
            Color color = config.getColor("color", null);

            // Load potion effect attribute
            return new PotionEffectAttribute(item, name, type, duration, amplifier, ambient, particles, color);
        }

        @Override
        public PotionEffect loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load potion effect
            PotionEffectType type = PotionEffectType.getByName(compound.getString("potion-type"));
            Value<Integer> duration = DURATION.loadFromNBT(item, compound);
            Value<Integer> amplifier = AMPLIFIER.loadFromNBT(item, compound);
            boolean ambient = compound.getBoolean("ambient");
            boolean particles = compound.getBoolean("particles");
            Color color = compound.hasKey("color") ? Color.fromRGB(compound.getInt("color")) : null;

            // Load potion effect attribute
            return new PotionEffectAttribute(item, name, type, duration, amplifier, ambient, particles, color);
        }
    }

}
