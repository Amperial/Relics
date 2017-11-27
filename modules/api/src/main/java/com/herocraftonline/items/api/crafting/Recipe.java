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
package com.herocraftonline.items.api.crafting;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Defines a crafting recipe.
 *
 * @author Austin Payne
 */
public interface Recipe extends Predicate<Map<Position, ItemStack>> {

    /**
     * Gets the result of crafting the recipe.
     *
     * @return the recipe's result
     */
    Result getResult();

    /**
     * Gets the required inventory dimensions to craft the recipe.
     *
     * @return the recipe's dimensions
     */
    Dimensions getDimensions();

    /**
     * Gets the recipe's optional map renderer to render the recipe on a map.
     *
     * @return the recipe's map renderer
     */
    Optional<MapRenderer> getMapRenderer();

    /**
     * Gets a string list representing the recipe's ingredients.
     *
     * @return the recipe's ingredient list
     */
    List<String> getIngredientList();

    void saveToNBT(NBTTagCompound compound);

    interface RecipeFactory<T extends Recipe> {

        T loadFromNBT(NBTTagCompound compound);

        T loadFromConfig(ConfigurationSection config);

    }

}
