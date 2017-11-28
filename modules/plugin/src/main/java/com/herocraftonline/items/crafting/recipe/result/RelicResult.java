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
package com.herocraftonline.items.crafting.recipe.result;

import com.herocraftonline.items.api.crafting.Result;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent;
import org.bukkit.inventory.ItemStack;

public class RelicResult implements Result {

    private final Item result;

    public RelicResult(Item result) {
        this.result = result;
    }

    @Override
    public ItemStack getItem() {
        return result.getItem();
    }

    @Override
    public String getDisplayIcon() {
        return result.getAttribute(Reagent.class).map(reagent -> reagent.getReagentType().getDisplayIcon()).orElse(result.getMaterial().name());
    }

    @Override
    public String toString() {
        return result.getName();
    }

}
