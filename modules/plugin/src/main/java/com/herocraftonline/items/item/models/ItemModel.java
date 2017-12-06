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
package com.herocraftonline.items.item.models;

import com.herocraftonline.items.api.item.model.Model;
import org.bukkit.inventory.ItemStack;

public class ItemModel implements Model {

    private final String name;
    private final String path;
    private short durability = 0;

    public ItemModel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    public short getDurability() {
        return durability;
    }

    public void setDurability(short durability) {
        this.durability = durability;
    }

    @Override
    public void apply(ItemStack item) {
        item.setDurability(durability);
    }

}
