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
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntitySource extends EntityDamageSource implements com.herocraftonline.items.api.item.attribute.attributes.trigger.source.event.EntityDamageByEntitySource {

    private final LivingEntity attacker;

    public EntityDamageByEntitySource(Item item, EntityDamageByEntityEvent event, LivingEntity attacker) {
        super(item, event);

        this.attacker = attacker;
    }

    public EntityDamageByEntityEvent getEvent() {
        return (EntityDamageByEntityEvent) super.getEvent();
    }

    @Override
    public LivingEntity getAttacker() {
        return attacker;
    }

}
