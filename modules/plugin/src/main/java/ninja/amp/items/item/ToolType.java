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

public enum ToolType implements ItemType {
    AXE("tool-axe"),
    HOE("tool-hoe"),
    PICKAXE("tool-pickaxe"),
    SHEARS("tool-shears"),
    SHOVEL("tool-shovel");

    private final String name;

    ToolType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
