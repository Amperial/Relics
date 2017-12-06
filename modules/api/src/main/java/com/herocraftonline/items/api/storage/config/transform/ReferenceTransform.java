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
import com.herocraftonline.items.api.storage.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.regex.Pattern;

/**
 * A config transform that replaces references to other configs with the transformed contents of those configs.
 *
 * @author Austin Payne
 */
public class ReferenceTransform extends ConfigTransform {

    private static final Pattern CONFIG = Pattern.compile("#\\{[\\w/-]*[\\w-]+(\\[([\\S\\t ])*])?}");

    public ReferenceTransform(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        ConfigManager configManager = plugin.getConfigManager();
        section.getKeys(true).stream().filter(section::isString).forEach(key -> {
            String string = section.getString(key);
            // Check if string matches regex
            if (CONFIG.matcher(string).matches()) {
                String path;
                Object[] argz;

                // Get path and arguments to pass to referenced config
                if (string.contains("[")) {
                    int bracket = string.indexOf("[");

                    // Get path to referenced config
                    path = string.substring(2, bracket);

                    // Parse new args
                    String[] split = string.substring(bracket + 1, string.length() - 2).split(",");
                    argz = new Object[split.length];
                    System.arraycopy(split, 0, argz, 0, split.length);
                } else {
                    // Get path to referenced config
                    path = string.substring(2, string.length() - 1);

                    // Use current args
                    argz = args;
                }

                // Load and transform nested config
                ConfigurationSection value = configManager.loadAndTransform("configs/" + path + ".yml", plugin, argz);

                // Replace value at config path
                setValue(section, key, value);
            }
        });
        return section;
    }

}
