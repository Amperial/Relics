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

import org.bukkit.entity.Player;

/**
 * Used to wrap the information of a player using an item menu.<br>
 * For example, store extra information about a player's character, hero, etc.
 *
 * @author Austin Payne
 */
public class Owner {

    private final Player player;

    public Owner(Player player) {
        this.player = player;
    }

    /**
     * Gets the player of the owner.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

}
