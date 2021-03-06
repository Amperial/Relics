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
 * A replacer that searches for number ranges of the form {@code (min,max)},<br>
 * replacing them with a random value between the min and max.
 *
 * @author Austin Payne
 */
public class RangeReplacer extends Replacer {

    private static final String VALUE = "-?\\d+(\\.\\d+)?";
    private static final Pattern PAIR = Pattern.compile("\\(" + VALUE + "," + VALUE + "\\)");

    public RangeReplacer(Replaceable value) {
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
