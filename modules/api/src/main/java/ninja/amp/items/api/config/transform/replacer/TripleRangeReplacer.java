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

public class TripleRangeReplacer extends Replacer {

    private static final Pattern TRIPLE = Pattern.compile("\\(([+-]?\\d+(\\.\\d+)?,){2}[+-]?\\d+(\\.\\d+)?\\)");

    public TripleRangeReplacer(Replaceable value) {
        super(TRIPLE.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        int firstComma = replace.indexOf(',');
        int lastComma = replace.lastIndexOf(',');

        double min = Double.parseDouble(replace.substring(1, firstComma));
        double max = Double.parseDouble(replace.substring(firstComma + 1, lastComma));
        double fraction = Double.parseDouble(replace.substring(lastComma + 1, replace.length() - 1));

        double value = min + fraction * (max - min);
        return replace.contains(".") ? Double.toString(value) : Integer.toString((int) value);
    }

}
