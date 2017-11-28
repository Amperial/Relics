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
import org.bukkit.entity.LivingEntity;

public class LivingEntitySource extends EntitySource {

    private final LivingEntity source;

    public LivingEntitySource(Item item, LivingEntity source) {
        super(item, source);

        this.source = source;
    }

    @Override
    public LivingEntity getSource() {
        return source;
    }

}
