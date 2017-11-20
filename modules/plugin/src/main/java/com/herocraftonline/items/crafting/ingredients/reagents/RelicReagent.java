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
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent.ReagentType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Optional;

public class RelicReagent implements ReagentType {

    private final String name;

    public RelicReagent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        Optional<Item> item = Relics.instance().getItemManager().getItem(itemStack);
        return item.isPresent() && item.get().hasAttributeDeep(Attribute.predicate(Reagent.class)
                .and(attribute -> ((Reagent) attribute).getReagentType().equals(this)));
    }

    @Override
    public String getDisplayIcon() {
        return name.toUpperCase().replace(' ', '_');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelicReagent)) return false;
        RelicReagent that = (RelicReagent) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}