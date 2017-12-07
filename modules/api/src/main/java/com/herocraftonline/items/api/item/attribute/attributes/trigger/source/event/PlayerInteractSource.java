/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.trigger.source.event;

import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.PlayerSource;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface PlayerInteractSource extends PlayerSource {

    PlayerInteractEvent getEvent();

    default Player getPlayer() {
        return getEvent().getPlayer();
    }

}
