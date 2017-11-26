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
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Clickable;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Blueprint;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Recipe;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.crafting.CraftingMenu;
import com.herocraftonline.items.crafting.RecipeRenderer;
import com.herocraftonline.items.crafting.ShapedRecipe;
import com.herocraftonline.items.crafting.ShapelessRecipe;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.Optional;

public class BlueprintAttribute extends BaseAttribute<Blueprint> implements Blueprint, Clickable {

    private final Recipe recipe;
    private short mapId;

    public BlueprintAttribute(String name, Recipe recipe, short mapId) {
        super(name, DefaultAttribute.BLUEPRINT);

        this.recipe = recipe;
        this.mapId = mapId;

        setLore(((lore, prefix) -> {
            String displayName = recipe.getResult().getItemMeta().getDisplayName();
            if (displayName == null) {
                displayName = recipe.getResult().getType().toString();
            }
            lore.add(prefix + "Blueprint: " + displayName);
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
    public void onClick(PlayerInteractEvent event, Item item) {
        CraftingMenu.open(event.getPlayer(), getRecipe());
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
        Recipe.RecipeFactory<ShapedRecipe> shapedRecipeFactory = new ShapedRecipe.Factory();
        Recipe.RecipeFactory<ShapelessRecipe> shapelessRecipeFactory = new ShapelessRecipe.Factory();

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Blueprint loadFromConfig(String name, ConfigurationSection config) {
            Recipe recipe;
            ConfigurationSection recipeConfig = config.getConfigurationSection("recipe");
            if (recipeConfig.getString("type").equals("shaped")) {
                recipe = shapedRecipeFactory.loadFromConfig(recipeConfig);
            } else {
                recipe = shapelessRecipeFactory.loadFromConfig(recipeConfig);
            }

            return new BlueprintAttribute(name, recipe, (short) -1);
        }

        @Override
        public Blueprint loadFromNBT(String name, NBTTagCompound compound) {
            Recipe recipe;
            NBTTagCompound recipeCompound = compound.getCompound("recipe");
            if (recipeCompound.getString("type").equals("shaped")) {
                recipe = shapedRecipeFactory.loadFromNBT(recipeCompound);
            } else {
                recipe = shapelessRecipeFactory.loadFromNBT(recipeCompound);
            }
            short mapId = (short) compound.getInt("mapId");

            return new BlueprintAttribute(name, recipe, mapId);
        }
    }

}
