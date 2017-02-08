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

import java.util.List;

public class ReplacerTransform extends ConfigTransform {

    public ReplacerTransform(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        for (String key : section.getKeys(true)) {
            if (section.isString(key)) {
                // Replace string
                String string = replace(section.getString(key));

                // Replace string at config path
                setValue(section, key, string);
            } else if (section.isList(key)) {
                List<Object> list = (List<Object>) section.getList(key);
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    if (element instanceof String) {
                        // Replace string
                        String string = replace((String) element);

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

    private String replace(String string) {
        // Create replaceable for string
        Replaceable replaceable = new Replaceable(string);

        // Add replacers, order here is important
        // As the regex for each replacer increases in complexity, it becomes less specific
        // Therefore each consecutive replacer assumes the previous replacers are all unable to find a match
        replaceable.addReplacer(new PairRange(replaceable));
        replaceable.addReplacer(new TripleRange(replaceable));
        replaceable.addReplacer(new EqualChanceList(replaceable));
        replaceable.addReplacer(new WeightedChanceList(replaceable));
        replaceable.addReplacer(new Expression(replaceable));

        // Perform replacement
        return replaceable.replace();
    }

}
