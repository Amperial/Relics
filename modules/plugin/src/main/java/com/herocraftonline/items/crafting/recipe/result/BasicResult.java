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

import com.herocraftonline.items.api.item.attribute.attributes.crafting.Result;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.inventory.ItemStack;

public class BasicResult implements Result {

    private final ItemStack result;

    public BasicResult(ItemStack result) {
        this.result = result;
    }

    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public String getDisplayIcon() {
        return getItem().getType().name();
    }

    @Override
    public String toString() {
        return ItemUtil.getName(getItem()) + " x" + result.getAmount();
    }

}
