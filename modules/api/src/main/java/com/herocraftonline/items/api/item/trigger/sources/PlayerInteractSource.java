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
package com.herocraftonline.items.api.item.trigger.sources;

import com.herocraftonline.items.api.item.Item;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSource extends PlayerSource {

    private final PlayerInteractEvent event;

    public PlayerInteractSource(Item item, PlayerInteractEvent event) {
        super(item, event.getPlayer());

        this.event = event;
    }

    public PlayerInteractEvent getEvent() {
        return event;
    }

}
