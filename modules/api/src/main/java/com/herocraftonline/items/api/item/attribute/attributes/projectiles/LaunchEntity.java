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
package com.herocraftonline.items.api.item.attribute.attributes.projectiles;

import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.Triggerable;
import org.bukkit.entity.EntityType;

public interface LaunchEntity extends Attribute<LaunchEntity>, Triggerable {

    EntityType getEntity();

    double getVelocity();

}
