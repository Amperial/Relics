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

import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.CommandSenderSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.LocationSource;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface EntitySource extends CommandSenderSource, LocationSource {

    Entity getEntity();

    default Location getLocation() {
        return getEntity().getLocation();
    }

}
