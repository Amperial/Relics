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
package com.herocraftonline.items.api.storage.value.variables;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.Value;

import java.util.Optional;

public interface VariableContainer {

    default boolean hasVariable(String name) {
        return getValue(name).isPresent();
    }

    default boolean hasVariable(String name, Class<?> type) {
        return getValue(name, type).isPresent();
    }

    Optional<Object> getValue(String name);

    @SuppressWarnings("unchecked")
    default <T> Optional<T> getValue(String name, Class<T> type) {
        return getValue(name).filter(value -> type.isAssignableFrom(value.getClass())).map(value -> (T) value);
    }

    @SuppressWarnings("unchecked")
    default <T> T getValue(String name, T def) {
        return getValue(name, (Class<T>) def.getClass()).orElse(def);
    }

    void setValue(String name, Object value);

    void addDynamicValue(Value value);

    void resetDynamicValues();

    void saveToNBT(NBTTagCompound compound);

}
