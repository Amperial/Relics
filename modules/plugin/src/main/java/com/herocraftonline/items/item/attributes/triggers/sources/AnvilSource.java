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
package com.herocraftonline.items.item.attributes.triggers.sources;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.item.attributes.triggers.sources.entity.PlayerSource;
import org.bukkit.entity.Player;

public class AnvilSource extends PlayerSource implements com.herocraftonline.items.api.item.attribute.attributes.trigger.source.item.AnvilSource {

    private final Item upgrade;

    public AnvilSource(Player player, Item item, Item upgrade) {
        super(item, player);

        this.upgrade = upgrade;
    }

    @Override
    public Item getUpgradeItem() {
        return upgrade;
    }

}
