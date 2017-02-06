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

public class PairRangeReplacer extends Replacer {

    private static final Pattern PAIR = Pattern.compile("\\([+-]?\\d+(\\.\\d+)?,[+-]?\\d+(\\.\\d+)?\\)");

    public PairRangeReplacer(Replaceable value) {
        super(PAIR.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        int comma = replace.indexOf(',');

        double min = Double.parseDouble(replace.substring(1, comma));
        double max = Double.parseDouble(replace.substring(comma + 1, replace.length() - 1));

        double value = min + RANDOM.nextDouble() * (max - min);
        return replace.contains(".") ? Double.toString(value) : Integer.toString((int) value);
    }

}
