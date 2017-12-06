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

public class VariableReplaceable extends BasicReplaceable {

    public VariableReplaceable(VariableContainer variables, String string) {
        super(string);

        // Add variable replacer to replacers
        addReplacer(new VariableReplacer(variables, this));
    }

    public VariableReplaceable(VariableContainer variables) {
        this(variables, "");
    }

}
