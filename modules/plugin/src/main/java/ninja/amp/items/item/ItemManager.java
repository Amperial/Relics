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

import ninja.amp.items.AmpItems;
import ninja.amp.items.config.DefaultConfig;
import ninja.amp.items.config.ItemConfig;
import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private final AmpItems plugin;
    private final Map<String, AttributeType> attributeTypes;
    private final Map<String, ItemType> itemTypes;
    private final Map<String, ItemConfig> items;
    private ItemFactory factory;

    public ItemManager(AmpItems plugin) {
        this.plugin = plugin;
        this.attributeTypes = new HashMap<>();
        this.itemTypes = new HashMap<>();
        this.items = new HashMap<>();
        this.factory = new Item.DefaultItemFactory(this);

        registerAttributeTypes(EnumSet.allOf(DefaultAttributeType.class), plugin);
        registerItemTypes(EnumSet.allOf(DefaultItemType.class), plugin);

        // Load attribute type lore order
        FileConfiguration attributes = plugin.getConfigManager().getConfig(DefaultConfig.ATTRIBUTES);
        int position = 0;
        for (String type : attributes.getStringList("lore-order")) {
            if (hasAttributeType(type)) {
                getAttributeType(type).setLorePosition(position);
                position++;
            }
        }

        // Load default socket accepts
        FileConfiguration sockets = plugin.getConfigManager().getConfig(DefaultConfig.SOCKETS);
        if (sockets.isConfigurationSection("colors")) {
            ConfigurationSection colors = sockets.getConfigurationSection("colors");
            for (SocketColor color : SocketColor.values()) {
                if (colors.isSet(color.getName() + ".accepts")) {
                    List<String> accepts = colors.getStringList(color.getName() + ".accepts");
                    for (String accept : accepts) {
                        SocketColor acceptColor = SocketColor.fromName(accept);
                        if (acceptColor != null) {
                            color.addAccepts(acceptColor);
                        }
                    }
                }
            }
        }

        // Load items
        FileConfiguration itemConfig = plugin.getConfigManager().getConfig(DefaultConfig.ITEMS);
        itemConfig.getStringList("items").forEach(item -> registerItem(new ItemConfig(item), plugin));
    }

    public boolean hasAttributeType(String type) {
        return attributeTypes.containsKey(type);
    }

    public AttributeType getAttributeType(String type) {
        return attributeTypes.get(type);
    }

    public Map<String, AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void registerAttributeTypes(EnumSet<? extends AttributeType> types, JavaPlugin plugin) {
        types.forEach(type -> registerAttributeType(type, plugin));
    }

    public void registerAttributeType(AttributeType type, JavaPlugin plugin) {
        attributeTypes.put(type.getName(), type);
        this.plugin.getConfigManager().registerCustomConfig(type, plugin);
    }

    public ItemAttribute loadAttribute(ConfigurationSection config) {
        String type = config.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromConfig(config) : null;
    }

    public ItemAttribute loadAttribute(NBTTagCompound compound) {
        String type = compound.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromNBT(compound) : null;
    }

    public boolean hasItemType(String type) {
        return itemTypes.containsKey(type);
    }

    public ItemType getItemType(String type) {
        return itemTypes.get(type);
    }

    public Map<String, ItemType> getItemTypeTypes() {
        return itemTypes;
    }

    public void registerItemTypes(EnumSet<? extends ItemType> types, JavaPlugin plugin) {
        types.forEach(type -> registerItemType(type, plugin));
    }

    public void registerItemType(ItemType type, JavaPlugin plugin) {
        itemTypes.put(type.getName(), type);
    }

    public boolean hasItem(String item) {
        return items.containsKey(item);
    }

    public ItemConfig getItemConfig(String item) {
        return items.get(item);
    }

    public boolean isItem(ItemStack itemStack) {
        return factory.isItem(itemStack);
    }

    public Item getItem(ItemStack itemStack) {
        return factory.isItem(itemStack) ? factory.loadFromItemStack(itemStack) : null;
    }

    public Item getItem(NBTTagCompound compound) {
        return factory.loadFromNBT(compound);
    }

    public Item getItem(ConfigurationSection config) {
        return factory.loadFromConfig(config);
    }

    public Item getItem(ItemConfig config) {
        return getItem(plugin.getConfigManager().getConfig(config));
    }

    public Item getItem(String item) {
        return getItem(getItemConfig(item));
    }

    public void registerItem(ItemConfig config, JavaPlugin plugin) {
        items.put(config.getItem(), config);
        this.plugin.getConfigManager().registerCustomConfig(config, plugin);
    }

    public ItemFactory getFactory() {
        return factory;
    }

    public void setFactory(ItemFactory factory) {
        if (factory != null) {
            this.factory = factory;
        }
    }

}
