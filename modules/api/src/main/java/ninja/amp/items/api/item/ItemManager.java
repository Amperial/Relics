/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item;

import ninja.amp.items.api.config.ItemConfig;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface ItemManager {

    Item findItem(Player player, UUID itemId);

    Item findItem(Inventory inventory, UUID itemId);

    Item findItem(ItemStack[] contents, UUID itemId);

    ItemStack findItemStack(Player player, UUID itemId);

    ItemStack findItemStack(Inventory inventory, UUID itemId);

    ItemStack findItemStack(ItemStack[] contents, UUID itemId);

    boolean isItem(ItemStack itemStack);

    Item getItem(ItemStack itemStack);

    Item getItem(NBTTagCompound compound);

    Item getItem(ConfigurationSection config, Object... args);

    Item getItem(ItemConfig config, Object... args);

    Item getItem(String item, Object... args);

    boolean hasItemConfig(String item);

    ItemConfig getItemConfig(String item);

    Map<String, ItemConfig> getItemConfigs();

    void registerItemConfigs(Collection<? extends ItemConfig> items, Plugin plugin);

    void registerItemConfig(ItemConfig item, Plugin plugin);

    boolean hasAttributeType(String type);

    AttributeType getAttributeType(String type);

    Map<String, AttributeType> getAttributeTypes();

    void registerAttributeTypes(Collection<? extends AttributeType> types, Plugin plugin);

    void registerAttributeType(AttributeType type, Plugin plugin);

    ItemAttribute loadAttribute(String name, ConfigurationSection config);

    ItemAttribute loadAttribute(String name, NBTTagCompound compound);

    ItemFactory getFactory();

}
