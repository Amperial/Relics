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
 * A replacer that searches for element lists of the format {@code <element,element,element...>},<br>
 * replacing them with a single random element from the list.
 *
 * @author Austin Payne
 */
public class ListReplacer extends Replacer {

    private static final Pattern EQUAL_CHANCE = Pattern.compile("<[^<:,\\s]*(,[^<:,\\s>]*)*>");

    public ListReplacer(Replaceable value) {
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
