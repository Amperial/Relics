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

import java.util.Random;
import java.util.regex.Pattern;

public abstract class ConfigTransform {

    protected static final Pattern NUMBER = Pattern.compile("-?\\d+(\\.\\d+)?");
    protected static final Random RANDOM = new Random();

    protected final ItemPlugin plugin;

    public ConfigTransform(ItemPlugin plugin) {
        this.plugin = plugin;
    }

    protected static void setValue(ConfigurationSection section, String key, Object value) {
        if (value instanceof String) {
            String string = (String) value;
            if (NUMBER.matcher(string).matches()) {
                double number = Double.valueOf(string);
                if (string.contains(".")) {
                    section.set(key, number);
                } else {
                    section.set(key, (int) number);
                }
                return;
            } else if (string.isEmpty()) {
                section.set(key, null);
                return;
            }
        }
        section.set(key, value);
    }

    public abstract ConfigurationSection transform(ConfigurationSection section, Object[] args);

}
