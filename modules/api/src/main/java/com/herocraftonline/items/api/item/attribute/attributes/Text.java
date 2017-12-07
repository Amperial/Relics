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

import java.util.List;

/**
 * An attribute whose purpose is to add text to the item's lore.
 *
 * @author Austin Payne
 */
public interface Text extends Attribute<Text> {

    /**
     * Gets the text of the attribute.
     *
     * @return the attribute's text
     */
    List<String> getText();

}
