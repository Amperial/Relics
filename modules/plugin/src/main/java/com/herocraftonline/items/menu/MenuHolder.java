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
package com.herocraftonline.items.menu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Allows you to set the item menu that created the inventory as the inventory's holder.
 *
 * @author Austin Payne
 */
public class MenuHolder implements InventoryHolder {

    private ItemMenu menu;
    private Inventory inventory;

    private MenuHolder(ItemMenu menu) {
        this.menu = menu;
    }

    /**
     * Gets the item menu holding the inventory.
     *
     * @return The item menu holding the inventory
     */
    public ItemMenu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Creates an inventory owned by a menu holder.
     *
     * @param owner the item menu to own the inventory
     * @param size  the size of the inventory
     * @param name  the name of the inventory
     * @return the inventory created
     */
    public static MenuHolder createInventory(ItemMenu owner, int size, String name) {
        MenuHolder holder = new MenuHolder(owner);
        holder.inventory = Bukkit.createInventory(holder, size, name);
        return holder;
    }

}
