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

import com.herocraftonline.items.api.storage.nbt.NBTTagFloat;

public class NBTTagFloat_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagFloat implements NBTTagFloat {

    public NBTTagFloat_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagFloat v) {
        super(v.i());
    }

    public NBTTagFloat_v1_11_R1(float v) {
        super(v);
    }

    @Override
    public NBTTagFloat newInstance(float data) {
        return new NBTTagFloat_v1_11_R1(data);
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
    public NBTTagFloat_v1_11_R1 c() {
        return new NBTTagFloat_v1_11_R1(i());
    }

    @Override
    public NBTTagFloat_v1_11_R1 clone() {
        return this.c();
    }

}
