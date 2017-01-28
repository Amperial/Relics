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

import net.md_5.bungee.api.ChatColor;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public static final ItemFactory DEFAULT_FACTORY = new ItemFactory();

    private final ItemStack base;
    private final ItemAttribute attribute;

    public Item(ItemStack base, ItemAttribute attribute) {
        this.base = base;
        this.attribute = attribute;
    }

    public ItemStack getItem() {
        ItemStack item = base.clone();

        // Set ItemMeta
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        attribute.getLore().addTo(lore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Set NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(item);
        attribute.getType().getFactory().saveToNBT(attribute, compound);
        item = NMSHandler.getInterface().setTagCompound(item, compound);

        return item;
    }

    public ItemAttribute getAttribute() {
        return attribute;
    }

    public static class ItemFactory {

        public Item loadFromConfig(FileConfiguration config) {
            // Load ItemStack
            Material material = Material.getMaterial(config.getString("material"));
            ItemStack base = new ItemStack(material);
            ItemMeta meta = base.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("display-name")));
            base.setItemMeta(meta);

            // Load ItemAttribute
            ItemAttribute attribute = DefaultAttributeType.GROUP.getFactory().loadFromConfig(config);

            return new Item(base, attribute);
        }

        public Item loadFromItemStack(ItemStack itemStack) {
            // We already have the ItemStack, Load ItemAttribute
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(itemStack);
            ItemAttribute attribute = DefaultAttributeType.GROUP.getFactory().loadFromNBT(compound);

            return new Item(itemStack, attribute);
        }

        public void saveToItemStack(Item item, ItemStack itemStack) {
            
        }

    }

}
