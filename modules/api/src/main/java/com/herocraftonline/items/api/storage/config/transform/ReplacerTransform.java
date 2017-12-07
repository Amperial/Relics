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
package com.herocraftonline.items.api.storage.config.transform;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.storage.value.replacer.BasicReplaceable;
import com.herocraftonline.items.api.storage.value.replacer.Replaceable;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * A config transform that attempts to replace strings containing various features with concrete values.
 *
 * @author Austin Payne
 */
public class ReplacerTransform extends ConfigTransform {

    private final Replaceable replaceable;

    public ReplacerTransform(ItemPlugin plugin) {
        super(plugin);

        replaceable = new BasicReplaceable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        for (String key : section.getKeys(true)) {
            if (section.isString(key)) {
                // Replace string
                String string = replaceable.replace(section.getString(key));

                // Replace string at config path
                setValue(section, key, string);
            } else if (section.isList(key)) {
                List<Object> list = (List<Object>) section.getList(key);
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    if (element instanceof String) {
                        // Replace string
                        String string = replaceable.replace((String) element);

                        // Replace value at list index
                        setValue(list, i, string);
                    }
                }

                // Replace list at config path
                setValue(section, key, list);
            }
        }
        return section;
    }

}
