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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.source;

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
