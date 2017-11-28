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
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.storage.config.ConfigManager;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.config.ItemConfig;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class ItemManager implements com.herocraftonline.items.api.item.ItemManager {

    private final ItemPlugin plugin;
    private final ItemFactory factory;
    private final Map<String, AttributeType> attributeTypes;
    private final Map<String, ItemConfig> items;

    public ItemManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.factory = new CustomItem.DefaultItemFactory(this);
        this.attributeTypes = new HashMap<>();
        this.items = new HashMap<>();

        // Register default attribute types
        registerAttributeTypes(DefaultAttributes.getTypes(), plugin);

        // Load attribute type lore order
        // TODO: this is somewhat bad
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration attributes = configManager.getConfig(DefaultConfig.ATTRIBUTES);
        int position = 0;
        for (String type : attributes.getStringList("lore-order")) {
            if (hasAttributeType(type)) {
                getAttributeType(type).setLorePosition(position);
                position++;
            }
        }

        // Load item configss
        FileConfiguration itemConfig = configManager.getConfig(DefaultConfig.ITEMS);
        itemConfig.getStringList("items").stream().map(ItemConfig::new).forEach(config -> items.put(config.getItem().toLowerCase(), config));
        items.values().forEach(config -> configManager.registerCustomConfig(config, plugin, false));

        DefaultAttributes.loadFactories(plugin);
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
        return factory.isItem(itemStack);
    }

    @Override
    public Optional<ItemStack> getItemStack(String name, Object... args) {
        return getItem(name, args).flatMap(item -> Optional.of(item.getItem()));
    }

    @Override
    public Optional<Item> getItem(ItemStack itemStack) {
        return Optional.ofNullable(factory.loadFromItemStack(itemStack));
    }

    @Override
    public Item getItem(NBTTagCompound compound) {
        // Ensure compound isnt null for some reason
        if (compound == null) {
            plugin.getMessenger().log(Level.WARNING, "Attempted to get item from null compound");
            return null;
        }

        // Load item from nbt
        return factory.loadFromNBT(compound);
    }

    @Override
    public Item getItem(ConfigurationSection config, Object... args) {
        // Ensure config isnt null for some reason
        if (config == null) {
            plugin.getMessenger().log(Level.WARNING, "Attempted to get item from null config");
            return null;
        }

        // Load item from config
        Item item = factory.loadFromConfig(plugin.getConfigManager().transformConfig(config, args));

        // Item loaded debug
        plugin.getMessenger().debug("Loaded item " + item.getName() + " from config");

        return item;
    }

    @Override
    public Optional<Item> getItem(String item, Object... args) {
        ItemConfig itemConfig = items.get(item.toLowerCase());
        if (itemConfig == null) {
            plugin.getMessenger().log(Level.WARNING, "Attempted to get item from null config");
            return null;
        }

        return Optional.ofNullable(getItem(plugin.getConfigManager().getConfig(itemConfig), args));
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
        this.plugin.getConfigManager().registerCustomConfig(type, plugin, false);
    }

    @Override
    public Attribute loadAttribute(String name, ConfigurationSection config) {
        String type = config.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromConfig(name, config) : null;
    }

    @Override
    public Attribute loadAttribute(String name, NBTTagCompound compound) {
        String type = compound.getString("type");
        return hasAttributeType(type) ? getAttributeType(type).getFactory().loadFromNBT(name, compound) : null;
    }

    @Override
    public ItemFactory getFactory() {
        return factory;
    }

}
