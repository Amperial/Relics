/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item;

import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.storage.config.ItemConfig;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Manages item finding, loading, generating and more.
 *
 * @author Austin Payne
 */
public interface ItemManager {

    /**
     * Finds the item with a certain id in the given player's inventory.
     *
     * @param player the player
     * @param itemId the item's id
     * @return the item
     */
    Optional<Item> findItem(Player player, UUID itemId);

    /**
     * Finds the item with a certain id in the given inventory.
     *
     * @param inventory the inventory
     * @param itemId    the item's id
     * @return the item
     */
    Optional<Item> findItem(Inventory inventory, UUID itemId);

    /**
     * Finds the item with a certain id in the given item stack contents.
     *
     * @param contents the item stacks
     * @param itemId   the item's id
     * @return the item
     */
    Optional<Item> findItem(ItemStack[] contents, UUID itemId);

    /**
     * Finds the item stack of an item with a certain id in the given player's inventory.
     *
     * @param player the player
     * @param itemId the item's id
     * @return the item's item stack
     */
    Optional<ItemStack> findItemStack(Player player, UUID itemId);

    /**
     * Finds the item stack of an item with a certain id in the given inventory.
     *
     * @param inventory the inventory
     * @param itemId    the item's id
     * @return the item's item stack
     */
    Optional<ItemStack> findItemStack(Inventory inventory, UUID itemId);

    /**
     * Finds the item stack of an item with a certain id in the given item stack contents.
     *
     * @param contents the item stacks
     * @param itemId   the item's id
     * @return the item's item stack
     */
    Optional<ItemStack> findItemStack(ItemStack[] contents, UUID itemId);

    /**
     * Checks if a certain item stack is also an item.
     *
     * @param itemStack the item
     * @return {@code true} if the item stack is an item, else {@code false}
     */
    boolean isItem(ItemStack itemStack);

    /**
     * Loads an item of a certain name with the given args and gets its itemstack.
     *
     * @param name the item name
     * @param args the args
     * @return the generated itemstack
     */
    Optional<ItemStack> getItemStack(String name, Object... args);

    /**
     * Gets the item represented by an item stack.
     *
     * @param itemStack the item stack
     * @return the item
     */
    Optional<Item> getItem(ItemStack itemStack);

    /**
     * Gets the item represented by an nbt tag compound.
     *
     * @param compound the tag compound
     * @return the item
     */
    Item getItem(NBTTagCompound compound);

    /**
     * Loads an item from a configuration section with the given args.
     *
     * @param config the configuration section
     * @param args   the args
     * @return the generated item
     */
    Item getItem(ConfigurationSection config, Object... args);

    /**
     * Loads an item from an item config with the given args.
     *
     * @param config the item config
     * @param args   the args
     * @return the generated item
     */
    Item getItem(ItemConfig config, Object... args);

    /**
     * Loads an item of a certain name with the given args.
     *
     * @param item the item name
     * @param args the args
     * @return the generated item
     */
    Optional<Item> getItem(String item, Object... args);

    /**
     * Checks if an item type of a certain name is registered
     *
     * @param name the item type name
     * @return {@code true} if the item type is registered, else {@code false}
     */
    boolean hasItemType(String name);

    /**
     * Gets the item type with the given name.
     *
     * @param name the name of the item type
     * @return the item type, or {@code null} if not found
     */
    ItemType getItemType(String name);

    /**
     * Gets the item type with the given name. If one is not found, and {@code createAsTransientIfNotFound}
     * is {@code true}, then a new one flagged as transient will be created and returned.
     *
     * @param name the name of the item type
     * @param createAsTransientIfNotFound weather to create one if not found or not
     * @return the item type, or {@code null} if {@code createAsTransientIfNotFound} is {@code false} and not found
     */
    ItemType getItemType(String name, boolean createAsTransientIfNotFound);

    /**
     * Gets all item types
     *
     * @return all item types
     */
    Collection<? extends ItemType> getItemTypes();

    /**
     * Checks if an item config of a certain name is registered.
     *
     * @param item the item name
     * @return {@code true} if the item config is registered, else {@code false}
     */
    boolean hasItemConfig(String item);

    /**
     * Gets the item config of a certain name.
     *
     * @param item the item name
     * @return the item config
     */
    ItemConfig getItemConfig(String item);

    /**
     * Get all registered item configs by name.
     *
     * @return the registered item configs
     */
    Map<String, ItemConfig> getItemConfigs();

    /**
     * Registers the given item configs.
     *
     * @param items  the item configs to register
     * @param plugin the plugin with the item configs
     */
    void registerItemConfigs(Collection<? extends ItemConfig> items, Plugin plugin);

    /**
     * Registers the given item config.
     *
     * @param item   the item config to register
     * @param plugin the plugin with the item config
     */
    void registerItemConfig(ItemConfig item, Plugin plugin);

    /**
     * Checks if the attribute type of a certain name is registered.
     *
     * @param type the attribute type's name
     * @return {@code true} if the attribute type is registered, else {@code false}
     */
    boolean hasAttributeType(String type);

    /**
     * Gets the attribute type of a certain name.
     *
     * @param type the attribute type's name
     * @return the attribute type
     */
    AttributeType getAttributeType(String type);

    /**
     * Gets all registered attribute types by name.
     *
     * @return the registered attribute types
     */
    Map<String, AttributeType> getAttributeTypes();

    /**
     * Registers the given attribute types.
     *
     * @param types  the attribute types to register
     * @param plugin the plugin with the attribute types
     */
    void registerAttributeTypes(Collection<? extends AttributeType> types, Plugin plugin);

    /**
     * Registers the given attribute type.
     *
     * @param type   the attribute type to register
     * @param plugin the plugin with the attribute type
     */
    void registerAttributeType(AttributeType type, Plugin plugin);

    /**
     * Loads an item attribute with the given name from a configuration section.
     *
     * @param name   the item attribute's name
     * @param config the configuration section
     * @return the item attribute
     */
    Attribute loadAttribute(String name, ConfigurationSection config);

    /**
     * Loads an item attribute with the given name from an nbt tag compound.
     *
     * @param name     the item attribute's name
     * @param compound the tag compound
     * @return the item attribute
     */
    Attribute loadAttribute(String name, NBTTagCompound compound);

    /**
     * Gets the item manager's item factory.
     *
     * @return the item factory
     */
    ItemFactory getFactory();

}
