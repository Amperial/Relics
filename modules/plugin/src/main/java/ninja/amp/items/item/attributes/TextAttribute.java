/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.Text;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import ninja.amp.items.nms.nbt.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextAttribute extends BasicAttribute implements Text {

    private final List<String> text;

    public TextAttribute(String name, List<String> text) {
        super(name, DefaultAttributeType.TEXT);

        this.text = text;

        setLore(lore -> lore.addAll(getText()));
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
            list.add(NBTTagString.create(line));
        }
        compound.set("text", list);
    }

    public static class TextAttributeFactory extends BasicAttributeFactory<TextAttribute> {

        public TextAttributeFactory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public TextAttribute loadFromConfig(ConfigurationSection config) {
            // Load name and text
            String name = config.getName();
            List<String> text = config.getStringList("text");
            text.replaceAll(line -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', line));

            // Create text attribute
            return new TextAttribute(name, text);
        }

        @Override
        public TextAttribute loadFromNBT(NBTTagCompound compound) {
            // Load name and text
            String name = compound.getString("name");
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
