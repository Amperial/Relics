/*
 * This file is part of Relics NMS 1_12_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_12_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions.nbt;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;

import java.lang.reflect.Field;

public class NBTTagLongArray_v1_12_R1 extends NBTTagLongArray implements NBTBase {

    private static final Field DATA_FIELD;

    static {
        try {
            DATA_FIELD = NBTTagLongArray.class.getDeclaredField("b");
            DATA_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public NBTTagLongArray_v1_12_R1(NBTTagLongArray along) {
        super(getLongArray(along));
    }

    public NBTTagLongArray_v1_12_R1(long[] along) {
        super(along);
    }

    public long[] getLongArray() {
        return getLongArray(this);
    }

    @Override
    public NBTTagLongArray_v1_12_R1 c() {
        return new NBTTagLongArray_v1_12_R1(getLongArray());
    }

    @Override
    public NBTTagLongArray_v1_12_R1 clone() {
        return this.c();
    }

    private static long[] getLongArray(NBTTagLongArray along) {
        try {
            return (long[]) DATA_FIELD.get(along);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
