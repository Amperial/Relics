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
package com.herocraftonline.items.api.config.transform;

import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Used when loading configs to transform a config into a concrete state usable by the item factory.
 *
 * @author Austin Payne
 */
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

    protected static void setValue(List<Object> list, int index, Object value) {
        if (value instanceof String) {
            String string = (String) value;
            if (NUMBER.matcher(string).matches()) {
                double number = Double.valueOf(string);
                if (string.contains(".")) {
                    list.set(index, number);
                } else {
                    list.set(index, (int) number);
                }
                return;
            } else if (string.isEmpty()) {
                list.remove(index);
                return;
            }
        }
        list.set(index, value);
    }

    /**
     * Transforms the the given configuration section with certain args.
     *
     * @param section the configuration section
     * @param args    the args
     * @return the transformed configuration section
     */
    public abstract ConfigurationSection transform(ConfigurationSection section, Object[] args);

}
