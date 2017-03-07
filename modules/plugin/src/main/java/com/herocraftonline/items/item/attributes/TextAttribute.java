/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.Text;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TextAttribute extends BaseAttribute<Text> implements Text {

    private final List<String> text;

    public TextAttribute(String name, List<String> text) {
        super(name, DefaultAttribute.TEXT);

        this.text = text;

        setLore((lore, prefix) -> lore.addAll(getText().stream().map(line -> prefix + line).collect(Collectors.toList())));
    }

    @Override
    public List<String> getText() {
        return text;
    }

    @Override
    public void addText(String... text) {
        Collections.addAll(this.text, text);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagList list = NBTTagList.create();
        for (String line : getText()) {
            list.addBase(NBTTagString.create(line));
        }
        compound.setBase("text", list);
    }

    public static class Factory extends BaseAttributeFactory<Text> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Text loadFromConfig(String name, ConfigurationSection config) {
            // Load text
            List<String> text = config.getStringList("text");
            text.replaceAll(line -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', line));

            // Create text attribute
            return new TextAttribute(name, text);
        }

        @Override
        public Text loadFromNBT(String name, NBTTagCompound compound) {
            // Load text
            List<String> text = new ArrayList<>();
            if (compound.hasKey("text")) {
                NBTTagList list = compound.getList("text", 8);
                for (int i = 0; i < list.size(); i++) {
                    text.add(list.getString(i));
                }
            }

            // Create text attribute
            return new TextAttribute(name, text);
        }
    }

}
