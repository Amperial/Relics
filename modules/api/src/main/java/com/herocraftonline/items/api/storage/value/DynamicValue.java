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

import java.util.function.Function;

public class DynamicValue<T> implements Value<T> {

    private final Replaceable replaceable;
    private final String value;
    private final T def;
    private final Function<String, T> parse;
    private final boolean cache;
    private T last;

    public DynamicValue(VariableContainer variables, String value, Function<String, T> parse, T def, boolean cache) {
        this.replaceable = new VariableReplaceable(variables);
        this.value = value;
        this.parse = parse;
        this.def = def;
        this.cache = cache;
        this.last = null;
    }

    @Override
    public T getValue() {
        if (last == null || !cache) {
            try {
                last = parse.apply(replaceable.replace(value));
            } catch (Exception e) {
                last = def;
            }
        }
        return last;
    }

    public void reset() {
        this.last = null;
    }

}
