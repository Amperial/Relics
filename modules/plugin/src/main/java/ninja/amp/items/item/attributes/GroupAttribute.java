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
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeContainer;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeContainer;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GroupAttribute extends BasicAttributeContainer implements AttributeGroup {

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
    public Collection<ItemAttribute> getAttributes() {
        return attributes.values();
    }

    @Override
    public Map<String, ItemAttribute> getAttributesByName() {
        return attributes;
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        for (ItemAttribute attribute : getAttributes()) {
            if (predicate.test(attribute)) {
                return true;
            }
            if (attribute instanceof AttributeContainer) {
                if (((AttributeContainer) attribute).hasAttributeDeep(predicate)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        for (ItemAttribute attribute : getAttributes()) {
            if (clazz.isAssignableFrom(attribute.getClass()) && predicate.test((T) attribute)) {
                return Optional.of((T) attribute);
            }
            if (attribute instanceof AttributeContainer) {
                Optional<T> optional = ((AttributeContainer) attribute).getAttributeDeep(predicate, clazz);
                if (optional.isPresent()) {
                    return optional;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        for (ItemAttribute attribute : getAttributes()) {
            action.accept(attribute);
            if (attribute instanceof AttributeContainer) {
                ((AttributeContainer) attribute).forEachDeep(action);
            }
        }
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
        forEach(attribute -> attribute.onEquip(player));
    }

    @Override
    public void onUnEquip(Player player) {
        forEach(attribute -> attribute.onUnEquip(player));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagCompound attributes = NBTTagCompound.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            attributes.setBase(attribute.getName(), attributeCompound);
        }
        compound.setBase("attributes", attributes);
        compound.setBoolean("spacing", spacing);
    }

    public static class Factory extends BasicAttributeFactory<AttributeGroup> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public AttributeGroup loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            Map<String, ItemAttribute> attributeMap = new TreeMap<>();
            if (config.isConfigurationSection("attributes")) {
                ConfigurationSection attributes = config.getConfigurationSection("attributes");
                attributes.getKeys(false).stream().filter(attributes::isConfigurationSection).forEach(attributeName -> {
                    ConfigurationSection attributeSection = attributes.getConfigurationSection(attributeName);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeName, attributeSection);
                    if (attribute != null) {
                        attributeMap.put(attributeName, attribute);
                    }
                });
            }

            // Create attribute group
            return new GroupAttribute(name, attributeMap, config.getBoolean("spacing", true));
        }

        @Override
        public AttributeGroup loadFromNBT(String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            Map<String, ItemAttribute> attributeMap = new TreeMap<>();
            if (compound.hasKey("attributes")) {
                NBTTagCompound attributes = compound.getCompound("attributes");
                attributes.getKeySet().forEach(attributeName -> {
                    NBTTagCompound attributeCompound = attributes.getCompound(attributeName);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeName, attributeCompound);
                    if (attribute != null) {
                        attributeMap.put(attributeName, attribute);
                    }
                });
            }

            // Create attribute group
            return new GroupAttribute(name, attributeMap, !compound.hasKey("spacing") || compound.getBoolean("spacing"));
        }

    }

}
