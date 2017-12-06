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
package com.herocraftonline.items.menu.items;

import com.herocraftonline.items.menu.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * A menu item that closes the item menu.
 *
 * @author Austin Payne
 */
public class CloseItem extends StaticMenuItem {

    public CloseItem(ItemStack icon) {
        super(ChatColor.RED + "Close", icon);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }

}
