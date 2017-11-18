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
import com.herocraftonline.items.api.item.attribute.AttributeContainer;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A custom item instance containing attributes and all other item information.
 *
 * @author Austin Payne
 */
public interface Item extends AttributeContainer, Clickable, Equippable {

    /**
     * Gets the unique id of the item.
     *
     * @return the item's id
     */
    UUID getId();

    /**
     * Gets the name of the item.
     *
     * @return the item's name
     */
    String getName();

    /**
     * Gets the material of the item.
     *
     * @return the item's material
     */
    Material getMaterial();

    /**
     * Gets the enchantments of the item.
     *
     * @return the item's enchantments
     */
    Map<Enchantment, Integer> getEnchantments();

    /**
     * Checks if the item is unbreakable.
     *
     * @return {@code true} if the item is unbreakable
     */
    boolean isUnbreakable();

    /**
     * Gets the type of the item.
     *
     * @return the item's type
     */
    ItemType getType();

    /**
     * Checks if the item is currently equipped.
     *
     * @return {@code true} if the item is equipped, else {@code false}
     */
    boolean isEquipped();

    /**
     * Creates an item stack representing the item.
     *
     * @return the item stack
     */
    ItemStack getItem();

    /**
     * Attempts to update the given item stack to represent the item.
     *
     * @param item the item stack
     * @return empty optional if successfully updated, else optional containing updated item copy
     */
    Optional<ItemStack> updateItem(ItemStack item);

    /**
     * Adds attributes to the custom item.
     *
     * @param attributes the attributes to add
     */
    void addAttribute(Attribute... attributes);

    /**
     * Removes an attribute from the custom item.
     *
     * @param attribute the attribute to remove
     */
    void removeAttribute(Attribute attribute);

    /**
     * Saves all necessary item information to an nbt tag compound.
     *
     * @param compound the tag compound
     */
    void saveToNBT(NBTTagCompound compound);

}
