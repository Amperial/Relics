/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/**
 * Creates custom item instances from config, nbt, and item stacks.
 *
 * @author Austin Payne
 */
public interface ItemFactory {

    /**
     * Loads an item instance from the given configuration section.
     *
     * @param config the configuration section
     * @return the item
     */
    Item loadFromConfig(ConfigurationSection config);

    /**
     * Loads an item instance from the given nbt tag compound.
     *
     * @param compound the tag compound
     * @return the item
     */
    Item loadFromNBT(NBTTagCompound compound);

    /**
     * Loads an item instance from the given item stack.
     *
     * @param itemStack the item stack
     * @return the item
     */
    Item loadFromItemStack(ItemStack itemStack);

    /**
     * Checks if the given item stack is a custom item.
     *
     * @param itemStack the item stack
     * @return {@code true} if the item stack is a custom item, else {@code false}
     */
    boolean isItem(ItemStack itemStack);

}
