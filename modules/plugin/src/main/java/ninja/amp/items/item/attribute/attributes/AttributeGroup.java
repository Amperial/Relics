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
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AttributeGroup extends BasicAttribute {

    private final Map<String, ItemAttribute> attributes;

    public AttributeGroup(String name, Map<String, ItemAttribute> attributes) {
        super(name, DefaultAttributeType.GROUP);

        this.attributes = attributes;

        setLore(lore -> getAttributes().stream()
                .sorted(Comparator.comparingInt(a -> a.getType().getLorePosition()))
                .forEachOrdered(a -> a.getLore().addTo(lore)));
    }

    public ItemAttribute getAttribute(String name) {
        return attributes.get(name);
    }

    public Collection<ItemAttribute> getAttributes() {
        return attributes.values();
    }

    @Override
    public boolean canEquip(Player player) {
        return getAttributes().stream().allMatch(attribute -> attribute.canEquip(player));
    }

    @Override
    public void equip(Player player) {
        getAttributes().forEach(attribute -> attribute.equip(player));
    }

    @Override
    public void unEquip(Player player) {
        getAttributes().forEach(attribute -> attribute.unEquip(player));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagCompound attributes = NBTTagCompound.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            attributes.set(attribute.getName(), attributeCompound);
        }
        compound.set("attributes", attributes);
    }

    public static class AttributeGroupFactory extends BasicAttributeFactory<AttributeGroup> {

        public AttributeGroupFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public AttributeGroup loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name and attributes
            String name = config.getName();
            Map<String, ItemAttribute> attributes = new HashMap<>();
            if (config.isConfigurationSection("attributes")) {
                ConfigurationSection attributesSection = config.getConfigurationSection("attributes");
                attributesSection.getKeys(false).stream().filter(attributesSection::isConfigurationSection).forEach(attributeName -> {
                    ConfigurationSection attributeSection = attributesSection.getConfigurationSection(attributeName);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeSection);
                    if (attribute != null) {
                        attributes.put(attributeName, attribute);
                    }
                });
            }

            // Create attribute group
            return new AttributeGroup(name, attributes);
        }

        @Override
        public AttributeGroup loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name and attributes
            String name = compound.getString("name");
            Map<String, ItemAttribute> attributes = new HashMap<>();
            if (compound.hasKey("attributes")) {
                NBTTagList list = compound.getList("attributes", 10);
                for (int i = 0; i < list.size(); i++) {
                    NBTTagCompound attributeCompound = list.getCompound(i);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeCompound);
                    if (attribute != null) {
                        attributes.put(attribute.getName(), attribute);
                    }
                }
            }

            // Create attribute group
            return new AttributeGroup(name, attributes);
        }

    }

}
