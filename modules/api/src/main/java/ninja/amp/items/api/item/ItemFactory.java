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

import ninja.amp.items.nms.nbt.NBTTagCompound;
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
