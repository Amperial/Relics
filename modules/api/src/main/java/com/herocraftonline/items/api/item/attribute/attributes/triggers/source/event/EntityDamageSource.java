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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.source.event;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity.EntitySource;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public interface EntityDamageSource extends EntitySource {

    EntityDamageEvent getEvent();

    default Entity getEntity() {
        return getEvent().getEntity();
    }

}
