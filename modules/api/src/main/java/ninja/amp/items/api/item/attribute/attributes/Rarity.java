/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.item.attribute.ItemAttribute;

/**
 * An item attribute that indicates the rarity of an item.
 *
 * @author Austin Payne
 */
public interface Rarity extends ItemAttribute {

    /**
     * Gets the rarity of the item.
     *
     * @return the item's rarity
     */
    int getRarity();

    /**
     * Sets the rarity of the item.
     *
     * @param rarity the rarity
     */
    void setRarity(int rarity);

}
