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
package ninja.amp.items.api.equipment;

import ninja.amp.items.api.item.Item;
import org.bukkit.entity.Player;

public interface EquipmentManager {

    void loadEquipment(Player player);

    Equipment getEquipment(Player player);

    boolean isEquipped(Player player, Item item);

    boolean canEquip(Player player, Item item);

    void equip(Player player, Item item);

    void replaceEquip(Player player, Item item);

    void unEquip(Player player, Item item);

    void unEquipAll(Player player);

}
