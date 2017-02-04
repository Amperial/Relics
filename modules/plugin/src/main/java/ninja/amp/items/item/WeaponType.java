/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item;

import ninja.amp.items.api.item.ItemType;

public enum WeaponType implements ItemType {
    AXE("weapon-axe"),
    BOW("weapon-bow"),
    HOE("weapon-hoe"),
    PICKAXE("weapon-pickaxe"),
    SHOVEL("weapon-shovel"),
    SWORD("weapon-sword");

    private final String name;

    WeaponType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
