/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Represents an item or attribute that can do something when clicked.
 *
 * @author Austin Payne
 */
public interface Clickable {

    /**
     * Handles a player interact event with the held item.
     *
     * @param event the player interact event
     * @param item  the held item
     */
    default void onClick(PlayerInteractEvent event, Item item) { }

}
