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
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.crafting.Recipe;
import com.herocraftonline.items.api.crafting.Recipe.RecipeFactory;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Blueprint;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity.PlayerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.crafting.CraftingMenu;
import com.herocraftonline.items.crafting.recipe.RecipeRenderer;
import com.herocraftonline.items.crafting.recipe.ShapedRecipe;
import com.herocraftonline.items.crafting.recipe.ShapelessRecipe;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import java.util.Optional;

public class BlueprintAttribute extends BaseAttribute<Blueprint> implements Blueprint {

    private final Recipe recipe;
    private short mapId;

    public BlueprintAttribute(Item item, String name, String ingredientsText, String resultText, Recipe recipe, short mapId) {
        super(item, name, DefaultAttributes.BLUEPRINT);

        this.recipe = recipe;
        this.mapId = mapId;

        setLore(((lore, prefix) -> {
            lore.add(prefix + ingredientsText);
            recipe.getIngredientList().forEach(ingredient -> lore.add(prefix + ingredient));
            lore.add(prefix + resultText);
            lore.add(prefix + recipe.getResult());
        }));
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public void apply(ItemStack item) {
        // Only apply custom renderers if item is a map
        if (item != null && item.getType() == Material.MAP) {
            // Get map view
            MapView view = Bukkit.getMap(mapId);
            // Create map view if it doesn't already exist and update map id
            if (view == null) {
                view = Bukkit.createMap(Bukkit.getWorlds().get(0));
                mapId = view.getId();
            }
            // Set map renderers if not already set
            if (view.getRenderers().stream().noneMatch(r -> r instanceof RecipeRenderer)) {
                view.getRenderers().forEach(view::removeRenderer);
                getRecipe().getMapRenderer().ifPresent(view::addRenderer);
            }
            // Set item durability to map id
            if (item.getDurability() != mapId) {
                item.setDurability(mapId);
            }
        }
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof PlayerSource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<PlayerSource> playerSource = source.ofType(PlayerSource.class);
        if (playerSource.isPresent()) {
            Player player = playerSource.get().getPlayer();
            ItemStack blueprint = source.getItem().getItem();
            CraftingMenu.open(Relics.instance(), player, getRecipe(), blueprint);
            return TriggerResult.TRIGGERED;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagCompound recipe = NBTTagCompound.create();
        getRecipe().saveToNBT(recipe);
        compound.setBase("recipe", recipe);
        compound.setInt("mapId", mapId);
    }

    public static class Factory extends BaseAttributeFactory<Blueprint> {
        private final RecipeFactory<ShapedRecipe> shapedRecipeFactory;
        private final RecipeFactory<ShapelessRecipe> shapelessRecipeFactory;
        private final String ingredientsText;
        private final String resultText;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            shapedRecipeFactory = new ShapedRecipe.Factory(plugin);
            shapelessRecipeFactory = new ShapelessRecipe.Factory(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.BLUEPRINT);
            ingredientsText = ChatColor.translateAlternateColorCodes('&', config.getString("ingredients", "&aCrafting Recipe:"));
            resultText = ChatColor.translateAlternateColorCodes('&', config.getString("result", "&aCrafting Result:"));
        }

        @Override
        public Blueprint loadFromConfig(Item item, String name, ConfigurationSection config) {
            Recipe recipe;
            ConfigurationSection recipeConfig = config.getConfigurationSection("recipe");
            if (recipeConfig.getString("type").equals("shaped")) {
                recipe = shapedRecipeFactory.loadFromConfig(recipeConfig);
            } else {
                recipe = shapelessRecipeFactory.loadFromConfig(recipeConfig);
            }

            return new BlueprintAttribute(item, name, ingredientsText, resultText, recipe, (short) -1);
        }

        @Override
        public Blueprint loadFromNBT(Item item, String name, NBTTagCompound compound) {
            Recipe recipe;
            NBTTagCompound recipeCompound = compound.getCompound("recipe");
            if (recipeCompound.getString("type").equals("shaped")) {
                recipe = shapedRecipeFactory.loadFromNBT(recipeCompound);
            } else {
                recipe = shapelessRecipeFactory.loadFromNBT(recipeCompound);
            }
            short mapId = (short) compound.getInt("mapId");

            return new BlueprintAttribute(item, name, ingredientsText, resultText, recipe, mapId);
        }
    }

}
