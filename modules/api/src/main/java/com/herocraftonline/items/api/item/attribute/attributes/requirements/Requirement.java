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
package com.herocraftonline.items.api.item.attribute.attributes.requirements;

import com.herocraftonline.items.api.item.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

/**
 * An attribute that defines a requirement for a living entity to use the item.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public interface Requirement<T extends Attribute<T>> extends Attribute<T> {

    /**
     * Tests if the living entity meets the requirement of the attribute
     *
     * @param livingEntity the living entity
     * @return {@code true} if the test passes, {@code false} otherwise
     */
    default boolean test(LivingEntity livingEntity) { return true; }
}
