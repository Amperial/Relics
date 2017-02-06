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

    public boolean find() {
        if (!value.getString().equals(lastValue)) {
            lastValue = value.getString();
            matcher.reset(lastValue);
            lastFound = matcher.find();
        }
        return lastFound;
    }

    public void replace() {
        value.setString(matcher.replaceFirst(getValue(matcher.group())));
    }

    public abstract String getValue(String replace);

}
