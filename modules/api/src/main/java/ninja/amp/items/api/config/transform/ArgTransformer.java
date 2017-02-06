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

public class ArgTransformer extends ConfigTransform {

    public ArgTransformer(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        // Check all paths in config
        for (String key : section.getKeys(true)) {
            // Args will appear as strings
            if (section.isString(key)) {
                String string = section.getString(key);

                // Format string with transform args
                string = String.format(string, args);

                // Replace value at config path
                setValue(section, key, string);
            }
        }
        return section;
    }

}
