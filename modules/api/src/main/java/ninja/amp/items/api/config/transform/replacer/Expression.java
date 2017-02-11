/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.config.transform.replacer;

import java.util.regex.Pattern;

/**
 * A replacer that searches for simple math expressions containing numbers separated by the operators {@code +-*\^},<br>
 * replacing them with the value of the evaluated expression.
 *
 * @author Austin Payne
 */
public class Expression extends Replacer {

    private static final String NUMBER = "-?\\d+(\\.\\d+)?";
    private static final String VALUE = NUMBER + "([\\^*/+-]" + NUMBER + ")";
    private static final Pattern EXPRESSION = Pattern.compile(VALUE + "+|\\(" + VALUE + "*\\)");

    public Expression(Replaceable value) {
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
