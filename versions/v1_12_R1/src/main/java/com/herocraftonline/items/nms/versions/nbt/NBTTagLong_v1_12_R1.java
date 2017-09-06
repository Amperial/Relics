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

import com.herocraftonline.items.api.storage.nbt.NBTTagLong;

public class NBTTagLong_v1_12_R1 extends net.minecraft.server.v1_12_R1.NBTTagLong implements NBTTagLong {

    public NBTTagLong_v1_12_R1(net.minecraft.server.v1_12_R1.NBTTagLong l) {
        super(l.d());
    }

    public NBTTagLong_v1_12_R1(long l) {
        super(l);
    }

    @Override
    public NBTTagLong newInstance(long data) {
        return new NBTTagLong_v1_12_R1(data);
    }

    @Override
    public byte asByte() {
        return g();
    }

    @Override
    public short asShort() {
        return f();
    }

    @Override
    public int asInt() {
        return e();
    }

    @Override
    public long asLong() {
        return d();
    }

    @Override
    public float asFloat() {
        return i();
    }

    @Override
    public NBTTagLong_v1_12_R1 c() {
        return new NBTTagLong_v1_12_R1(d());
    }

    @Override
    public NBTTagLong_v1_12_R1 clone() {
        return this.c();
    }

}
