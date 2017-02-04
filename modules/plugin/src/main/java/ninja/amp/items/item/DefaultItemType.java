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

public enum DefaultItemType implements ItemType {
    BLOCK("block"),
    CONSUMABLE("consumable"),
    ENTITY("entity"),
    INTERACTABLE("interactable"),
    OTHER("other"),
    PROJECTILE("projectile"),
    SHIELD("shield"),
    SHULKER_BOX("shulker-box");

    private final String name;

    DefaultItemType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
