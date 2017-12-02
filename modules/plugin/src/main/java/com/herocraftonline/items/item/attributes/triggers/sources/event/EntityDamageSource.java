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
package com.herocraftonline.items.item.attributes.triggers.sources.event;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.item.attributes.triggers.sources.entity.EntitySource;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageSource extends EntitySource implements com.herocraftonline.items.api.item.attribute.attributes.triggers.source.event.EntityDamageSource {

    private final EntityDamageEvent event;

    public EntityDamageSource(Item item, EntityDamageEvent event) {
        super(item, event.getEntity());

        this.event = event;
    }

    @Override
    public EntityDamageEvent getEvent() {
        return event;
    }

}
