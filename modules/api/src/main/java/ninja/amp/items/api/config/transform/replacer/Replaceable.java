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

import java.util.ArrayList;
import java.util.List;

public class Replaceable {

    private List<Replacer> replacers = new ArrayList<>();
    private String string;

    public Replaceable(String string) {
        this.string = string;
    }

    public void addReplacer(Replacer replacer) {
        replacers.add(replacer);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String replace() {
        while (replacers.stream().anyMatch(Replacer::find)) {
            replacers.stream().filter(Replacer::find).forEachOrdered(Replacer::replace);
        }
        return string;
    }

}
