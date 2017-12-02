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

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.Attribute;
import org.bukkit.entity.Player;

/**
 * An attribute that defines a requirement for a player
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public interface Requirement<T extends Attribute<T>> extends Attribute<T> {

    boolean test(Player player, Item item);

}
