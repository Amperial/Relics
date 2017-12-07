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
package com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface LivingEntitySource extends EntitySource {

    LivingEntity getEntity();

    default Location getLocation() {
        return getEntity().getEyeLocation();
    }

}
