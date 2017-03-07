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
package com.herocraftonline.items.item;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemFactory;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.sockets.SocketColor;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import com.herocraftonline.items.api.storage.config.ItemConfig;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.attributes.DefaultAttributeType;
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
import java.util.Optional;
import java.util.UUID;

public class ItemManager implements com.herocraftonline.items.api.item.ItemManager {

    private final ItemPlugin plugin;
    private final Map<String, AttributeType> attributeTypes;
    private final Map<String, ItemConfig> items;
    private ItemFactory factory;

    public ItemManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.attributeTypes = new HashMap<>();
        this.items = new HashMap<>();

        registerAttributeTypes(EnumSet.allOf(DefaultAttributeType.class), plugin);

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

        setDefaultFactories();
    }

    public void setDefaultFactories() {
        factory = new CustomItem.DefaultItemFactory(this);

        DefaultAttributeType.loadFactories(plugin);
    }

    @Override
    public Optional<Item> findItem(Player player, UUID itemId) {
        return findItem(player.getInventory(), itemId);
    }

    @Override
    public Optional<Item> findItem(Inventory inventory, UUID itemId) {
        return findItem(inventory.getContents(), itemId);
    }

    @Override
    public Optional<Item> findItem(ItemStack[] contents, UUID itemId) {
        for (ItemStack itemStack : contents) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                Optional<Item> item = getItem(itemStack);
                if (item.isPresent() && item.get().getId().equals(itemId)) {
                    return item;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ItemStack> findItemStack(Player player, UUID itemId) {
        return findItemStack(player.getInventory(), itemId);
    }

    @Override
    public Optional<ItemStack> findItemStack(Inventory inventory, UUID itemId) {
        return findItemStack(inventory.getContents(), itemId);
    }

    @Override
    public Optional<ItemStack> findItemStack(ItemStack[] contents, UUID itemId) {
        for (ItemStack itemStack : contents) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                Optional<Item> item = getItem(itemStack);
                if (item.isPresent() && item.get().getId().equals(itemId)) {
                    return Optional.of(itemStack);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR && factory.isItem(itemStack);
    }

    @Override
    public Optional<Item> getItem(ItemStack itemStack) {
        return isItem(itemStack) ? Optional.of(factory.loadFromItemStack(itemStack)) : Optional.empty();
    }

    @Override
    public Item getItem(NBTTagCompound compound) {
        return factory.loadFromNBT(compound);
    }

    @Override
    public Item getItem(ConfigurationSection config, Object... args) {
        // Replace string arguments with numbers where possible
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                try {
                    String itemArg = (String) args[i];
                    double value = Double.valueOf(itemArg);
                    if (itemArg.contains(".")) {
                        args[i] = value;
                    } else {
                        args[i] = (int) value;
                    }
                } catch (NumberFormatException e) {
                    // Arg isn't a number
                }
            }
        }
        return factory.loadFromConfig(plugin.getConfigManager().transformConfig(config, args));
    }

    @Override
    public Item getItem(ItemConfig config, Object... args) {
        return getItem(plugin.getConfigManager().getConfig(config), args);
    }

    @Override
    public Item getItem(String item, Object... args) {
        return hasItemConfig(item) ? getItem(getItemConfig(item.toLowerCase()), args) : null;
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
