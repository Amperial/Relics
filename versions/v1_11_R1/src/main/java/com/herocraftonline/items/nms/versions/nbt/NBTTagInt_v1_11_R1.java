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

import com.herocraftonline.items.api.storage.nbt.NBTTagInt;

public class NBTTagInt_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagInt implements NBTTagInt {

    public NBTTagInt_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagInt i) {
        super(i.e());
    }

    public NBTTagInt_v1_11_R1(int i) {
        super(i);
    }

    @Override
    public NBTTagInt newInstance(int data) {
        return new NBTTagInt_v1_11_R1(data);
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
    public NBTTagInt_v1_11_R1 c() {
        return new NBTTagInt_v1_11_R1(e());
    }

    @Override
    public NBTTagInt_v1_11_R1 clone() {
        return this.c();
    }

}
