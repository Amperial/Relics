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

public class EqualChanceList extends Replacer {

    private static final Pattern EQUAL_CHANCE = Pattern.compile("<[^<:,\\s]*(,[^<:,\\s>]*)*>");

    public EqualChanceList(Replaceable value) {
        super(EQUAL_CHANCE.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        String list = replace.substring(1, replace.length() - 1);

        // Get possible elements
        String[] elements = list.split(",");

        // Return random element
        return elements.length > 0 ? elements[RANDOM.nextInt(elements.length)] : "";
    }

}
