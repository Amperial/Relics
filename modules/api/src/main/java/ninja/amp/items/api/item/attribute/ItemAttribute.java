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

public interface ItemAttribute {

    static Predicate<ItemAttribute> type(Class<?> clazz) {
        return attribute -> clazz.isAssignableFrom(attribute.getClass());
    }

    String getName();

    AttributeType getType();

    ItemLore getLore();

    void saveToNBT(NBTTagCompound compound);

}
