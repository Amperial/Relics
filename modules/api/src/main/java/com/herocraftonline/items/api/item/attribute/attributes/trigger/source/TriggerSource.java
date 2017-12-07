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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.source;

import com.herocraftonline.items.api.item.Item;

import java.util.Optional;

/**
 * Used to pass information through to trigger code.
 *
 * @author Austin Payne
 */
public interface TriggerSource {

    /**
     * Gets the item related to the trigger source.
     *
     * @return the source item
     */
    Item getItem();

    /**
     * Checks if the trigger source is of the given type.
     *
     * @param type the type
     * @return {@code true} if the trigger source is the given type, else {@code false}
     */
    default boolean isType(Class type) {
        return ofType(type).isPresent();
    }

    /**
     * Returns an {@link Optional} containing {@link this} casted to the given type,<br>
     * Or {@link Optional#empty()} if casting to the given type is not possible.
     *
     * @param type the class of the type to cast to
     * @param <T>  the type being casted to
     * @return the {@link Optional} instance
     */
    @SuppressWarnings("unchecked")
    default <T extends TriggerSource> Optional<T> ofType(Class<T> type) {
        return Optional.ofNullable(type.isAssignableFrom(getClass()) ? (T) this : null);
    }

}
