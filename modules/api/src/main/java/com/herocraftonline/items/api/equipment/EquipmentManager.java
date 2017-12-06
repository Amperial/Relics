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
package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface EquipmentManager {

    void loadEquipment(Player player);

    Equipment getEquipment(Player player);

    boolean isEquipped(Player player, Item item);

    void equip(Player player, Item item, ItemStack itemStack);

    void replaceEquip(Player player, Item item, ItemStack itemStack);

    void unEquip(Player player, Item item, ItemStack itemStack);

    void unEquipAll(Player player);

}
