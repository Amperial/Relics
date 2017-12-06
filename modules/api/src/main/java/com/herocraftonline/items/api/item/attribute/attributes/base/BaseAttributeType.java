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
import com.herocraftonline.items.api.item.attribute.AttributeType;

import java.util.function.Function;

/**
 * A base attribute type implementation to simplify the creation of attributes.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public class BaseAttributeType<T extends Attribute<T>> implements AttributeType<T> {

    private final String name;
    private final String fileName;
    private int lorePosition;

    private final Function<ItemPlugin, AttributeFactory<T>> factoryLoader;
    private AttributeFactory<T> factory;

    public BaseAttributeType(String name, int lorePosition, Function<ItemPlugin, AttributeFactory<T>> factoryLoader) {
        this.name = name;
        this.fileName = "attributes/" + name + ".yml";
        this.lorePosition = lorePosition;
        this.factoryLoader = factoryLoader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getLorePosition() {
        return lorePosition;
    }

    @Override
    public void setLorePosition(int position) {
        this.lorePosition = position;
    }

    @Override
    public boolean test(Attribute attribute) {
        return equals(attribute.getType());
    }

    @Override
    public AttributeFactory<T> getFactory() {
        return factory;
    }

    /**
     * Loads the attribute factory for attributes of this attribute type
     *
     * @param plugin the item plugin instance
     */
    public void loadFactory(ItemPlugin plugin) {
        factory = factoryLoader.apply(plugin);
    }

}
