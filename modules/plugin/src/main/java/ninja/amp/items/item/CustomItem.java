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

import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemFactory;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem implements Item {

    private final String name;
    private final Material material;
    private final ItemType type;
    private final AttributeGroup attributes;

    private CustomItem(String name, Material material, ItemType type, AttributeGroup attributes) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public AttributeGroup getAttributes() {
        return attributes;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());

        // Set ItemMeta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        List<String> lore = new ArrayList<>();
        attributes.getLore().addTo(lore, "");
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Set NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(item);
        saveToNBT(compound);
        item = NMSHandler.getInterface().setTagCompound(item, compound);

        return item;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        attributes.saveToNBT(compound);
        compound.setString("name", getName());
        compound.setString("material", getMaterial().name());
        compound.setString("item-type", getType().getName());
    }

    public static class DefaultItemFactory implements ItemFactory {

        private ItemManager itemManager;

        public DefaultItemFactory(ItemManager itemManager) {
            this.itemManager = itemManager;
        }

        @Override
        public CustomItem loadFromConfig(ConfigurationSection config) {
            // Load name, material, type, and attributes
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            Material material = Material.getMaterial(config.getString("material"));
            ItemType type = itemManager.getItemType(config.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig(config);

            // Create Item
            return new CustomItem(name, material, type, attribute);
        }

        @Override
        public CustomItem loadFromNBT(NBTTagCompound compound) {
            // Load name, material, type, and attributes
            String name = compound.getString("name");
            Material material = Material.getMaterial(compound.getString("material"));
            ItemType type = itemManager.getItemType(compound.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromNBT(compound);

            // Create Item
            return new CustomItem(name, material, type, attribute);
        }

        @Override
        public CustomItem loadFromItemStack(ItemStack itemStack) {
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
