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
package com.herocraftonline.items.item.attributes.triggers.sources;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.BaseSource;
import org.bukkit.Location;

public class LocationSource extends BaseSource implements com.herocraftonline.items.api.item.attribute.attributes.triggers.source.LocationSource {

    private final Location location;

    public LocationSource(Item item, Location location) {
        super(item);

        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

}
