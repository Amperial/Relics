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
package com.herocraftonline.items.api.storage.value;

public class StaticValue<T> implements Value<T> {

    private final T value;

    public StaticValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

}
