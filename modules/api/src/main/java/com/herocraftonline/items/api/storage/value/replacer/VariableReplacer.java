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
package com.herocraftonline.items.api.storage.value.replacer;

import com.herocraftonline.items.api.storage.value.variables.VariableContainer;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * A replacer that searches for variable references of the form {@code &variable&},<br>
 * replacing them with the variable's actual value from the variable container.
 *
 * @author Austin Payne
 */
public class VariableReplacer extends Replacer {

    private static final Pattern VARIABLE = Pattern.compile("&[A-Za-z]+&");

    private final VariableContainer variables;

    public VariableReplacer(VariableContainer variables, Replaceable value) {
        super(VARIABLE.matcher(value.getString()), value);

        this.variables = variables;
    }

    @Override
    public String getValue(String replace) {
        String name = replace.substring(1, replace.length() - 1);

        return variables.getValue(name).map(Object::toString).orElse(name);
    }

}
