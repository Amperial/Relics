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
package com.herocraftonline.items.api.item.attribute.attributes.crafting;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;

import java.util.Map;
import java.util.Optional;

/**
 * Defines a crafting recipe.
 *
 * @author Austin Payne
 */
public interface Recipe {

    /**
     * Gets the resulting item of crafting the recipe.
     *
     * @return the recipe's result
     */
    ItemStack getResult();

    /**
     * Gets the required inventory dimensions to craft the recipe.
     *
     * @return the recipe's dimensions
     */
    Dimensions getDimensions();

    /**
     * Checks if the given item configuration matches the crafting recipe.
     *
     * @param items the item ingredients
     * @return {@code true} if the items matches the recipe, else {@code false}
     */
    boolean matches(Map<Position, ItemStack> items);

    /**
     *
     * @return
     */
    Optional<MapRenderer> getMapRenderer();

    void saveToNBT(NBTTagCompound compound);

    interface RecipeFactory<T extends Recipe> {

        T loadFromNBT(NBTTagCompound compound);

        T loadFromConfig(ConfigurationSection config);

    }

}
