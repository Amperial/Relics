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

public class WeightedChanceListReplacer extends Replacer {

    private static final Pattern WEIGHTED_CHANCE = Pattern.compile("<[^<:,\\s]*:([1-9]\\d*|\\d\\.\\d*[1-9])+(,[^<:,\\s]*:([1-9]\\d*|\\d\\.\\d*[1-9])+)*>");

    public WeightedChanceListReplacer(Replaceable value) {
        super(WEIGHTED_CHANCE.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        String list = replace.substring(1, replace.length() - 1);

        // Get possible elements
        String[] elements = list.split(",");
        Double[] values = new Double[elements.length];
        double total = 0;
        for (int i = 0; i < elements.length; i++) {
            String[] element = elements[i].split(":");
            elements[i] = element[0];
            values[i] = Double.valueOf(element[1]);
            total += values[i];
        }

        // Return random element
        double random = RANDOM.nextDouble() * total;
        double weight = 0;
        for (int i = 0; i < elements.length; i++) {
            weight += values[i];
            if (weight >= random) {
                return elements[i];
            }
        }
        return "";
    }

}
