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
package ninja.amp.items.item.attribute.attributes;

import ninja.amp.items.AmpItems;
import ninja.amp.items.item.attribute.ItemLore;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import ninja.amp.items.nms.nbt.NBTTagString;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class InformationAttribute extends BasicAttribute {

    private final List<String> information;

    public InformationAttribute(List<String> information) {
        super(DefaultAttributeType.INFO);

        this.information = information;

        setLore(new ItemLore() {
            @Override
            public void addTo(List<String> lore) {
                lore.addAll(getInformation());
            }
        });
    }

    public List<String> getInformation() {
        return information;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", DefaultAttributeType.INFO.getName());
        NBTTagList list = NBTTagList.create();
        for (String line : getInformation()) {
            list.add(NBTTagString.create(line));
        }
        compound.set("text", list);
    }

    public static class InformationAttributeFactory extends BasicAttributeFactory<InformationAttribute> {

        public InformationAttributeFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public InformationAttribute loadFromConfig(ConfigurationSection config) {
            // Load attribute
            return new InformationAttribute(config.getStringList("text"));
        }

        @Override
        public InformationAttribute loadFromNBT(NBTTagCompound compound) {
            // Load text
            List<String> text = new ArrayList<>();
            if (compound.hasKey("text")) {
                NBTTagList list = compound.getList("text", 8);
                for (int i = 0; i < list.size(); i++) {
                    text.add(list.getString(i));
                }
            }

            // Load attribute
            return new InformationAttribute(text);
        }

    }

}
