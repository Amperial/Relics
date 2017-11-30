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
package com.herocraftonline.items.item.triggers.sources.entity;

import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.Player;

public class PlayerSource extends HumanEntitySource implements com.herocraftonline.items.api.item.trigger.source.entity.PlayerSource {

    public PlayerSource(Item item, Player player) {
        super(item, player);
    }

    @Override
    public Player getPlayer() {
        return (Player) super.getEntity();
    }

}
