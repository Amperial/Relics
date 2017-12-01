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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.Trigger;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;

import java.util.function.Predicate;

/**
 * A type of trigger that triggers only when a condition is met.
 *
 * @author Austin Payne
 */
public interface ConditionTrigger<T extends Trigger<T>> extends Trigger<T>, Predicate<TriggerSource> {

}