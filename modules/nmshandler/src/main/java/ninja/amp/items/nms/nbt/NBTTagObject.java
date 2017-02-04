/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems NMS API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.nms.nbt;

public interface NBTTagObject<T> extends NBTBase {

    static <A> NBTTagObject<A> create(A data) {
        return ((NBTTagObject<?>) NBTBase.NBT_INSTANCES[12]).newInstance(data);
    }

    <B> NBTTagObject<B> newInstance(B item);

    T getObject();

}
