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
import com.herocraftonline.items.api.item.attribute.AttributeContainer;

import java.util.Map;

/**
 * An attribute that is also an attribute container.<br>
 * Contains methods that may not be in every attribute container.
 *
 * @author Austin Payne
 */
public interface Group extends Attribute<Group>, AttributeContainer {

    /**
     * Gets the attributes in the attribute group by name.
     *
     * @return the attribute group's attributes and their names
     */
    Map<String, ? extends Attribute> getAttributesByName();

    /**
     * Adds attributes to the attribute group.
     *
     * @param attributes the attributes to add
     */
    void addAttribute(Attribute... attributes);

    /**
     * Removes an attribute from the attribute group.
     *
     * @param attribute the attribute to remove
     */
    void removeAttribute(Attribute attribute);

}
