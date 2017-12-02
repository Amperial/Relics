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
package com.herocraftonline.items.api.item.attribute;

import java.util.List;

/**
 * Handles adding an attribute's lore to an item.
 *
 * @author Austin Payne
 */
public interface AttributeLore {

    /**
     * An empty attribute lore.
     */
    AttributeLore NONE = (lore, prefix) -> {
    };

    /**
     * Adds the attribute lore to the given list of lore strings.
     *
     * @param lore   the lore strings
     * @param prefix the current lore string prefix
     */
    void addTo(List<String> lore, String prefix);

}
