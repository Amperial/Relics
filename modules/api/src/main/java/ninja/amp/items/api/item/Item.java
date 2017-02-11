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

import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeContainer;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * A custom item instance containing attributes and all other item information.
 *
 * @author Austin Payne
 */
public interface Item extends AttributeContainer, Equippable, Clickable {

    /**
     * Gets the name of the item.
     *
     * @return the item's name
     */
    String getName();

    /**
     * Gets the unique id of the item.
     *
     * @return the item's id
     */
    UUID getId();

    /**
     * Gets the material of the item.
     *
     * @return the item's material
     */
    Material getMaterial();

    /**
     * Gets the type of the item.
     *
     * @return the item's type
     */
    ItemType getType();

    /**
     * Creates an item stack representing the item.
     *
     * @return the item stack
     */
    ItemStack getItem();

    /**
     * Updates the name, lore, and nbt of an item stack representing the item.
     *
     * @param item the item stack
     * @return the updated item stack
     */
    ItemStack updateItem(ItemStack item);

    /**
     * Adds attributes to the custom item.
     *
     * @param attributes the attributes to add
     */
    void addAttribute(ItemAttribute... attributes);

    /**
     * Removes an attribute from the custom item.
     *
     * @param attribute the attribute to remove
     */
    void removeAttribute(ItemAttribute attribute);

    /**
     * Checks if the custom item has been equipped.
     *
     * @return {@code true} if the item is equipped, else {@code false}
     */
    boolean isEquipped();

    /**
     * Saves all necessary item information to an nbt tag compound.
     *
     * @param compound the tag compound
     */
    void saveToNBT(NBTTagCompound compound);

}
