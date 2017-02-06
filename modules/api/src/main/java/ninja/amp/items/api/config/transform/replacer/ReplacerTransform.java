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

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.config.transform.ConfigTransform;
import org.bukkit.configuration.ConfigurationSection;

public class ReplacerTransform extends ConfigTransform {

    public ReplacerTransform(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        section.getKeys(true).stream().filter(section::isString).forEach(key -> {
            // Create replaceable for string
            Replaceable replaceable = new Replaceable(section.getString(key));

            // Add replacers, order here is important
            // As the regex for each replacer increases in complexity, it becomes less specific
            // Therefore each consecutive replacer assumes the previous replacers are all unable to find a match
            replaceable.addReplacer(new PairRange(replaceable));
            replaceable.addReplacer(new TripleRange(replaceable));
            replaceable.addReplacer(new EqualChanceList(replaceable));
            replaceable.addReplacer(new WeightedChanceList(replaceable));
            replaceable.addReplacer(new Expression(replaceable));

            // Replace value at config path
            setValue(section, key, replaceable.replace());
        });
        return section;
    }

}
