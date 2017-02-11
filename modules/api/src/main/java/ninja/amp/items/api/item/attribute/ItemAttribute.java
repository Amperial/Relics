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
package ninja.amp.items.api.item.attribute;

import ninja.amp.items.nms.nbt.NBTTagCompound;

import java.util.function.Predicate;

/**
 * An attribute on a custom item.
 *
 * @author Austin Payne
 */
public interface ItemAttribute {

    /**
     * Gets a predicate that checks if the item attribute is of the given type.
     *
     * @param clazz the attribute type
     * @return the predicate
     */
    static Predicate<ItemAttribute> type(Class<?> clazz) {
        return attribute -> clazz.isAssignableFrom(attribute.getClass());
    }

    /**
     * Gets the name of the item attribute.
     *
     * @return the item attribute's name
     */
    String getName();

    /**
     * Gets the type of the item attribute.
     *
     * @return the item attribute's type
     */
    AttributeType getType();

    /**
     * Gets the lore of the item attribute.
     *
     * @return the item attribute's lore
     */
    ItemLore getLore();

    /**
     * Saves all necessary item attribute information to an nbt tag compound.
     *
     * @param compound the tag compound
     */
    void saveToNBT(NBTTagCompound compound);

}
