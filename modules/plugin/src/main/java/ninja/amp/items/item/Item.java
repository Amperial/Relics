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
package ninja.amp.items.item;

import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private final ItemStack itemStack;
    private final ItemType type;
    private final AttributeGroup attributes;

    private Item(ItemStack itemStack, ItemType type, AttributeGroup attributes) {
        this.itemStack = itemStack;
        this.type = type;
        this.attributes = attributes;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemType getType() {
        return type;
    }

    public List<ItemAttribute> getAttribute() {
        return attributes.getAttributes();
    }

    public ItemStack getItem() {
        ItemStack item = itemStack.clone();

        // Set ItemMeta
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        attributes.getLore().addTo(lore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Set NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(item);
        compound.setString("item-type", type.getName());
        attributes.saveToNBT(compound);
        item = NMSHandler.getInterface().setTagCompound(item, compound);

        return item;
    }

    public static class DefaultItemFactory implements ItemFactory {

        private ItemManager itemManager;

        public DefaultItemFactory(ItemManager itemManager) {
            this.itemManager = itemManager;
        }

        @Override
        public Item loadFromConfig(ConfigurationSection config) {
            // Load name, material, type, and attributes
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            Material material = Material.getMaterial(config.getString("material"));
            ItemType type = itemManager.getItemType(config.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig(config);

            // Create ItemStack
            ItemStack base = new ItemStack(material);
            ItemMeta meta = base.getItemMeta();
            meta.setDisplayName(name);
            base.setItemMeta(meta);

            // Create Item
            return new Item(base, type, attribute);
        }

        @Override
        public Item loadFromItemStack(ItemStack itemStack) {
            // We already have the ItemStack, load attributes
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(itemStack);
            ItemType type = itemManager.getItemType(compound.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromNBT(compound);

            return new Item(itemStack, type, attribute);
        }

        @Override
        public boolean isItem(ItemStack itemStack) {
            return NMSHandler.getInterface().getTagCompound(itemStack).hasKey("item-type");
        }

    }

}
