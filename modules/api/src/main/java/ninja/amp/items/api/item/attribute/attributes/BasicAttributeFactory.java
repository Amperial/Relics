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
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.AttributeFactory;
import ninja.amp.items.api.item.attribute.ItemAttribute;

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
