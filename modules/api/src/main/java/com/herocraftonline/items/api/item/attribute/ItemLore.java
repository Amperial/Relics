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
package com.herocraftonline.items.api.item.attribute;

import java.util.List;

/**
 * Handles building an item's lore.
 *
 * @author Austin Payne
 */
public interface ItemLore {

    /**
     * An empty item lore.
     */
    ItemLore NONE = (lore, prefix) -> {
    };

    /**
     * Adds the item lore to the given list of lore strings.
     *
     * @param lore   the lore strings
     * @param prefix the current lore string prefix
     */
    void addTo(List<String> lore, String prefix);

}
