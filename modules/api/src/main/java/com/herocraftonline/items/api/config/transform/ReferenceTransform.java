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
import com.herocraftonline.items.api.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.regex.Pattern;

/**
 * A config transform that replaces references to other configs with the transformed contents of those configs.
 *
 * @author Austin Payne
 */
public class ReferenceTransform extends ConfigTransform {

    private static final Pattern CONFIG = Pattern.compile("#\\{[\\w/-]*[\\w-]+}");

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
                String path = string.substring(2, string.length() - 1);

                // Load and transform nested config
                ConfigurationSection value = configManager.loadAndTransform("configs/" + path + ".yml", plugin, args);

                // Replace value at config path
                setValue(section, key, value);
            }
        });
        return section;
    }

}
