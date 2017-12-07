/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.effects;

import org.bukkit.Note;
import org.bukkit.Sound;

public interface SoundEffect extends Effect<SoundEffect> {

    Sound getSound();

    float getVolume();

    float getPitch();

    /**
     * Gets the minimum volume needed for a sound to reach a certain distance.<br>
     * A sound can always be heard within 16 blocks regardless of volume.<br>
     * This method is intended for calculating the volume needed for larger distances.
     *
     * @param maxDistance the max distance that the sound should reach
     * @return the volume for the max distance
     */
    static float getVolumeForDistance(float maxDistance) {
        return Math.abs(maxDistance) / 16f;
    }

    /**
     * Gets the required pitch to play a certain note.<br>
     * Calls {@link #getNotePitch(byte)} with the note's id.
     *
     * @param note the note being played
     * @return the pitch of the note
     */
    @SuppressWarnings("deprecation")
    static float getNotePitch(Note note) {
        return getNotePitch(note.getId());
    }

    /**
     * Gets the required pitch to play a note of a certain id.<br>
     * Calculated with the formula {@code 2^((note-12)/12)}.<br>
     * The note 12 corresponds to a pitch of 1, an F#.
     *
     * @param note the id of the note being played
     * @return the pitch of the note
     */
    static float getNotePitch(byte note) {
        return (float) Math.pow(2, (note - 12.0) / 12.0);
    }

    /**
     * Gets the required pitch to play a note of a certain frequency.<br>
     * A frequency of 739.99hz corresponds to a pitch of 1, an F#.<br>
     * The pitch of other frequencies are calculated from this value.
     *
     * @param hertz the frequency of the note being played
     * @return the pitch of the note
     */
    static float getNotePitch(float hertz) {
        return hertz / 739.99f;
    }

}
