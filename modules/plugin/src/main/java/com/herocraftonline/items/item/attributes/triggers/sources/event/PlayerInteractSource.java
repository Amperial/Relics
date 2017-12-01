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
package com.herocraftonline.items.item.attributes.triggers.sources.event;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.item.attributes.triggers.sources.entity.PlayerSource;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSource extends PlayerSource implements com.herocraftonline.items.api.item.attribute.attributes.triggers.source.event.PlayerInteractSource {

    private PlayerInteractEvent event;

    public PlayerInteractSource(Item item, PlayerInteractEvent event) {
        super(item, event.getPlayer());

        this.event = event;
    }

    @Override
    public PlayerInteractEvent getEvent() {
        return event;
    }

}
