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
package com.herocraftonline.items.api.item;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Represents an item or attribute that has to be equipped to use.
 *
 * @author Austin Payne
 */
public interface Equippable {

    /**
     * Equips the equippable to a living entity and checks if the item should be updated.
     *
     * @param livingEntity the living entity
     * @return {@code true} if the item should be updated, else {@code false}
     */
    default boolean onEquip(LivingEntity livingEntity) { return false; }

    /**
     * UnEquips the equippable from a living entity and checks if the item should be updated.
     *
     * @param livingEntity the living entity
     * @return {@code true} if the item should be updated, else {@code false}
     */
    default boolean onUnEquip(LivingEntity livingEntity) { return false; }


}
