/*
 * This file is part of AmpItems NMS 1_11_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems NMS 1_11_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTTagString;

public class NBTTagString_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagString implements NBTTagString {

    public NBTTagString_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagString data) {
        super(data.c_());
    }

    public NBTTagString_v1_11_R1(String data) {
        super(data);
    }

    public NBTTagString_v1_11_R1() {
        super();
    }

    @Override
    public NBTTagString newInstance() {
        return new NBTTagString_v1_11_R1();
    }

    @Override
    public NBTTagString newInstance(String data) {
        return new NBTTagString_v1_11_R1(data);
    }

    @Override
    public String getString() {
        return c_();
    }

    @Override
    public NBTTagString_v1_11_R1 c() {
        return new NBTTagString_v1_11_R1(c_());
    }

    @Override
    public NBTTagString_v1_11_R1 clone() {
        return this.c();
    }

}
