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
    boolean canEquip(Player player);

    /**
     * Equips the equippable to a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    boolean onEquip(Player player);

    /**
     * UnEquips the equippable from a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    boolean onUnEquip(Player player);

}
