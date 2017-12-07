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

import com.herocraftonline.items.api.item.attribute.attributes.trigger.Trigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;

import java.util.Optional;

/**
 * A type of trigger that triggers with a transformed trigger source.
 *
 * @author Austin Payne
 */
public interface TransformTrigger<T extends Trigger<T>> extends Trigger<T> {

    Optional<TriggerSource> transform(TriggerSource source);

    default <S extends TriggerSource> Optional<S> transform(TriggerSource source, Class<S> type) {
        return transform(source).map(transformed -> transformed.ofType(type).orElse(null));
    }

}
