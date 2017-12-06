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
package com.herocraftonline.items.api.storage.value;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;

public class StaticValue<T> implements Value<T> {

    private final String key;
    private final T value;

    public StaticValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public StaticValue(T value) {
        this(null, value);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        if (key != null) {
            if (value instanceof Boolean) {
                compound.setBoolean(key, (Boolean) value);
            } else if (value instanceof Double) {
                compound.setDouble(key, (Double) value);
            } else if (value instanceof Integer) {
                compound.setInt(key, (Integer) value);
            } else if (value instanceof Long) {
                compound.setLong(key, (Long) value);
            } else if (value instanceof String) {
                compound.setString(key, (String) value);
            }
        }
    }

}
