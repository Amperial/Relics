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

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.Trigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;

import java.util.List;

/**
 * A base condition trigger implementation to simplify the creation of condition triggers.<br>
 * Handles testing a condition as a requirement to trigger the target triggerable attributes.
 *
 * @author Austin Payne
 */
public abstract class BaseCondition<T extends Trigger<T>> extends BaseTrigger<T> implements ConditionTrigger<T> {

    public BaseCondition(Item item, String name, AttributeType<T> type, List<String> targets, boolean separate) {
        super(item, name, type, targets, separate);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return test(source) && super.canTrigger(source);
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        return test(source) ? super.onTrigger(source) : TriggerResult.NOT_TRIGGERED;
    }

}
