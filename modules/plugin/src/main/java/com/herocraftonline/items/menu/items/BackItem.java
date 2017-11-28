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
package com.herocraftonline.items.menu.items;

import com.herocraftonline.items.menu.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * A menu item that opens the item menu's parent menu if one exists.
 *
 * @author Austin Payne
 */
public class BackItem extends StaticMenuItem {

    public BackItem(ItemStack icon) {
        super(ChatColor.RED + "Back", icon);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillGoBack(true);
    }

}
