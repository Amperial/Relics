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
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.Group;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.SocketColor;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GemAttribute extends BaseAttribute<Gem> implements Gem {

    private String displayName;
    private SocketColor color;
    private Group attributes;
    private Item item;

    public GemAttribute(String name, String displayName, SocketColor color, Group attributes) {
        super(name, DefaultAttribute.GEM);
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
        return attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttribute(Class<? extends Attribute> type) {
        return attributes.hasAttribute(type);
    }

    @Override
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public Optional<Attribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<Attribute> getAttribute(Predicate<Attribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type) {
        return attributes.getAttribute(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, String name) {
        return attributes.getAttribute(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttribute(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<Attribute> getAttributes(Predicate<Attribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type) {
        return attributes.getAttributes(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributes(type, predicate);
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEach(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Consumer<T> action) {
        attributes.forEach(type, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEach(type, predicate, action);
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttributeDeep(Class<? extends Attribute> type) {
        return attributes.hasAttributeDeep(type);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type) {
        return attributes.getAttributeDeep(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, String name) {
        return attributes.getAttributeDeep(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributeDeep(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type) {
        return attributes.getAttributesDeep(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributesDeep(type, predicate);
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEachDeep(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Consumer<T> action) {
        attributes.forEachDeep(type, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEachDeep(type, predicate, action);
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
        public Gem loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attributes
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("name", "?"));
            SocketColor color = SocketColor.fromName(config.getString("color", "yellow"));
            Group attributes = DefaultAttribute.GROUP.getFactory().loadFromConfig("attributes", config);

            // Create gem
            Gem gem = new GemAttribute(name, displayName, color, attributes);

            // Load item
            if (config.isConfigurationSection("item")) {
                ConfigurationSection itemConfig = config.getConfigurationSection("item");
                Item item = itemManager.getItem(itemConfig);
                gem.setItem(item);
            }

            return gem;
        }

        @Override
        public Gem loadFromNBT(String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and attribute
            String displayName = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            NBTTagCompound attributesCompound = compound.getCompound("attributes");
            Group attributes = (Group) itemManager.loadAttribute("attributes", attributesCompound);

            // Create gem
            Gem gem = new GemAttribute(name, displayName, color, attributes);

            // Load item
            if (compound.hasKey("item")) {
                NBTTagCompound itemCompound = compound.getCompound("item");
                Item item = itemManager.getItem(itemCompound);
                gem.setItem(item);
            }

            return gem;
        }
    }

}
