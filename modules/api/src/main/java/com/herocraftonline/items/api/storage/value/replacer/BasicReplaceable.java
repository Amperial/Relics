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

/**
 * A basic class to simplify instantiating a
 */
public class BasicReplaceable extends Replaceable {

    public BasicReplaceable(String string) {
        super(string);

        // Add replacers, order here is important
        // As the regex for each replacer increases in complexity, it becomes less specific
        // Therefore each consecutive replacer assumes the previous replacers are all unable to find a match
        addReplacer(new RangeReplacer(this));
        addReplacer(new InterpolateReplacer(this));
        addReplacer(new IndexListReplacer(this));
        addReplacer(new RandomListReplacer(this));
        addReplacer(new WeightedListReplacer(this));
        addReplacer(new ExpressionReplacer(this));
    }

    public BasicReplaceable() {
        this("");
    }

}
