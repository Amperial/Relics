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

import java.util.Random;

/**
 * A chance condition that can be triggered with a certain chance
 *
 * @author Austin Payne
 */
public interface Chance extends ConditionTrigger<Chance> {

    /**
     * Gets the chance as a value between 0 and 1 to trigger the condition trigger.
     *
     * @return the trigger chance
     */
    double getChance();

    /**
     * Random number generator used to test the chance condition.
     */
    Random RANDOM = new Random();

    @Override
    default boolean test(TriggerSource source) {
        return getChance() > RANDOM.nextDouble();
    }

}
