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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;

/**
 * A condition trigger that can only trigger once every period of time.
 *
 * @author Austin Payne
 */
public interface Cooldown extends ConditionTrigger<Cooldown> {

    /**
     * Gets the duration of the cooldown between triggering target triggerables.
     *
     * @return the cooldown's duration
     */
    long getDuration();

    /**
     * Sets the duration of the cooldown between triggering target triggerables.
     *
     * @param duration the cooldown's duration
     */
    void setDuration(long duration);

    /**
     * Gets the last time the cooldown was used.
     *
     * @return the cooldown's last used time
     */
    long getLastUsed();

    /**
     * Sets the last time the cooldown was used.
     *
     * @param lastUsed the cooldown's last used time
     */
    void setLastUsed(long lastUsed);

    /**
     * Checks if the trigger is on cooldown.
     *
     * @return if the trigger is on cooldown
     */
    boolean isOnCooldown();

    /**
     * Sets if the trigger is on cooldown.
     *
     * @param onCooldown if the trigger is on cooldown
     */
    void setOnCooldown(boolean onCooldown);

    @Override
    default boolean test(TriggerSource source) {
        return !isOnCooldown();
    }

}
