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
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.LocationSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.PlayerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.effects.SoundEffect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SoundEffectAttribute extends BaseAttribute<SoundEffect> implements SoundEffect {

    private Sound sound;
    private float volume;
    private float pitch;
    private boolean global;

    public SoundEffectAttribute(Item item, String name, Sound sound, float volume, float pitch, boolean global) {
        super(item, name, DefaultAttributes.SOUND_EFFECT);

        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.global = global;
    }

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return global ? source instanceof LocationSource : source instanceof PlayerSource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        if (global) {
            // Play sound at location to everyone
            Optional<LocationSource> locationSource = source.ofType(LocationSource.class);
            if (locationSource.isPresent()) {
                Location location = locationSource.get().getLocation();
                location.getWorld().playSound(location, getSound(), getVolume(), getPitch());
                return TriggerResult.TRIGGERED;
            }
        } else {
            // Play sound at player's location to player
            Optional<PlayerSource> playerSource = source.ofType(PlayerSource.class);
            if (playerSource.isPresent()) {
                Player player = playerSource.get().getPlayer();
                player.playSound(player.getLocation(), getSound(), getVolume(), getPitch());
                return TriggerResult.TRIGGERED;
            }
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("sound", getSound().name());
        compound.setFloat("volume", getVolume());
        compound.setFloat("pitch", getPitch());
        compound.setBoolean("global", global);
    }

    public static class Factory extends BaseAttributeFactory<SoundEffect> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public SoundEffect loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load sound, volume, pitch, and global
            Sound sound = Sound.valueOf(config.getString("sound"));
            float volume = (float) config.getDouble("volume", 1);
            float pitch = (float) config.getDouble("pitch", 1);
            boolean global = config.getBoolean("global", false);

            // Load sound effect attribute
            return new SoundEffectAttribute(item, name, sound, volume, pitch, global);
        }

        @Override
        public SoundEffect loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load sound, volume, pitch, and global
            Sound sound = Sound.valueOf(compound.getString("sound"));
            float volume = compound.getFloat("volume");
            float pitch = compound.getFloat("pitch");
            boolean global = compound.getBoolean("global");

            // Load sound effect attribute
            return new SoundEffectAttribute(item, name, sound, volume, pitch, global);
        }
    }

}
