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
package com.herocraftonline.items.api.item.attribute.attributes.crafting;

import com.herocraftonline.items.api.crafting.ReagentType;
import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An attribute that enables an item to be used as a relics crafting reagent.
 *
 * @author Austin Payne
 */
public interface Reagent extends Attribute<Reagent> {

    /**
     * Gets the reagent's type.
     *
     * @return the type of reagent
     */
    ReagentType getReagentType();

}
