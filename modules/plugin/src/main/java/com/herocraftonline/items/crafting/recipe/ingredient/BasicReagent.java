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
package com.herocraftonline.items.crafting.recipe.ingredient;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.crafting.ReagentType;
import com.herocraftonline.items.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BasicReagent implements ReagentType {

    private final Material material;
    private final String name;

    public BasicReagent(Material material) {
        this.material = material;
        this.name = ItemUtil.getName(material);
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() == getMaterial() && !Relics.instance().getItemManager().isItem(itemStack);
    }

    @Override
    public String getDisplayIcon() {
        return getMaterial().name().toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }

}
