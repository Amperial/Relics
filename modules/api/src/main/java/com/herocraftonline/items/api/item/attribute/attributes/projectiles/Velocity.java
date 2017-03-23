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

import com.herocraftonline.items.api.item.attribute.ValueAttribute;

/**
 * An attribute that allows setting or multiplying the velocity of a projectile shot from a bow.
 *
 * @author Austin Payne
 */
public interface Velocity extends ValueAttribute<Velocity, Double> {

    /**
     * Checks if the projectile velocity should be multiplied or set.
     *
     * @return {@code true} if the velocity should be multiplied or {@code false} if it should be set
     */
    boolean isMultiplier();

    /**
     * Sets if the projectile velocity should be multiplied or set.
     *
     * @param multiply if the velocity should be multiplied
     */
    void setMultiplier(boolean multiply);

}
