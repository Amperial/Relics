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
package com.herocraftonline.items.api.item.attribute.attributes;

/**
 * An attribute that expands upon generic minecraft damage stats, adding variation to an item's damage.
 *
 * @author Austin Payne
 */
public interface Damage extends Minecraft {

    /**
     * Gets the attribute's base damage.
     *
     * @return the base damage
     */
    double getDamage();

    /**
     * Sets the attribute's base damage.
     *
     * @param damage the base damage
     */
    void setDamage(double damage);

    /**
     * Gets the attribute's damage variation.
     *
     * @return the damage variation
     */
    double getVariation();

    /**
     * Sets the attribute's damage variation.
     *
     * @param variation the damage variation
     */
    void setVariation(double variation);

}
