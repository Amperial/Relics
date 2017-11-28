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

import com.herocraftonline.items.api.item.Item;

import java.util.Optional;

/**
 * Used to pass information through to trigger code.
 */
public class TriggerSource {

    private final Item item;

    public TriggerSource(Item item) {
        this.item = item;
    }

    /**
     * Gets the item related to the trigger source.
     *
     * @return the source item
     */
    public Item getItem() {
        return item;
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
    public <T> Optional<T> ofType(Class<T> type) {
        return Optional.ofNullable(type.isAssignableFrom(getClass()) ? (T) this : null);
    }

}
