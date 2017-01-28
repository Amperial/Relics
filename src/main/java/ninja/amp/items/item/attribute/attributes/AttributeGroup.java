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
import ninja.amp.items.config.ConfigUtil;
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.ItemLore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static class AttributeGroupFactory extends BasicAttributeFactory {

        public AttributeGroupFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public ItemAttribute loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            List<ItemAttribute> attributes = new ArrayList<>();
            if (config.isList("attributes")) {
                for (ConfigurationSection attribute : ConfigUtil.getConfigurationSectionList(config, "attributes")) {
                    String type = attribute.getString("type");
                    if (itemManager.hasAttributeType(type)) {
                        attributes.add(itemManager.getAttributeType(type).getFactory().loadFromConfig(attribute));
                    }
                }
            }

            return new AttributeGroup(attributes);
        }

        @Override
        public ItemAttribute loadFromNBT() {
            return null;
        }

        @Override
        public void saveToNBT() {

        }

    }

}
