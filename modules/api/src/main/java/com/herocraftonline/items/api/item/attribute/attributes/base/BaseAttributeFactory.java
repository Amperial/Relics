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
package com.herocraftonline.items.api.item.attribute.attributes.base;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeFactory;

/**
 * A basic attribute factory implementation to simplify the creation of attribute factories.<br>
 * Holds on to an instance of the item plugin and provides it to the attribute factory.
 *
 * @param <T> the type of attribute created by the factory
 * @author Austin Payne
 */
public abstract class BaseAttributeFactory<T extends Attribute<T>> implements AttributeFactory<T> {

    private final ItemPlugin plugin;

    public BaseAttributeFactory(ItemPlugin plugin) {
        this.plugin = plugin;
    }

    protected ItemPlugin getPlugin() {
        return plugin;
    }

}
