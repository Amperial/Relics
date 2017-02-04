/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.menu.items;

import ninja.amp.items.api.menu.ItemClickEvent;
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
