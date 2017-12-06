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
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.Group;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.SocketColor;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GemAttribute extends BaseAttribute<Gem> implements Gem {

    private String displayName;
    private SocketColor color;
    private Group attributes;
    private Item item;

    public GemAttribute(Item item, String name, String displayName, SocketColor color, Group attributes) {
        super(item, name, DefaultAttributes.GEM);
        this.displayName = displayName;
        this.color = color;
        this.attributes = attributes;

        setLore((lore, prefix) -> attributes.getLore().addTo(lore, prefix));
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public SocketColor getColor() {
        return color;
    }

    @Override
    public void setColor(SocketColor color) {
        this.color = color;
    }

    @Override
    public boolean hasItem() {
        return item != null;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean hasAttribute(String name) {
        return hasItem() && attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttribute(Class<? extends Attribute> type) {
        return hasItem() && attributes.hasAttribute(type);
    }

    @Override
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return hasItem() && attributes.hasAttribute(predicate);
    }

    @Override
    public Optional<Attribute> getAttribute(String name) {
        return hasItem() ? attributes.getAttribute(name) : Optional.empty();
    }

    @Override
    public Optional<Attribute> getAttribute(Predicate<Attribute> predicate) {
        return hasItem() ? attributes.getAttribute(predicate) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type) {
        return hasItem() ? attributes.getAttribute(type) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, String name) {
        return hasItem() ? attributes.getAttribute(type, name) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate) {
        return hasItem() ? attributes.getAttribute(type, predicate) : Optional.empty();
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return hasItem() ? attributes.getAttributes() : Collections.emptySet();
    }

    @Override
    public Collection<Attribute> getAttributes(Predicate<Attribute> predicate) {
        return hasItem() ? attributes.getAttributes(predicate) : Collections.emptySet();
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type) {
        return hasItem() ? attributes.getAttributes(type) : Collections.emptySet();
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type, Predicate<T> predicate) {
        return hasItem() ? attributes.getAttributes(type, predicate) : Collections.emptySet();
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        if (hasItem()) {
            attributes.forEach(action);
        }
    }

    @Override
    public void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        if (hasItem()) {
            attributes.forEach(predicate, action);
        }
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Consumer<T> action) {
        if (hasItem()) {
            attributes.forEach(type, action);
        }
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        if (hasItem()) {
            attributes.forEach(type, predicate, action);
        }
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return hasItem() && attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttributeDeep(Class<? extends Attribute> type) {
        return hasItem() && attributes.hasAttributeDeep(type);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        return hasItem() && attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(String name) {
        return hasItem() ? attributes.getAttributeDeep(name) : Optional.empty();
    }

    @Override
    public Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate) {
        return hasItem() ? attributes.getAttributeDeep(predicate) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type) {
        return hasItem() ? attributes.getAttributeDeep(type) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, String name) {
        return hasItem() ? attributes.getAttributeDeep(type, name) : Optional.empty();
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate) {
        return hasItem() ? attributes.getAttributeDeep(type, predicate) : Optional.empty();
    }

    @Override
    public Collection<Attribute> getAttributesDeep() {
        return hasItem() ? attributes.getAttributesDeep() : Collections.emptySet();
    }

    @Override
    public Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate) {
        return hasItem() ? attributes.getAttributesDeep(predicate) : Collections.emptySet();
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type) {
        return hasItem() ? attributes.getAttributesDeep(type) : Collections.emptySet();
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type, Predicate<T> predicate) {
        return hasItem() ? attributes.getAttributesDeep(type, predicate) : Collections.emptySet();
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        if (hasItem()) {
            attributes.forEachDeep(action);
        }
    }

    @Override
    public void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        if (hasItem()) {
            attributes.forEachDeep(predicate, action);
        }
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Consumer<T> action) {
        if (hasItem()) {
            attributes.forEachDeep(type, action);
        }
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        if (hasItem()) {
            attributes.forEachDeep(type, predicate, action);
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("name", getDisplayName());
        compound.setString("color", getColor().getName());
        NBTTagCompound attributesCompound = NBTTagCompound.create();
        attributes.saveToNBT(attributesCompound);
        compound.setBase("attributes", attributesCompound);
        if (hasItem()) {
            NBTTagCompound item = NBTTagCompound.create();
            getItem().saveToNBT(item);
            compound.setBase("item", item);
        }
    }

    public static class Factory extends BaseAttributeFactory<Gem> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Gem loadFromConfig(Item item, String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attributes
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("name", "?"));
            SocketColor color = SocketColor.fromName(config.getString("color", "yellow"));
            Group attributes = DefaultAttributes.GROUP.getFactory().loadFromConfig(item, "attributes", config);

            // Create gem
            Gem gem = new GemAttribute(item, name, displayName, color, attributes);

            // Load item
            if (config.isConfigurationSection("item")) {
                ConfigurationSection itemConfig = config.getConfigurationSection("item");
                Item gemItem = itemManager.getItem(itemConfig);
                gem.setItem(gemItem);
            }

            return gem;
        }

        @Override
        public Gem loadFromNBT(Item item, String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and attribute
            String displayName = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            NBTTagCompound attributesCompound = compound.getCompound("attributes");
            Group attributes = (Group) itemManager.loadAttribute(item, "attributes", attributesCompound);

            // Create gem
            Gem gem = new GemAttribute(item, name, displayName, color, attributes);

            // Load item
            if (compound.hasKey("item")) {
                NBTTagCompound itemCompound = compound.getCompound("item");
                Item gemItem = itemManager.getItem(itemCompound);
                gem.setItem(gemItem);
            }

            return gem;
        }
    }

}
