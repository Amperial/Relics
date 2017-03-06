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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.AttributeFactory;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;

/**
 * A basic attribute factory implementation to simplify the creation of item attribute factories.<br>
 * Holds on to an instance of the item plugin and provides it to the attribute factory.
 *
 * @param <T> the item attribute that this factory creates
 * @author Austin Payne
 */
public abstract class BasicAttributeFactory<T extends ItemAttribute> implements AttributeFactory<T> {

    private ItemPlugin plugin;

    public BasicAttributeFactory(ItemPlugin plugin) {
        this.plugin = plugin;
    }

    protected ItemPlugin getPlugin() {
        return plugin;
    }

}
