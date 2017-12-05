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

import com.herocraftonline.items.api.storage.value.variables.VariableContainer;
import com.herocraftonline.items.api.storage.value.replacer.Replaceable;
import com.herocraftonline.items.api.storage.value.replacer.VariableReplaceable;

public abstract class DynamicValue<T> implements Value<T> {

    private final Replaceable replaceable;
    private final String value;
    private final boolean cache;
    private T last;

    public DynamicValue(VariableContainer variables, String value, boolean cache) {
        this.replaceable = new VariableReplaceable(variables);
        this.value = value;
        this.cache = cache;
        this.last = null;
    }

    @Override
    public T getValue() {
        if (!cache || last == null) {
            last = getValue(replaceable.replace(value));
        }
        return last;
    }

    protected abstract T getValue(String string);

    public void reset() {
        this.last = null;
    }

}
