/*
 * This file is part of AmpItems NMS 1_10_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems NMS 1_10_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTTagObject;

public class NBTTagObject_v1_10_R1<T> extends NBTTagByte_v1_10_R1 implements NBTTagObject<T> {

    private T object;

    public NBTTagObject_v1_10_R1(T object) {
        super((byte) 0);

        this.object = object;
    }

    @Override
    public <B> NBTTagObject<B> newInstance(B item) {
        return new NBTTagObject_v1_10_R1<>(item);
    }

    @Override
    public T getObject() {
        return object;
    }

    @Override
    public NBTTagObject_v1_10_R1<T> c() {
        return new NBTTagObject_v1_10_R1<>(object);
    }

}