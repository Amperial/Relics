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

public class ListTransformer extends ConfigTransform {

    private static final Pattern EQUAL_CHANCE = Pattern.compile("<[^<:,\\s]*(,[^<:,\\s>]*)*>");
    private static final Pattern PROPORTIONAL_CHANCE = Pattern.compile("<[^<:,\\s]*:([1-9]\\d*|\\d\\.\\d*[1-9])+(,[^<:,\\s]*:([1-9]\\d*|\\d\\.\\d*[1-9])+)*>");

    public ListTransformer(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        // Check all paths in config
        for (String key : section.getKeys(true)) {
            // Random element lists will appear as strings
            if (section.isString(key)) {
                String string = section.getString(key);

                // Look for equal and proportional chance lists inside angle brackets
                Replacer equal = new EqualChanceReplacer(string);
                Replacer proportional = new ProportionalChanceReplacer(string);
                while (equal.find(string) || proportional.find(string)) {
                    while (equal.find(string)) {
                        string = equal.replace();
                    }
                    while (proportional.find(string)) {
                        string = proportional.replace();
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
            String list = matcher.group();
            String value = value(list.substring(1, list.length() - 1));
            return string = matcher.replaceFirst(value);
        }

        public abstract String value(String list);

    }

    private class EqualChanceReplacer extends Replacer {

        public EqualChanceReplacer(String string) {
            super(EQUAL_CHANCE.matcher(string), string);
        }

        @Override
        public String value(String list) {
            // Get possible elements
            String[] elements = list.split(",");

            // Return random element
            return elements.length > 0 ? elements[RANDOM.nextInt(elements.length)] : "";
        }

    }

    private class ProportionalChanceReplacer extends Replacer {

        public ProportionalChanceReplacer(String string) {
            super(PROPORTIONAL_CHANCE.matcher(string), string);
        }

        @Override
        public String value(String list) {
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

}
