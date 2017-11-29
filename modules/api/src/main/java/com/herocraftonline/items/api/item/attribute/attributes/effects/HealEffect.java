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
package com.herocraftonline.items.api.item.attribute.attributes.effects;

/**
 * A triggerable effect that heals a living entity.
 *
 * @author Austin Payne
 */
public interface HealEffect extends Effect<HealEffect> {

    /**
     * Gets the amount to heal an entity for.
     *
     * @return the heal amount
     */
    double getHeal();

    /**
     * Sets the amount to heal an entity for.
     *
     * @param heal the heal amount
     */
    void setHeal(double heal);

}
