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

import com.herocraftonline.items.api.storage.nbt.NBTTagDouble;

public class NBTTagDouble_v1_10_R1 extends net.minecraft.server.v1_10_R1.NBTTagDouble implements NBTTagDouble {

    public NBTTagDouble_v1_10_R1(net.minecraft.server.v1_10_R1.NBTTagDouble v) {
        super(v.h());
    }

    public NBTTagDouble_v1_10_R1(double v) {
        super(v);
    }

    @Override
    public NBTTagDouble newInstance(double data) {
        return new NBTTagDouble_v1_10_R1(data);
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
    public double asDouble() {
        return h();
    }

    @Override
    public NBTTagDouble_v1_10_R1 c() {
        return new NBTTagDouble_v1_10_R1(asDouble());
    }

    @Override
    public NBTTagDouble_v1_10_R1 clone() {
        return this.c();
    }

}
