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
package ninja.amp.items.api.menu;

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
