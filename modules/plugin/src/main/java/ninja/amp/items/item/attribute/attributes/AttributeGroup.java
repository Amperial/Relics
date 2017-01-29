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
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.ItemLore;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class AttributeGroup extends BasicAttribute {

    private final List<ItemAttribute> attributes;

    public AttributeGroup(List<ItemAttribute> attributes) {
        super(DefaultAttributeType.GROUP);

        this.attributes = attributes;

        setLore(new ItemLore() {
            @Override
            public void addTo(List<String> lore) {
                List<ItemAttribute> attributes = getAttributes();
                attributes.sort(ItemAttribute.LORE_ORDER);
                attributes.forEach((ItemAttribute a) -> a.getLore().addTo(lore));
            }
        });
    }

    public List<ItemAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", DefaultAttributeType.GROUP.getName());
        NBTTagList list = NBTTagList.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            list.add(attributeCompound);
        }
        compound.set("attributes", list);
    }

    public static class AttributeGroupFactory extends BasicAttributeFactory<AttributeGroup> {

        public AttributeGroupFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public AttributeGroup loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            List<ItemAttribute> attributes = new ArrayList<>();
            if (config.isConfigurationSection("attributes")) {
                ConfigurationSection attributesSection = config.getConfigurationSection("attributes");
                attributesSection.getKeys(false).stream().filter(attributesSection::isConfigurationSection).forEach(attribute -> {
                    ConfigurationSection attributeSection = attributesSection.getConfigurationSection(attribute);
                    String type = attributeSection.getString("type");
                    if (itemManager.hasAttributeType(type)) {
                        attributes.add(itemManager.getAttributeType(type).getFactory().loadFromConfig(attributeSection));
                    }
                });
            }

            return new AttributeGroup(attributes);
        }

        @Override
        public AttributeGroup loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            List<ItemAttribute> attributes = new ArrayList<>();
            if (compound.hasKey("attributes")) {
                NBTTagList list = compound.getList("attributes", 10);
                for (int i = 0; i < list.size(); i++) {
                    NBTTagCompound attribute = list.getCompound(i);
                    String type = attribute.getString("type");
                    if (itemManager.hasAttributeType(type)) {
                        attributes.add(itemManager.getAttributeType(type).getFactory().loadFromNBT(attribute));
                    }
                }
            }

            return new AttributeGroup(attributes);
        }

    }

}
