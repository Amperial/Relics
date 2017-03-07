/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.storage.config.transform.replacer;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles finding the most simplified value possible for a given string and replacers.
 *
 * @author Austin Payne
 */
public class Replaceable {

    private List<Replacer> replacers = new ArrayList<>();
    private String string;

    public Replaceable(String string) {
        this.string = string;
    }

    /**
     * Adds a replacer to the replaceable.
     *
     * @param replacer the replacer to add
     */
    public void addReplacer(Replacer replacer) {
        replacers.add(replacer);
    }

    /**
     * Gets the string of the replaceable.
     *
     * @return the replaceable string
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string of the replaceable.
     *
     * @param string the string
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Replaces the string with the result of each replacer in order until none can reduce the string anymore.
     *
     * @return the final string value
     */
    public String replace() {
        while (replacers.stream().anyMatch(Replacer::find)) {
            replacers.stream().filter(Replacer::find).forEachOrdered(Replacer::replace);
        }
        return string;
    }

}
