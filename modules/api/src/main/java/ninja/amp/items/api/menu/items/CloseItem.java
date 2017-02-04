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
 * A menu item that closes the item menu.
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
