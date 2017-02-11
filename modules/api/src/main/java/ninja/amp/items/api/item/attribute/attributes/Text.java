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

import java.util.List;

/**
 * An item attribute whose purpose is to add text to the item's lore.
 *
 * @author Austin Payne
 */
public interface Text extends ItemAttribute {

    /**
     * Gets the text of the attribute.
     *
     * @return the attribute's text
     */
    List<String> getText();

    /**
     * Adds text to the attribute.
     *
     * @param text the text to add
     */
    void addText(String... text);

}
