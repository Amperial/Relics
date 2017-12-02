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
package com.herocraftonline.items.api.item.attribute;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;

import java.util.function.Predicate;

/**
 * An attribute on a custom item.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public interface Attribute<T extends Attribute<T>> {

    /**
     * Gets a predicate that checks if the item attribute is of the given type.
     *
     * @param type the attribute type
     * @return the predicate
     */
    static Predicate<Attribute> predicate(Class<?> type) {
        return attribute -> type.isAssignableFrom(attribute.getClass());
    }

    /**
     * Gets the name of the attribute.
     *
     * @return the attribute's name
     */
    String getName();

    /**
     * Gets the type of the attribute.
     *
     * @return the attribute's type
     */
    AttributeType<T> getType();

    /**
     * Gets the lore of the attribute.
     *
     * @return the attribute's lore
     */
    AttributeLore getLore();

    /**
     * Saves all necessary attribute information to an nbt tag compound.
     *
     * @param compound the tag compound
     */
    void saveToNBT(NBTTagCompound compound);

}
