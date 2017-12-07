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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.conditions;

import com.herocraftonline.items.api.item.attribute.attributes.trigger.Trigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;

import java.util.function.Predicate;

/**
 * A type of trigger that triggers only when a condition is met.
 *
 * @author Austin Payne
 */
public interface ConditionTrigger<T extends Trigger<T>> extends Trigger<T>, Predicate<TriggerSource> {

}
