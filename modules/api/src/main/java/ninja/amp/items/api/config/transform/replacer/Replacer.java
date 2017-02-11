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

import java.util.Random;
import java.util.regex.Matcher;

/**
 * Handles replacing a section of a string that matches a regex matcher with a calculated value.
 *
 * @author Austin Payne
 */
public abstract class Replacer {

    protected static final Random RANDOM = new Random();

    private Matcher matcher;
    private Replaceable value;
    private String lastValue;
    private boolean lastFound;

    public Replacer(Matcher matcher, Replaceable value) {
        this.matcher = matcher;
        this.value = value;
    }

    /**
     * Checks if the replacer's matcher matches any part of the current value.
     *
     * @return {@code true} if the replacer can find a match, else {@code false}
     */
    public boolean find() {
        if (!value.getString().equals(lastValue)) {
            lastValue = value.getString();
            matcher.reset(lastValue);
            lastFound = matcher.find();
        }
        return lastFound;
    }

    /**
     * Replaces a part of the current value with the calculated value.
     */
    public void replace() {
        value.setString(matcher.replaceFirst(getValue(matcher.group())));
    }

    /**
     * Calculates the value of a part of the current value.
     *
     * @param replace the string to replace
     * @return the new calculated value
     */
    public abstract String getValue(String replace);

}
