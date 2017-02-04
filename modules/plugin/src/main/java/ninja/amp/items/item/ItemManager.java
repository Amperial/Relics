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

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.config.DefaultConfig;
import ninja.amp.items.api.config.ItemConfig;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemFactory;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.item.attributes.DamageAttribute;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.item.attributes.GroupAttribute;
import ninja.amp.items.item.attributes.MinecraftAttribute;
import ninja.amp.items.item.attributes.ModelAttribute;
import ninja.amp.items.item.attributes.RarityAttribute;
import ninja.amp.items.item.attributes.TextAttribute;
import ninja.amp.items.item.attributes.sockets.GemAttribute;
import ninja.amp.items.item.attributes.sockets.SocketAttribute;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemManager implements ninja.amp.items.api.item.ItemManager {

    private final ItemPlugin plugin;
    private final Map<String, AttributeType> attributeTypes;
    private final Map<String, ItemType> itemTypes;
    private final Map<String, ItemConfig> items;
    private ItemFactory factory;

    public ItemManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.attributeTypes = new HashMap<>();
        this.itemTypes = new HashMap<>();
        this.items = new HashMap<>();

        registerAttributeTypes(EnumSet.allOf(DefaultAttributeType.class), plugin);
        registerItemTypes(EnumSet.allOf(DefaultItemType.class), plugin);
        registerItemTypes(EnumSet.allOf(ArmorType.class), plugin);
        registerItemTypes(EnumSet.allOf(ToolType.class), plugin);
        registerItemTypes(EnumSet.allOf(WeaponType.class), plugin);

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
        DefaultAttributeType.DAMAGE.setFactory(new DamageAttribute.DamageFactory(plugin));
        DefaultAttributeType.GEM.setFactory(new GemAttribute.Factory(plugin));
        DefaultAttributeType.GROUP.setFactory(new GroupAttribute.Factory(plugin));
        DefaultAttributeType.MINECRAFT.setFactory(new MinecraftAttribute.MinecraftAttributeFactory(plugin));
        DefaultAttributeType.MODEL.setFactory(new ModelAttribute.Factory(plugin));
        DefaultAttributeType.RARITY.setFactory(new RarityAttribute.Factory(plugin));
        DefaultAttributeType.SOCKET.setFactory(new SocketAttribute.Factory(plugin));
        DefaultAttributeType.TEXT.setFactory(new TextAttribute.Factory(plugin));
    }

    @Override
    public ItemStack findInInventory(Player player, UUID itemId) {
        return findInInventory(player.getInventory(), itemId);
    }

    @Override
    public ItemStack findInInventory(Inventory inventory, UUID itemId) {
        return findInContents(inventory.getContents(), itemId);
    }

    @Override
    public ItemStack findInContents(ItemStack[] contents, UUID itemId) {
        for (ItemStack itemStack : contents) {
            if (itemStack != null && itemStack.getType() != Material.AIR && isItem(itemStack)) {
                Item item = getItem(itemStack);
                if (item.getId().equals(itemId)) {
                    return itemStack;
                }
            }
        }
        return null;
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
        return getItem(getItemConfig(item.toLowerCase()));
    }

    @Override
    public boolean hasItemConfig(String item) {
        return items.containsKey(item.toLowerCase());
    }

    @Override
    public ItemConfig getItemConfig(String item) {
        return items.get(item.toLowerCase());
    }

    @Override
    public Map<String, ItemConfig> getItemConfigs() {
        return Collections.unmodifiableMap(items);
    }

    @Override
    public void registerItemConfigs(Collection<? extends ItemConfig> items, Plugin plugin) {
        items.forEach(item -> registerItemConfig(item, plugin));
    }

    @Override
    public void registerItemConfig(ItemConfig config, Plugin plugin) {
        items.put(config.getItem().toLowerCase(), config);
        this.plugin.getConfigManager().registerCustomConfig(config, plugin);
    }

    @Override
    public boolean hasItemType(String type) {
        return itemTypes.containsKey(type.toLowerCase());
    }

    @Override
    public ItemType getItemType(String type) {
        return itemTypes.get(type.toLowerCase());
    }

    @Override
    public Map<String, ItemType> getItemTypes() {
        return Collections.unmodifiableMap(itemTypes);
    }

    @Override
    public void registerItemTypes(Collection<? extends ItemType> types, Plugin plugin) {
        types.forEach(type -> registerItemType(type, plugin));
    }

    @Override
    public void registerItemType(ItemType type, Plugin plugin) {
        itemTypes.put(type.getName().toLowerCase(), type);
    }

    @Override
    public boolean hasAttributeType(String type) {
        return attributeTypes.containsKey(type.toLowerCase());
    }

    @Override
    public AttributeType getAttributeType(String type) {
        return attributeTypes.get(type.toLowerCase());
    }

    @Override
    public Map<String, AttributeType> getAttributeTypes() {
        return Collections.unmodifiableMap(attributeTypes);
    }

    @Override
    public void registerAttributeTypes(Collection<? extends AttributeType> types, Plugin plugin) {
        types.forEach(type -> registerAttributeType(type, plugin));
    }

    @Override
    public void registerAttributeType(AttributeType type, Plugin plugin) {
        attributeTypes.put(type.getName().toLowerCase(), type);
        this.plugin.getConfigManager().registerCustomConfig(type, plugin);
    }

    @Override
    public ItemAttribute loadAttribute(String name, ConfigurationSection config) {
        String type = config.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromConfig(name, config) : null;
    }

    @Override
    public ItemAttribute loadAttribute(String name, NBTTagCompound compound) {
        String type = compound.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromNBT(name, compound) : null;
    }

    @Override
    public ItemFactory getFactory() {
        return factory;
    }

}
