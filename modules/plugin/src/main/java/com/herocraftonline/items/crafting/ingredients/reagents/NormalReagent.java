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
    public boolean matches(ItemStack item) {
        return item != null && item.getType() == getMaterial();
    }

}
