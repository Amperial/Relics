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

import com.herocraftonline.items.api.storage.nbt.NBTTagByteArray;

public class NBTTagByteArray_v1_12_R1 extends net.minecraft.server.v1_12_R1.NBTTagByteArray implements NBTTagByteArray {

    public NBTTagByteArray_v1_12_R1(net.minecraft.server.v1_12_R1.NBTTagByteArray data) {
        super(data.c());
    }

    public NBTTagByteArray_v1_12_R1(byte[] data) {
        super(data);
    }

    @Override
    public NBTTagByteArray newInstance(byte[] data) {
        return new NBTTagByteArray_v1_12_R1(data);
    }

    @Override
    public byte[] getByteArray() {
        return c();
    }

    @Override
    public NBTTagByteArray_v1_12_R1 clone() {
        byte[] data = c();
        byte[] abyte = new byte[data.length];
        System.arraycopy(data, 0, abyte, 0, data.length);
        return new NBTTagByteArray_v1_12_R1(abyte);
    }

}
