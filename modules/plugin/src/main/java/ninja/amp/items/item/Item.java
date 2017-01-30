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

    private final String name;
    private final Material material;
    private final ItemType type;
    private final AttributeGroup attributes;

    private Item(String name, Material material, ItemType type, AttributeGroup attributes) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemType getType() {
        return type;
    }

    public AttributeGroup getAttributes() {
        return attributes;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());

        // Set ItemMeta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        List<String> lore = new ArrayList<>();
        attributes.getLore().addTo(lore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Set NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(item);
        saveToNBT(compound);
        item = NMSHandler.getInterface().setTagCompound(item, compound);

        return item;
    }

    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("name", getName());
        compound.setString("material", getMaterial().name());
        compound.setString("item-type", getType().getName());
        attributes.saveToNBT(compound);
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

            // Create Item
            return new Item(name, material, type, attribute);
        }

        @Override
        public Item loadFromNBT(NBTTagCompound compound) {
            // Load name, material, type, and attributes
            String name = compound.getString("name");
            Material material = Material.getMaterial(compound.getString("material"));
            ItemType type = itemManager.getItemType(compound.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromNBT(compound);

            // Create Item
            return new Item(name, material, type, attribute);
        }

        @Override
        public Item loadFromItemStack(ItemStack itemStack) {
            // Get ItemStack NBT
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(itemStack);

            // Load from NBT
            return loadFromNBT(compound);
        }

        @Override
        public boolean isItem(ItemStack itemStack) {
            return NMSHandler.getInterface().getTagCompound(itemStack).hasKey("item-type");
        }

    }

}
