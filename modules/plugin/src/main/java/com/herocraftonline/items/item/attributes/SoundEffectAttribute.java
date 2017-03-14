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
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.effects.SoundEffect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SoundEffectAttribute extends BaseAttribute<SoundEffect> implements SoundEffect {

    private Sound sound;
    private float volume;
    private float pitch;
    private boolean global;

    public SoundEffectAttribute(String name, Sound sound, float volume, float pitch, boolean global) {
        super(name, DefaultAttribute.SOUND_EFFECT);

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
    public void play(Player player) {
        if (global) {
            player.getWorld().playSound(player.getLocation(), getSound(), getVolume(), getPitch());
        } else {
            player.playSound(player.getLocation(), getSound(), getVolume(), getPitch());
        }
    }

    @Override
    public void stop(Player player) {
        player.stopSound(getSound());
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
        public SoundEffect loadFromConfig(String name, ConfigurationSection config) {
            // Load sound, volume, pitch, and global
            Sound sound = Sound.valueOf(config.getString("sound"));
            float volume = (float) config.getDouble("volume", 1);
            float pitch = (float) config.getDouble("pitch", 1);
            boolean global = config.getBoolean("global", false);

            // Load sound effect attribute
            return new SoundEffectAttribute(name, sound, volume, pitch, global);
        }

        @Override
        public SoundEffect loadFromNBT(String name, NBTTagCompound compound) {
            // Load sound, volume, pitch, and global
            Sound sound = Sound.valueOf(compound.getString("sound"));
            float volume = compound.getFloat("volume");
            float pitch = compound.getFloat("pitch");
            boolean global = compound.getBoolean("global");

            // Load sound effect attribute
            return new SoundEffectAttribute(name, sound, volume, pitch, global);
        }
    }

}
