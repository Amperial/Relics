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
package ninja.amp.items.api.config.transform;

import ninja.amp.items.api.ItemPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeTransformer extends ConfigTransform {

    private static final Pattern PAIR = Pattern.compile("\\([+-]?\\d+(\\.\\d+)?,[+-]?\\d+(\\.\\d+)?\\)");
    private static final Pattern TRIPLE = Pattern.compile("\\(([+-]?\\d+(\\.\\d+)?,){2}[+-]?\\d+(\\.\\d+)?\\)");

    public RangeTransformer(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        // Check all paths in config
        for (String key : section.getKeys(true)) {
            // Ranges will appear as strings
            if (section.isString(key)) {
                String string = section.getString(key);

                // Look for pairs and triples of numbers inside parentheses
                Replacer random = new PairReplacer(string);
                Replacer exact = new TripleReplacer(string);
                while (random.find(string) || exact.find(string)) {
                    while (random.find(string)) {
                        string = random.replace();
                    }
                    while (exact.find(string)) {
                        string = exact.replace();
                    }
                }

                // Replace value at config path
                setValue(section, key, string);
            }
        }
        return section;
    }

    private abstract class Replacer {

        private Matcher matcher;
        private String string;

        public Replacer(Matcher matcher, String string) {
            this.matcher = matcher;
            this.string = string;
        }

        public boolean find(String string) {
            this.string = string;
            matcher.reset(string);
            return matcher.find();
        }

        public String replace() {
            String range = matcher.group();
            double value = value(range);
            string = matcher.replaceFirst(range.contains(".") ? Double.toString(value) : Integer.toString((int) value));
            return string;
        }

        public abstract double value(String range);

    }

    private class PairReplacer extends Replacer {

        public PairReplacer(String string) {
            super(PAIR.matcher(string), string);
        }

        @Override
        public double value(String range) {
            int comma = range.indexOf(',');

            double min = Double.parseDouble(range.substring(1, comma));
            double max = Double.parseDouble(range.substring(comma + 1, range.length() - 1));

            return min + RANDOM.nextDouble() * (max - min);
        }

    }

    private class TripleReplacer extends Replacer {

        public TripleReplacer(String string) {
            super(TRIPLE.matcher(string), string);
        }

        @Override
        public double value(String range) {
            int firstComma = range.indexOf(',');
            int lastComma = range.lastIndexOf(',');

            double min = Double.parseDouble(range.substring(1, firstComma));
            double max = Double.parseDouble(range.substring(firstComma + 1, lastComma));
            double fraction = Double.parseDouble(range.substring(lastComma + 1, range.length() - 1));

            return min + fraction * (max - min);
        }

    }

}
