/*
 * This file is part of Relics NMS 1_12_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_12_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions.nbt;

import com.herocraftonline.items.api.storage.nbt.NBTTagString;

public class NBTTagString_v1_12_R1 extends net.minecraft.server.v1_12_R1.NBTTagString implements NBTTagString {

    public NBTTagString_v1_12_R1(net.minecraft.server.v1_12_R1.NBTTagString data) {
        super(data.c_());
    }

    public NBTTagString_v1_12_R1(String data) {
        super(data);
    }

    public NBTTagString_v1_12_R1() {
        super();
    }

    @Override
    public NBTTagString newInstance(String data) {
        return new NBTTagString_v1_12_R1(data);
    }

    @Override
    public NBTTagString newInstance() {
        return new NBTTagString_v1_12_R1();
    }

    @Override
    public String getString() {
        return c_();
    }

    @Override
    public NBTTagString_v1_12_R1 c() {
        return new NBTTagString_v1_12_R1(c_());
    }

    @Override
    public NBTTagString_v1_12_R1 clone() {
        return this.c();
    }

}
