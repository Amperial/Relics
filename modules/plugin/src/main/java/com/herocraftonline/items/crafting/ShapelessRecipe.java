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

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Ingredient;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent.ReagentType;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Recipe;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import com.herocraftonline.items.api.util.InventoryUtil.Slot;
import com.herocraftonline.items.crafting.ingredients.BasicIngredient;
import com.herocraftonline.items.crafting.ingredients.reagents.NormalReagent;
import com.herocraftonline.items.crafting.ingredients.reagents.RelicReagent;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ShapelessRecipe implements Recipe {

    private static final Dimensions.Expansion MODE = Dimensions.Expansion.WIDE;
    private static final Dimensions MAX_DIMENSIONS = new Dimensions(5, 5);

    private final ItemStack result;
    private final Dimensions dimensions;
    private final Set<Ingredient> ingredients = new HashSet<>();

    public ShapelessRecipe(ItemStack result, Dimensions dimensions) {
        this.result = result;
        this.dimensions = dimensions;
    }

    public ShapelessRecipe(ItemStack result) {
        this(result, new Dimensions(1, 1));
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    @Override
    public Dimensions getDimensions() {
        return dimensions;
    }

    @Override
    public boolean matches(Map<Position, ItemStack> items) {
        Set<Ingredient> ingredients = new HashSet<>(getIngredients());

        // Every item should match a specified ingredient
        for (ItemStack item : items.values()) {
            Optional<Ingredient> ingredient = ingredients.stream().filter(ing -> ing.matches(item)).findAny();
            if (ingredient.isPresent()) {
                ingredients.remove(ingredient.get());
            } else {
                return false;
            }
        }

        // All ingredients should be matched at this point
        return ingredients.isEmpty();
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean addIngredient(Ingredient ingredient) {
        if (ingredients.size() == dimensions.size()) {
            if (ingredients.size() == MAX_DIMENSIONS.size()) {
                return false;
            }
            dimensions.expand(new Slot(ingredients.size()), MODE, MAX_DIMENSIONS);
        }
        ingredients.add(ingredient);
        return true;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        // Set type
        compound.setString("type", "shapeless");

        // Save result
        compound.setString("result", ItemUtil.serialize(getResult()));

        // Save dimensions
        NBTTagCompound dimensions = NBTTagCompound.create();
        dimensions.setInt("width", getDimensions().getWidth());
        dimensions.setInt("height", getDimensions().getHeight());
        compound.setBase("dimensions", dimensions);

        // Save ingredients
        NBTTagList ingredientList = NBTTagList.create();
        for (Ingredient ingredient : getIngredients()) {
            NBTTagCompound ingredientCompound = NBTTagCompound.create();
            ReagentType reagentType = ingredient.getType();
            if (reagentType instanceof RelicReagent) {
                ingredientCompound.setString("reagent", ((RelicReagent) reagentType).getName());
            } else {
                ingredientCompound.setString("material", ((NormalReagent) reagentType).getMaterial().name());
            }
            ingredientCompound.setInt("amount", ingredient.getAmount());
            ingredientList.addBase(ingredientCompound);
        }
        compound.setBase("ingredients", ingredientList);
    }

    public static class Factory implements RecipeFactory<ShapelessRecipe> {

        @Override
        public ShapelessRecipe loadFromNBT(NBTTagCompound compound) {
            // Load result
            ItemStack result = ItemUtil.deserialize(compound.getString("result"));

            // Load dimensions
            NBTTagCompound dimensionsCompound = compound.getCompound("dimensions");
            Dimensions dimensions = new Dimensions(dimensionsCompound.getInt("width"), dimensionsCompound.getInt("height"));

            // Create recipe
            ShapelessRecipe recipe = new ShapelessRecipe(result, dimensions);

            // Load ingredients
            NBTTagList ingredientList = compound.getList("ingredients", 10);
            for (int i = 0; i < ingredientList.size(); i++) {
                NBTTagCompound ingredientCompound = ingredientList.getCompound(i);
                ReagentType reagentType;
                if (ingredientCompound.hasKey("reagent")) {
                    reagentType = new RelicReagent(ingredientCompound.getString("reagent"));
                } else {
                    reagentType = new NormalReagent(Material.valueOf(ingredientCompound.getString("material")));
                }
                recipe.addIngredient(new BasicIngredient(reagentType, ingredientCompound.getInt("amount")));
            }

            return recipe;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ShapelessRecipe loadFromConfig(ConfigurationSection config) {
            // Load result
            ItemStack result;
            ConfigurationSection resultConfig = config.getConfigurationSection("result");
            if (resultConfig.isString("item-type")) {
                result = Relics.instance().getItemManager().getItem(resultConfig).getItem();
            } else {
                String material = resultConfig.getString("material");
                int amount = resultConfig.getInt("amount", 1);
                result = new ItemStack(Material.valueOf(material), amount);
            }

            // Create recipe
            ShapelessRecipe recipe;
            if (config.isConfigurationSection("dimensions")) {
                ConfigurationSection dimensions = config.getConfigurationSection("dimensions");
                recipe = new ShapelessRecipe(result, new Dimensions(dimensions.getInt("width"), dimensions.getInt("height")));
            } else {
                recipe = new ShapelessRecipe(result);
            }

            // Load ingredients
            List<Map<?, ?>> ingredients = config.getMapList("ingredients");
            for (Map<?, ?> ingredientConfig : ingredients) {
                ReagentType reagentType;
                if (ingredientConfig.containsKey("reagent")) {
                    reagentType = new RelicReagent((String) ingredientConfig.get("reagent"));
                } else {
                    reagentType = new NormalReagent(Material.valueOf((String) ingredientConfig.get("material")));
                }
                int amount = ingredientConfig.containsKey("amount") ? (Integer) ingredientConfig.get("amount") : 1;
                recipe.addIngredient(new BasicIngredient(reagentType, amount));
            }

            return recipe;
        }
    }

}
