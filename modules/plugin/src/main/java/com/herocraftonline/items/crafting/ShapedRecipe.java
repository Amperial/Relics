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
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Dimensions getDimensions() {
        return dimensions;
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

    @Override
    public Optional<MapRenderer> getMapRenderer() {
        return Optional.empty();
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

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        // Set type
        compound.setString("type", "shaped");

        // Save result
        compound.setString("result", ItemUtil.serialize(getResult()));

        // Save dimensions
        NBTTagCompound dimensions = NBTTagCompound.create();
        dimensions.setInt("width", getDimensions().getWidth());
        dimensions.setInt("height", getDimensions().getHeight());
        compound.setBase("dimensions", dimensions);

        // Save ingredients
        NBTTagList ingredientList = NBTTagList.create();
        for (Map.Entry<Position, Ingredient> ingredient : ingredients.entrySet()) {
            NBTTagCompound ingredientCompound = NBTTagCompound.create();
            ingredientCompound.setInt("slot", ingredient.getKey().getSlot(getDimensions()).getIndex());
            Reagent.ReagentType reagentType = ingredient.getValue().getType();
            if (reagentType instanceof RelicReagent) {
                ingredientCompound.setString("reagent", ((RelicReagent) reagentType).getName());
            } else {
                ingredientCompound.setString("material", ((NormalReagent) reagentType).getMaterial().name());
            }
            ingredientCompound.setInt("amount", ingredient.getValue().getAmount());
            ingredientList.addBase(ingredientCompound);
        }
        compound.setBase("ingredients", ingredientList);
    }

    public static class Factory implements RecipeFactory<ShapedRecipe> {

        @Override
        public ShapedRecipe loadFromNBT(NBTTagCompound compound) {
            // Load result
            ItemStack result = ItemUtil.deserialize(compound.getString("result"));

            // Load dimensions
            NBTTagCompound dimensionsCompound = compound.getCompound("dimensions");
            Dimensions dimensions = new Dimensions(dimensionsCompound.getInt("width"), dimensionsCompound.getInt("height"));

            // Create recipe
            ShapedRecipe recipe = new ShapedRecipe(result, dimensions);

            // Load ingredients
            NBTTagList ingredientList = compound.getList("ingredients", 10);
            for (int i = 0; i < ingredientList.size(); i++) {
                NBTTagCompound ingredientCompound = ingredientList.getCompound(i);
                int slot = ingredientCompound.getInt("slot");
                Reagent.ReagentType reagentType;
                if (ingredientCompound.hasKey("reagent")) {
                    reagentType = new RelicReagent(ingredientCompound.getString("reagent"));
                } else {
                    reagentType = new NormalReagent(Material.valueOf(ingredientCompound.getString("material")));
                }
                Ingredient ingredient = new BasicIngredient(reagentType, ingredientCompound.getInt("amount"));
                recipe.setIngredient(new Slot(slot).getPosition(dimensions), ingredient);
            }

            return recipe;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ShapedRecipe loadFromConfig(ConfigurationSection config) {
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
            ShapedRecipe recipe;
            if (config.isConfigurationSection("dimensions")) {
                ConfigurationSection dimensions = config.getConfigurationSection("dimensions");
                recipe = new ShapedRecipe(result, new Dimensions(dimensions.getInt("width"), dimensions.getInt("height")));
            } else {
                recipe = new ShapedRecipe(result);
            }

            // Load ingredients
            List<Map<?, ?>> ingredients = config.getMapList("ingredients");
            for (Map<?, ?> ingredientConfig : ingredients) {
                int slot = (Integer) ingredientConfig.get("slot");
                Reagent.ReagentType reagentType;
                if (ingredientConfig.containsKey("reagent")) {
                    reagentType = new RelicReagent((String) ingredientConfig.get("reagent"));
                } else {
                    reagentType = new NormalReagent(Material.valueOf((String) ingredientConfig.get("material")));
                }
                int amount = ingredientConfig.containsKey("amount") ? (Integer) ingredientConfig.get("amount") : 1;
                Ingredient ingredient = new BasicIngredient(reagentType, amount);
                recipe.setIngredient(new Slot(slot).getPosition(recipe.getDimensions()), ingredient);
            }

            return recipe;
        }
    }

    public static class ShapedRecipeRenderer extends MapRenderer {

        @Override
        public void render(MapView mapView, MapCanvas mapCanvas, Player player) {

        }
    }

}
