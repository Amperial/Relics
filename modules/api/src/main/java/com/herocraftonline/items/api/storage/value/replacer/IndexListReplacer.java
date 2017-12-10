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
 * A replacer that searches for element lists of the format {@code [element,element,element...](index)},<br>
 * replacing them with a single element from the list, at the position of the given index.
 *
 * @author Austin Payne
 */
public class IndexListReplacer extends Replacer {

    private static final String ELEMENT = "[^\\[(<:,\\s>\\])]*";
    private static final Pattern INDEX_LIST = Pattern.compile("\\[" + ELEMENT + "(," + ELEMENT + ")*]\\(\\d+\\)");

    public IndexListReplacer(Replaceable value) {
        super(INDEX_LIST.matcher(value.getString()), value);
    }

    @Override
    public String getValue(String replace) {
        int listEnd = replace.lastIndexOf(']');
        String list = replace.substring(1, listEnd);

        // Get possible elements
        String[] elements = list.split(",");

        // Get element index
        int index = Integer.valueOf(replace.substring(listEnd + 2, replace.length() - 1));

        // Return element at index
        return elements.length > index ? elements[index]: "";
    }

}
