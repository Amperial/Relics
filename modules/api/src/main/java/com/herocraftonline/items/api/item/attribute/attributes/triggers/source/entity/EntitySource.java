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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.CommandSenderSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.LocationSource;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface EntitySource extends CommandSenderSource, LocationSource {

    Entity getEntity();

    default Location getLocation() {
        return getEntity().getLocation();
    }

}
