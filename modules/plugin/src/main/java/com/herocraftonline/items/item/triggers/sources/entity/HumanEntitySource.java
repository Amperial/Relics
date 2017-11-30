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
import org.bukkit.entity.HumanEntity;

public class HumanEntitySource extends LivingEntitySource implements com.herocraftonline.items.api.item.trigger.source.entity.HumanEntitySource {

    public HumanEntitySource(Item item, HumanEntity entity) {
        super(item, entity);
    }

    @Override
    public HumanEntity getEntity() {
        return (HumanEntity) super.getEntity();
    }

}
