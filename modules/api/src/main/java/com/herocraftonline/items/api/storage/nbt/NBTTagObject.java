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
package com.herocraftonline.items.api.storage.nbt;

public interface NBTTagObject<T> extends NBTBase {

    static <A> NBTTagObject<A> create(A data) {
        return ((NBTTagObject<?>) NBT_INSTANCES[12]).newInstance(data);
    }

    <B> NBTTagObject<B> newInstance(B item);

    T getObject();

}
