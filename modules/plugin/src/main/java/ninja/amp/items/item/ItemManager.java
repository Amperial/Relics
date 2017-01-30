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
import ninja.amp.items.api.config.DefaultConfig;
import ninja.amp.items.api.config.ItemConfig;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemFactory;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.item.attributes.GroupAttribute;
import ninja.amp.items.item.attributes.RarityAttribute;
import ninja.amp.items.item.attributes.TextAttribute;
import ninja.amp.items.item.attributes.sockets.GemAttribute;
import ninja.amp.items.item.attributes.sockets.SocketAttribute;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager implements ninja.amp.items.api.item.ItemManager {

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
        itemConfig.getStringList("items").forEach(item -> registerItemConfig(new ItemConfig(item), plugin));
    }

    public void setDefaultFactories() {
        factory = new CustomItem.DefaultItemFactory(this);

        // TODO: Abstract this?
        DefaultAttributeType.RARITY.setFactory(new RarityAttribute.RarityAttributeFactory(plugin));
        DefaultAttributeType.TEXT.setFactory(new TextAttribute.TextAttributeFactory(plugin));
        DefaultAttributeType.SOCKET.setFactory(new SocketAttribute.SocketFactory(plugin));
        DefaultAttributeType.GEM.setFactory(new GemAttribute.GemFactory(plugin));
        DefaultAttributeType.GROUP.setFactory(new GroupAttribute.AttributeGroupFactory(plugin));
    }

    @Override
    public boolean isItem(ItemStack itemStack) {
        return factory.isItem(itemStack);
    }

    @Override
    public Item getItem(ItemStack itemStack) {
        return factory.isItem(itemStack) ? factory.loadFromItemStack(itemStack) : null;
    }

    @Override
    public Item getItem(NBTTagCompound compound) {
        return factory.loadFromNBT(compound);
    }

    @Override
    public Item getItem(ConfigurationSection config) {
        return factory.loadFromConfig(config);
    }

    @Override
    public Item getItem(ItemConfig config) {
        return getItem(plugin.getConfigManager().getConfig(config));
    }

    @Override
    public Item getItem(String item) {
        return getItem(getItemConfig(item));
    }

    @Override
    public boolean hasItemConfig(String item) {
        return items.containsKey(item);
    }

    @Override
    public ItemConfig getItemConfig(String item) {
        return items.get(item);
    }

    @Override
    public Map<String, ItemConfig> getItemConfigs() {
        return items;
    }

    @Override
    public void registerItemConfigs(Collection<? extends ItemConfig> items, JavaPlugin plugin) {
        items.forEach(item -> registerItemConfig(item, plugin));
    }

    @Override
    public void registerItemConfig(ItemConfig config, JavaPlugin plugin) {
        items.put(config.getItem(), config);
        this.plugin.getConfigManager().registerCustomConfig(config, plugin);
    }

    @Override
    public boolean hasItemType(String type) {
        return itemTypes.containsKey(type);
    }

    @Override
    public ItemType getItemType(String type) {
        return itemTypes.get(type);
    }

    @Override
    public Map<String, ItemType> getItemTypes() {
        return itemTypes;
    }

    @Override
    public void registerItemTypes(Collection<? extends ItemType> types, JavaPlugin plugin) {
        types.forEach(type -> registerItemType(type, plugin));
    }

    @Override
    public void registerItemType(ItemType type, JavaPlugin plugin) {
        itemTypes.put(type.getName(), type);
    }

    @Override
    public boolean hasAttributeType(String type) {
        return attributeTypes.containsKey(type);
    }

    @Override
    public AttributeType getAttributeType(String type) {
        return attributeTypes.get(type);
    }

    @Override
    public Map<String, AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    @Override
    public void registerAttributeTypes(Collection<? extends AttributeType> types, JavaPlugin plugin) {
        types.forEach(type -> registerAttributeType(type, plugin));
    }

    @Override
    public void registerAttributeType(AttributeType type, JavaPlugin plugin) {
        attributeTypes.put(type.getName(), type);
        this.plugin.getConfigManager().registerCustomConfig(type, plugin);
    }

    @Override
    public ItemAttribute loadAttribute(ConfigurationSection config) {
        String type = config.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromConfig(config) : null;
    }

    @Override
    public ItemAttribute loadAttribute(NBTTagCompound compound) {
        String type = compound.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromNBT(compound) : null;
    }

    @Override
    public ItemFactory getFactory() {
        return factory;
    }

}
