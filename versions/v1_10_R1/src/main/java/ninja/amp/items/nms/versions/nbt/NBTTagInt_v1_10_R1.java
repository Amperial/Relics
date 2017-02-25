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

import ninja.amp.items.nms.nbt.NBTTagInt;

public class NBTTagInt_v1_10_R1 extends net.minecraft.server.v1_10_R1.NBTTagInt implements NBTTagInt {

    public NBTTagInt_v1_10_R1(net.minecraft.server.v1_10_R1.NBTTagInt i) {
        super(i.e());
    }

    public NBTTagInt_v1_10_R1(int i) {
        super(i);
    }

    @Override
    public NBTTagInt newInstance(int data) {
        return new NBTTagInt_v1_10_R1(data);
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
    public double asDouble() {
        return h();
    }

    @Override
    public float asFloat() {
        return i();
    }

    @Override
    public NBTTagInt_v1_10_R1 c() {
        return new NBTTagInt_v1_10_R1(e());
    }

    @Override
    public NBTTagInt_v1_10_R1 clone() {
        return this.c();
    }

}
