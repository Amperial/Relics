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
package com.herocraftonline.items.crafting;

import com.herocraftonline.items.api.item.attribute.attributes.crafting.Ingredient;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Recipe;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShapedRecipe implements Recipe {

    private static final Dimensions.Expansion MODE = Dimensions.Expansion.SQUARE;
    private static final Dimensions MAX_DIMENSIONS = new Dimensions(5, 5);

    private final ItemStack result;
    private final Dimensions dimensions;
    private final Map<Position, Ingredient> ingredients = new HashMap<>();

    public ShapedRecipe(ItemStack result, Dimensions dimensions) {
        this.result = result;
        this.dimensions = dimensions;
    }

    public ShapedRecipe(ItemStack result) {
        this(result, new Dimensions(1, 1));
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean matches(Map<Position, ItemStack> items) {
        // Only and all of the positions specified should exist
        if (!ingredients.keySet().equals(items.keySet())) {
            return false;
        }

        // All of the ingredients should match
        return items.entrySet().stream().allMatch(item -> ingredients.get(item.getKey()).matches(item.getValue()));
    }

    public Ingredient getIngredient(Position position) {
        return ingredients.get(position);
    }

    public boolean setIngredient(Position position, Ingredient ingredient) {
        if (!MAX_DIMENSIONS.contains(position)) {
            return false;
        } else if (!dimensions.contains(position)) {
            dimensions.expand(position, MODE, MAX_DIMENSIONS);
        }
        ingredients.put(position, ingredient);
        return true;
    }

}
