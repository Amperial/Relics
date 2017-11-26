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
package com.herocraftonline.items.crafting.ingredients;

import com.herocraftonline.items.api.item.attribute.attributes.crafting.Ingredient;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent.ReagentType;
import org.bukkit.inventory.ItemStack;

public class BasicIngredient implements Ingredient {

    private final ReagentType reagent;
    private final int amount;

    public BasicIngredient(ReagentType reagent, int amount) {
        this.reagent = reagent;
        this.amount = amount;
    }

    @Override
    public ReagentType getType() {
        return reagent;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return itemStack != null && getType().test(itemStack) && itemStack.getAmount() == getAmount();
    }

}
