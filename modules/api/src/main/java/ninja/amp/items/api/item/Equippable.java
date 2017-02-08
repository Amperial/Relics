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
package ninja.amp.items.api.item;

import org.bukkit.entity.Player;

public interface Equippable {

    boolean canEquip(Player player);

    void onEquip(Player player);

    void onUnEquip(Player player);

}
