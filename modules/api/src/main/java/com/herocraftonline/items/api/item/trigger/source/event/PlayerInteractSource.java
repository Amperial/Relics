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
package com.herocraftonline.items.api.item.trigger.source.event;

import com.herocraftonline.items.api.item.trigger.source.entity.PlayerSource;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface PlayerInteractSource extends PlayerSource {

    PlayerInteractEvent getEvent();

    default Player getPlayer() {
        return getEvent().getPlayer();
    }

}
