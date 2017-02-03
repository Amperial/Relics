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
package ninja.amp.items.item.attributes.sockets;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.sockets.Gem;
import ninja.amp.items.api.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GemAttribute extends BasicAttribute implements Gem {

    private SocketColor color;
    private AttributeGroup attributes;
    private Item item;

    public GemAttribute(String name, SocketColor color, AttributeGroup attributes) {
        super(name, DefaultAttributeType.GEM);
        this.color = color;
        this.attributes = attributes;

        setLore((lore, prefix) -> attributes.getLore().addTo(lore, prefix));
    }

    @Override
    public String getDisplayName() {
        // TODO: Configurable
        return color.getChatColor() + "(" + ChatColor.GRAY + getName() + color.getChatColor() + ")";
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
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<ItemAttribute> getAttributes(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz) {
        return attributes.getAttribute(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(String name, Class<T> clazz) {
        return attributes.getAttributeDeep(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttribute(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributeDeep(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Class<T> clazz) {
        return attributes.getAttributes(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz) {
        return attributes.getAttributesDeep(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributes(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributesDeep(predicate, clazz);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEach(action, predicate);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEachDeep(action, predicate);
    }

    @Override
    public boolean canEquip(Player player) {
        return attributes.canEquip(player);
    }

    @Override
    public void onEquip(Player player) {
        attributes.onEquip(player);
    }

    @Override
    public void onUnEquip(Player player) {
        attributes.onUnEquip(player);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
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

    public static class Factory extends BasicAttributeFactory<Gem> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Gem loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attributes
            name = ChatColor.translateAlternateColorCodes('&', name);
            SocketColor color = SocketColor.fromName(config.getString("color"));
            AttributeGroup attributes = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig("attributes", config);

            // Create gem
            Gem gem = new GemAttribute(name, color, attributes);

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
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            NBTTagCompound attributesCompound = compound.getCompound("attributes");
            AttributeGroup attributes = (AttributeGroup) itemManager.loadAttribute("attributes", attributesCompound);

            // Create gem
            Gem gem = new GemAttribute(name, color, attributes);

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
