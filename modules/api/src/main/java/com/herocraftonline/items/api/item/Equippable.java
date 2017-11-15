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

import org.bukkit.entity.Player;

/**
 * Represents an item or attribute that has to be equipped to use.
 *
 * @author Austin Payne
 */
public interface Equippable {

    /**
     * Checks if the equippable can be equipped to a player.
     *
     * @param player the player
     * @return {@code true} if the equippable can be equipped, else {@code false}
     */
    default boolean canEquip(Player player) { return true; }

    /**
     * Equips the equippable to a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    default boolean onEquip(Player player) { return false; }

    /**
     * UnEquips the equippable from a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    default boolean onUnEquip(Player player) { return false; }


}
