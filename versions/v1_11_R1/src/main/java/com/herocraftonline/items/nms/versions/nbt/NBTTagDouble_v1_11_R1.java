/*
 * This file is part of Relics NMS 1_11_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_11_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions.nbt;

import com.herocraftonline.items.nms.nbt.NBTTagDouble;

public class NBTTagDouble_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagDouble implements NBTTagDouble {

    public NBTTagDouble_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagDouble v) {
        super(v.asDouble());
    }

    public NBTTagDouble_v1_11_R1(double v) {
        super(v);
    }

    @Override
    public NBTTagDouble newInstance(double data) {
        return new NBTTagDouble_v1_11_R1(data);
    }

    @Override
    public long asLong() {
        return d();
    }

    @Override
    public int asInt() {
        return e();
    }

    @Override
    public short asShort() {
        return f();
    }

    @Override
    public byte asByte() {
        return g();
    }

    @Override
    public float asFloat() {
        return i();
    }

    @Override
    public NBTTagDouble_v1_11_R1 c() {
        return new NBTTagDouble_v1_11_R1(asDouble());
    }

    @Override
    public NBTTagDouble_v1_11_R1 clone() {
        return this.c();
    }

}
