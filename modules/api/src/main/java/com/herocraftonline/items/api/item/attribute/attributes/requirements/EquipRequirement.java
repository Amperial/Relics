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
package com.herocraftonline.items.api.item.attribute.attributes.requirements;

import com.herocraftonline.items.api.item.Equippable;
import com.herocraftonline.items.api.item.attribute.Attribute;

/**
 * An attribute that defines a requirement for a player to equip an item.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public interface EquipRequirement<T extends Attribute<T>> extends Requirement<T>, Equippable {

}
