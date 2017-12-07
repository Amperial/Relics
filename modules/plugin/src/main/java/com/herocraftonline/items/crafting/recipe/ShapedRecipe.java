/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.crafting.recipe;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.crafting.Ingredient;
import com.herocraftonline.items.api.crafting.ReagentType;
import com.herocraftonline.items.api.crafting.Recipe;
import com.herocraftonline.items.api.crafting.Result;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import com.herocraftonline.items.api.util.InventoryUtil.Slot;
import com.herocraftonline.items.crafting.recipe.ingredient.BasicIngredient;
import com.herocraftonline.items.crafting.recipe.ingredient.BasicReagent;
import com.herocraftonline.items.crafting.recipe.ingredient.RelicReagent;
import com.herocraftonline.items.crafting.recipe.result.BasicResult;
import com.herocraftonline.items.crafting.recipe.result.RelicResult;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShapedRecipe implements Recipe {

    private static final Dimensions.ExpandMode MODE = Dimensions.ExpandMode.SQUARE;
    private static final Dimensions MAX_DIMENSIONS = new Dimensions(5, 5);

    private final Result result;
    private final Dimensions dimensions;
    private final Map<Position, Ingredient> ingredients = new HashMap<>();
    private final RecipeRenderer<ShapedRecipe> renderer;

    public ShapedRecipe(Result result, Dimensions dimensions) {
        this.result = result;
        this.dimensions = dimensions;
        this.renderer = new ShapedRecipeRenderer(this);
    }

    public ShapedRecipe(Result result) {
        this(result, new Dimensions(1, 1));
    }

    @Override
    public Result getResult() {
        return result;
    }

    @Override
    public Dimensions getDimensions() {
        return dimensions;
    }

    @Override
    public boolean test(Map<Position, ItemStack> items) {
        // Only and all of the positions specified should exist
        if (!ingredients.keySet().equals(items.keySet())) {
            return false;
        }

        // All of the ingredients should match
        return items.entrySet().stream().allMatch(item -> ingredients.get(item.getKey()).test(item.getValue()));
    }

    @Override
    public Optional<MapRenderer> getMapRenderer() {
        return Optional.of(renderer);
    }

    @Override
    public List<String> getIngredientList() {
        List<String> craftingArea = new ArrayList<>();
        List<String> ingredientList = new ArrayList<>();
        for (int y = 0; y < dimensions.getHeight(); y++) {
            String row = "";
            for (int x = 0; x < dimensions.getWidth(); x++) {
                Optional<Ingredient> ingredient = Optional.ofNullable(ingredients.get(new Position(x, y)));
                ingredient.ifPresent(i -> ingredientList.add((ingredientList.size() + 1) + ": " + i));
                row += "[" + ingredient.map(i -> "" + ingredientList.size()).orElse("-") + "]";
            }
            craftingArea.add(row);
        }
        craftingArea.addAll(ingredientList);
        return craftingArea;
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
        compound.setString("result", ItemUtil.serialize(getResult().getItem()));

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
            ReagentType reagentType = ingredient.getValue().getType();
            if (reagentType instanceof RelicReagent) {
                ingredientCompound.setString("reagent", ((RelicReagent) reagentType).getName());
            } else {
                ingredientCompound.setString("material", ((BasicReagent) reagentType).getMaterial().name());
            }
            ingredientCompound.setInt("amount", ingredient.getValue().getAmount());
            ingredientList.addBase(ingredientCompound);
        }
        compound.setBase("ingredients", ingredientList);
    }

    public static class Factory implements RecipeFactory<ShapedRecipe> {
        private final ItemPlugin plugin;

        public Factory(ItemPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public ShapedRecipe loadFromNBT(NBTTagCompound compound) {
            // Load result
            ItemStack resultItem = ItemUtil.deserialize(compound.getString("result")).orElse(new ItemStack(Material.AIR));
            Optional<Result> relicResult = plugin.getItemManager().getItem(resultItem).map(RelicResult::new);
            Result result = relicResult.orElse(new BasicResult(resultItem));

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
                ReagentType reagentType;
                if (ingredientCompound.hasKey("reagent")) {
                    reagentType = new RelicReagent(ingredientCompound.getString("reagent"));
                } else {
                    reagentType = new BasicReagent(Material.valueOf(ingredientCompound.getString("material")));
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
            Result result;
            ConfigurationSection resultConfig = config.getConfigurationSection("result");
            if (resultConfig.isString("item-type")) {
                result = new RelicResult(Relics.instance().getItemManager().getItem(resultConfig));
            } else {
                String material = resultConfig.getString("material");
                int amount = resultConfig.getInt("amount", 1);
                result = new BasicResult(new ItemStack(Material.valueOf(material), amount));
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
                ReagentType reagentType;
                if (ingredientConfig.containsKey("reagent")) {
                    reagentType = new RelicReagent((String) ingredientConfig.get("reagent"));
                } else {
                    reagentType = new BasicReagent(Material.valueOf((String) ingredientConfig.get("material")));
                }
                int amount = ingredientConfig.containsKey("amount") ? (Integer) ingredientConfig.get("amount") : 1;
                Ingredient ingredient = new BasicIngredient(reagentType, amount);
                recipe.setIngredient(new Slot(slot).getPosition(recipe.getDimensions()), ingredient);
            }

            return recipe;
        }
    }

    public static class ShapedRecipeRenderer extends RecipeRenderer<ShapedRecipe> {
        public ShapedRecipeRenderer(ShapedRecipe recipe) {
            super(recipe);
        }

        @Override
        public void render(MapView view, MapCanvas canvas, Player player) {
            ShapedRecipe recipe = getRecipe();
            Dimensions dimensions = recipe.getDimensions();
            int width = dimensions.getWidth() * 18 + 2;
            int height = dimensions.getHeight() * 18 + 2;
            if (width > 128 || height > 128) {
                return;
            }

            boolean drawResult = width < 99;
            boolean drawArrow = width < 89;
            int resultWidth = drawResult ? drawArrow ? 40 : 22 : 0;

            int x = (128 - width - resultWidth) / 2;
            int y = (128 - height) / 2;
            for (int x2 = 0; x2 < dimensions.getWidth(); x2++) {
                for (int y2 = 0; y2 < dimensions.getHeight(); y2++) {
                    drawIngredient(canvas, x + (x2 * 18), y + (y2 * 18), recipe.getIngredient(new Position(x2, y2)));
                }
            }

            if (drawResult) {
                drawResult(canvas, x + width + 2, 54, recipe.getResult(), drawArrow);
            }
        }
    }

}
