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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A menu item whose icon is not dynamically modified.<br>
 * Should be used when an item will always appear the same to every player.
 *
 * @author Austin Payne
 */
public class StaticMenuItem extends MenuItem {

    public StaticMenuItem(String displayName, ItemStack icon, String... lore) {
        super(displayName, icon, lore);

        setNameAndLore(getIcon(), getDisplayName(), getLore());
    }

    @Override
    public ItemStack getFinalIcon(Player player) {
        return getIcon();
    }

}
