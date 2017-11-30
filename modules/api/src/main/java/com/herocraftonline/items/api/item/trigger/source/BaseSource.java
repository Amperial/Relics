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
package com.herocraftonline.items.api.item.trigger.source;

import com.herocraftonline.items.api.item.Item;

public class BaseSource implements TriggerSource {

    private final Item item;

    public BaseSource(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

}
