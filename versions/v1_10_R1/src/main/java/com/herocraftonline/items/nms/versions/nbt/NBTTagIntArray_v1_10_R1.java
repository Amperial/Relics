/*
 * This file is part of Relics NMS 1_10_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_10_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions.nbt;

import com.herocraftonline.items.api.storage.nbt.NBTTagIntArray;

public class NBTTagIntArray_v1_10_R1 extends net.minecraft.server.v1_10_R1.NBTTagIntArray implements NBTTagIntArray {

    public NBTTagIntArray_v1_10_R1(net.minecraft.server.v1_10_R1.NBTTagIntArray aint) {
        super(aint.d());
    }

    public NBTTagIntArray_v1_10_R1(int[] aint) {
        super(aint);
    }

    @Override
    public NBTTagIntArray newInstance(int[] data) {
        return new NBTTagIntArray_v1_10_R1(data);
    }

    @Override
    public int[] getIntArray() {
        return d();
    }

    @Override
    public NBTTagIntArray_v1_10_R1 c() {
        return new NBTTagIntArray_v1_10_R1(d());
    }

    @Override
    public NBTTagIntArray_v1_10_R1 clone() {
        return this.c();
    }

}
