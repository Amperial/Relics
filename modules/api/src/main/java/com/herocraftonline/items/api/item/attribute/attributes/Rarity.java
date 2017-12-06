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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An attribute that indicates the rarity of an item.
 *
 * @author Austin Payne
 */
public interface Rarity extends Attribute<Rarity> {

    int getRarity();

    void setRarity(int rarity);

}
