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
package com.herocraftonline.items.crafting.ingredients.reagents;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent.ReagentType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NormalReagent implements ReagentType {

    private final Material material;

    public NormalReagent(Material material) {
        this.material = material;
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
        return getMaterial().name();
    }

}
