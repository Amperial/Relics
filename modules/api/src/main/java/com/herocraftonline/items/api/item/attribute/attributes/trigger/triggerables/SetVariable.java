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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.Triggerable;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.item.ItemSource;

import java.util.Optional;

/**
 * A triggerable attribute that sets the level of a variable on the source item.
 *
 * @author Austin Payne
 */
public interface SetVariable extends Triggerable<SetVariable> {

    /**
     * Gets the variable to be set.
     *
     * @return the variable name
     */
    String getVariable();

    /**
     * Gets the amount to set the variable to.
     *
     * @return the increase amount
     */
    double getAmount();

    @Override
    default boolean canTrigger(TriggerSource source) {
        if (getVariable() == null || getVariable().isEmpty() || getAmount() == 0) {
            return false;
        }
        Item item = source.ofType(ItemSource.class).map(ItemSource::getSourceItem).orElse(source.getItem());
        return item.hasVariable(getVariable(), Integer.class) || item.hasVariable(getVariable(), Double.class);
    }

    @Override
    default TriggerResult onTrigger(TriggerSource source) {
        if (getVariable() == null || getVariable().isEmpty() || getAmount() == 0) {
            return TriggerResult.NOT_TRIGGERED;
        }
        Item item = source.ofType(ItemSource.class).map(ItemSource::getSourceItem).orElse(source.getItem());
        Optional<Integer> intValue = item.getValue(getVariable(), Integer.class);
        if (intValue.isPresent()) {
            Integer value = (int) getAmount();
            item.setValue(getVariable(), value);
            return TriggerResult.UPDATE_ITEM;
        }
        Optional<Double> doubleValue = item.getValue(getVariable(), Double.class);
        if (doubleValue.isPresent()) {
            item.setValue(getVariable(), getAmount());
            return TriggerResult.UPDATE_ITEM;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

}
