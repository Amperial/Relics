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
    private final boolean spacing;

    public GroupAttribute(String name, Map<String, ItemAttribute> attributes, boolean spacing) {
        super(name, DefaultAttributeType.GROUP);

        this.attributes = attributes;
        this.spacing = spacing;

        if (spacing) {
            setLore((lore, prefix) -> {
                getAttributes().stream()
                        .sorted(Comparator.comparingInt(a -> a.getType().getLorePosition()))
                        .forEachOrdered(a -> {
                            int s = lore.size();
                            a.getLore().addTo(lore, prefix);
                            if (lore.size() > s) {
                                lore.add("");
                            }
                        });
                if (getAttributes().size() > 0) {
                    lore.remove(lore.size() - 1);
                }
            });
        } else {
            setLore((lore, prefix) -> getAttributes().stream()
                    .sorted(Comparator.comparingInt(a -> a.getType().getLorePosition()))
                    .forEachOrdered(a -> a.getLore().addTo(lore, prefix)));
        }
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
    public ItemAttribute getAttribute(AttributeType type, boolean deep) {
        for (ItemAttribute attribute : getAttributes()) {
            if (attribute.getType().equals(type)) {
                return attribute;
            }
            if (deep && attribute instanceof AttributeGroup) {
                AttributeGroup attributeGroup = (AttributeGroup) attribute;
                if (attributeGroup.hasAttribute(type, true)) {
                    return attributeGroup.getAttribute(type, true);
                }
            }
        }
        return null;
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
    public void onEquip(Player player) {
        getAttributes().forEach(attribute -> attribute.onEquip(player));
    }

    @Override
    public void onUnEquip(Player player) {
        getAttributes().forEach(attribute -> attribute.onUnEquip(player));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagList attributes = NBTTagList.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            attributes.addBase(attributeCompound);
        }
        compound.setBase("attributes", attributes);
        compound.setBoolean("spacing", spacing);
    }

    public static class Factory extends BasicAttributeFactory<AttributeGroup> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public AttributeGroup loadFromConfig(ConfigurationSection config) {
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
            return new GroupAttribute(name, attributes, config.getBoolean("spacing", true));
        }

        @Override
        public AttributeGroup loadFromNBT(NBTTagCompound compound) {
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
            return new GroupAttribute(name, attributes, !compound.hasKey("spacing") || compound.getBoolean("spacing"));
        }

    }

}
