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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.result;

import com.herocraftonline.items.api.item.attribute.attributes.trigger.Triggerable;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;

import java.util.function.BinaryOperator;

/**
 * Different results that can happen in the triggering process.
 *
 * @author Austin Payne
 */
public enum TriggerResult {
    /**
     * The triggerable was triggered successfully but has no meaningful result.
     */
    TRIGGERED,
    /**
     * The triggerable changed the item which should now be updated.
     */
    UPDATE_ITEM,
    /**
     * The triggerable performed some action that is meant to consume the item.
     */
    CONSUME_ITEM,
    /**
     * The triggerable was not performed.<br>
     * This should only occur if something called {@link Triggerable#onTrigger(TriggerSource)}<br>
     * without calling {@link Triggerable#canTrigger(TriggerSource)}, or if the latter is implemented incorrectly.<br>
     * <br>
     * The triggering should have no further effect because we don't know what went wrong.<br>
     * This should prevent items with triggerable attributes from being consumed if not everything was triggered.
     */
    NOT_TRIGGERED;

    /**
     * Used to get the trigger result that should be handled when dealing with combined target triggers.
     */
    public static final BinaryOperator<TriggerResult> COMBINED = (r1, r2) -> r1.ordinal() > r2.ordinal() ? r1 : r2;

    /**
     * Used to get the trigger result that should be handled when dealing with separate target triggers.
     */
    public static final BinaryOperator<TriggerResult> SEPARATE = (r1, r2) -> r1.equals(r2) ? r1 :
            COMBINED.apply(r1 == NOT_TRIGGERED ? TRIGGERED : r1, r2 == NOT_TRIGGERED ? TRIGGERED : r2);
}
