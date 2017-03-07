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

import com.herocraftonline.items.api.storage.nbt.NBTTagShort;

public class NBTTagShort_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagShort implements NBTTagShort {

    public NBTTagShort_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagShort data) {
        super(data.f());
    }

    public NBTTagShort_v1_11_R1(short data) {
        super(data);
    }

    @Override
    public NBTTagShort newInstance(short data) {
        return new NBTTagShort_v1_11_R1(data);
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
    public NBTTagShort_v1_11_R1 c() {
        return new NBTTagShort_v1_11_R1(f());
    }

    @Override
    public NBTTagShort_v1_11_R1 clone() {
        return this.c();
    }

}
