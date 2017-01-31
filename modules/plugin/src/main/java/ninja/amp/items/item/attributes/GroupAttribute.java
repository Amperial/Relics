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
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupAttribute extends BasicAttribute implements AttributeGroup {

    private final Map<String, ItemAttribute> attributes;

    public GroupAttribute(String name, Map<String, ItemAttribute> attributes) {
        super(name, DefaultAttributeType.GROUP);

        this.attributes = attributes;

        setLore(lore -> getAttributes().stream()
                .sorted(Comparator.comparingInt(a -> a.getType().getLorePosition()))
                .forEachOrdered(a -> a.getLore().addTo(lore)));
    }

    @Override
    public boolean hasAttribute(String name, boolean deep) {
        if (getAttributesByName().containsKey(name)) {
            return true;
        } else if (deep) {
            for (ItemAttribute attribute : getAttributes()) {
                if (attribute instanceof AttributeGroup) {
                    AttributeGroup attributeGroup = (AttributeGroup) attribute;
                    if (attributeGroup.hasAttribute(name, true)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasAttribute(AttributeType type, boolean deep) {
        for (ItemAttribute attribute : getAttributes()) {
            if (attribute.getType().equals(type)) {
                return true;
            } else if (deep && attribute instanceof AttributeGroup) {
                AttributeGroup attributeGroup = (AttributeGroup) attribute;
                if (attributeGroup.hasAttribute(type, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ItemAttribute getAttribute(String name, boolean deep) {
        if (attributes.containsKey(name)) {
            return attributes.get(name);
        } else if (deep) {
            for (ItemAttribute attribute : getAttributes()) {
                if (attribute instanceof AttributeGroup) {
                    AttributeGroup attributeGroup = (AttributeGroup) attribute;
                    if (attributeGroup.hasAttribute(name, true)) {
                        return attributeGroup.getAttribute(name, true);
                    }
                }
            }
        }
        return attributes.get(name);
    }

    @Override
    public Collection<ItemAttribute> getAttributes(AttributeType type, boolean deep) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (ItemAttribute attribute : getAttributes()) {
            if (attribute.getType().equals(type)) {
                attributes.add(attribute);
            }
            if (deep && attribute instanceof AttributeGroup) {
                attributes.addAll(((AttributeGroup) attribute).getAttributes(type, true));
            }
        }
        return attributes;
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        return attributes.values();
    }

    @Override
    public Map<String, ItemAttribute> getAttributesByName() {
        return attributes;
    }

    @Override
    public void addAttribute(ItemAttribute... attributes) {
        for (ItemAttribute attribute : attributes) {
            this.attributes.put(attribute.getName(), attribute);
        }
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
        NBTTagList attributes = NBTTagList.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            attributes.add(attributeCompound);
        }
        compound.set("attributes", attributes);
    }

    public static class AttributeGroupFactory extends BasicAttributeFactory<GroupAttribute> {

        public AttributeGroupFactory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public GroupAttribute loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name and attributes
            String name = config.getName();
            if (name.isEmpty()) {
                name = "group";
            }
            Map<String, ItemAttribute> attributes = new TreeMap<>();
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
            return new GroupAttribute(name, attributes);
        }

        @Override
        public GroupAttribute loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name and attributes
            String name = compound.getString("name");
            Map<String, ItemAttribute> attributes = new TreeMap<>();
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
            return new GroupAttribute(name, attributes);
        }

    }

}
