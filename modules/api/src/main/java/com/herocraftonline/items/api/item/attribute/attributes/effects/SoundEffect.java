package com.herocraftonline.items.api.item.attribute.attributes.effects;

import org.bukkit.Sound;

public interface SoundEffect extends Effect<SoundEffect> {

    Sound getSound();

    float getVolume();

    float getPitch();

}
