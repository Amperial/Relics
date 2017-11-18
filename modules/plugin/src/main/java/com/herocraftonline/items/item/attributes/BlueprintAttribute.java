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
import com.herocraftonline.items.crafting.ShapedRecipe;
import com.herocraftonline.items.crafting.ShapelessRecipe;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlueprintAttribute extends BaseAttribute<Blueprint> implements Blueprint, Clickable {

    private final Recipe recipe;

    public BlueprintAttribute(String name, Recipe recipe) {
        super(name, DefaultAttribute.BLUEPRINT);

        this.recipe = recipe;

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
    public void onClick(PlayerInteractEvent event, Item item) {
        CraftingMenu.open(event.getPlayer(), getRecipe());
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagCompound recipe = NBTTagCompound.create();
        getRecipe().saveToNBT(recipe);
        compound.setBase("recipe", recipe);
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

            return new BlueprintAttribute(name, recipe);
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

            return new BlueprintAttribute(name, recipe);
        }
    }

}
