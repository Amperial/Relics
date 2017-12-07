/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.Text;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextAttribute extends BaseAttribute<Text> implements Text {

    private static final Function<String, String> color = (s) -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', s);
    private final Value<List<String>> text;

    public TextAttribute(Item item, String name, Value<List<String>> text) {
        super(item, name, DefaultAttributes.TEXT);

        this.text = text;

        setLore((lore, prefix) -> lore.addAll(getText().stream().map(line -> prefix + color.apply(line)).collect(Collectors.toList())));
    }

    @Override
    public List<String> getText() {
        return text.getValue();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        text.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<Text> {
        private static final StoredValue<List<String>> TEXT = new StoredValue<>("text", StoredValue.STRING_LIST, new ArrayList<>(), true, true);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Text loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load text
            Value<List<String>> text = TEXT.loadFromConfig(item, config);

            // Create text attribute
            return new TextAttribute(item, name, text);
        }

        @Override
        public Text loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load text
            Value<List<String>> text = TEXT.loadFromNBT(item, compound);

            // Create text attribute
            return new TextAttribute(item, name, text);
        }
    }

}
