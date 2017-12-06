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
package com.herocraftonline.items.item.attributes.triggers.sources.entity;

import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.Player;

public class PlayerSource extends HumanEntitySource implements com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity.PlayerSource {

    public PlayerSource(Item item, Player player) {
        super(item, player);
    }

    @Override
    public Player getPlayer() {
        return (Player) super.getEntity();
    }

}
