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
 * A replacer that searches for number ranges of the form {@code (min,max)},<br>
 * replacing them with a random value between the min and max.
 *
 * @author Austin Payne
 */
public class PairRange extends Replacer {

    private static final String VALUE = "-?\\d+(\\.\\d+)?";
    private static final Pattern PAIR = Pattern.compile("\\(" + VALUE + "," + VALUE + "\\)");

    public PairRange(Replaceable value) {
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
