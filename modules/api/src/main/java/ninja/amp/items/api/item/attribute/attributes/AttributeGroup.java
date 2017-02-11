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

import java.util.Map;

/**
 * An attribute that is also an attribute container.<br>
 * Contains methods that may not be in every attribute container.
 *
 * @author Austin Payne
 */
public interface AttributeGroup extends ItemAttribute, AttributeContainer {

    /**
     * Gets the attributes in the attribute group by name.
     *
     * @return the attribute group's attributes and their names
     */
    Map<String, ? extends ItemAttribute> getAttributesByName();

    /**
     * Adds attributes to the attribute group.
     *
     * @param attributes the attributes to add
     */
    void addAttribute(ItemAttribute... attributes);

    /**
     * Removes an attribute from the attribute group.
     *
     * @param attribute the attribute to remove
     */
    void removeAttribute(ItemAttribute attribute);

}
