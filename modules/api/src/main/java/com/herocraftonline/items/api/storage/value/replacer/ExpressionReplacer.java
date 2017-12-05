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

import java.util.regex.Pattern;

/**
 * A replacer that searches for simple math expressions containing numbers separated by the operators {@code +-*\^},<br>
 * replacing them with the value of the evaluated expression.
 *
 * @author Austin Payne
 */
public class ExpressionReplacer extends Replacer {

    private static final String NUMBER = "-?\\d+(\\.\\d+)?";
    private static final String VALUE = NUMBER + "([\\^*/+-]" + NUMBER + ")";
    private static final Pattern EXPRESSION = Pattern.compile(VALUE + "+|\\(" + VALUE + "*\\)");

    public ExpressionReplacer(Replaceable value) {
        super(EXPRESSION.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        // Remove possible parentheses
        if (replace.startsWith("(")) {
            replace = replace.substring(1, replace.length() - 1);
        }

        return new com.udojava.evalex.Expression(replace).eval().toPlainString();
    }

}
