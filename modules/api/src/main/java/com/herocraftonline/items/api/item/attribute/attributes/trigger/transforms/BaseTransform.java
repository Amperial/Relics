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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.transforms;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;

import java.util.List;

/**
 * A base transform trigger implementation to simplify the creation of transform triggers.<br>
 * Handles transforming a trigger source to another type before triggering the target triggerable attributes.
 *
 * @author Austin Payne
 */
public abstract class BaseTransform<T extends TransformTrigger<T>> extends BaseTrigger<T> implements TransformTrigger<T> {

    public BaseTransform(Item item, String name, AttributeType<T> type, List<String> targets, boolean separate) {
        super(item, name, type, targets, separate);
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return transform(source).map(super::canTrigger).orElse(false);
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        return transform(source).map(super::onTrigger).orElse(TriggerResult.NOT_TRIGGERED);
    }

}
