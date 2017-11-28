/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.trigger;

/**
 * Handles triggering things by trigger sources and getting the result.
 *
 * @author Austin Payne
 */
public interface Triggerable {

    /**
     * Checks if the triggerable can be triggered by the given source.<br>
     * {@link this#onTrigger(TriggerSource)} should not be called unless this returns {@code true}.
     *
     * @param source the trigger source
     * @return {@code true} if the triggerable can be triggered, else {@code false}
     */
    boolean canTrigger(TriggerSource source);

    /**
     * Triggers the triggerable by the given source and gets the result.<br>
     * {@link this#canTrigger(TriggerSource)} should return {@code true} before calling this.
     *
     * @param source the trigger source
     * @return the trigger result
     */
    TriggerResult onTrigger(TriggerSource source);

}
