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

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagByteArray;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagIntArray;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;

public final class NBTUtil_v1_12_R1 {

    private NBTUtil_v1_12_R1() {
    }

    public static NBTBase fromNMSBase(net.minecraft.server.v1_12_R1.NBTBase base) {
        if (base instanceof NBTBase) {
            return (NBTBase) base;
        } else {
            switch (base.getTypeId()) {
                case 1:
                    return new NBTTagByte_v1_12_R1((NBTTagByte) base);
                case 2:
                    return new NBTTagShort_v1_12_R1((NBTTagShort) base);
                case 3:
                    return new NBTTagInt_v1_12_R1((NBTTagInt) base);
                case 4:
                    return new NBTTagLong_v1_12_R1((NBTTagLong) base);
                case 5:
                    return new NBTTagFloat_v1_12_R1((NBTTagFloat) base);
                case 6:
                    return new NBTTagDouble_v1_12_R1((NBTTagDouble) base);
                case 7:
                    return new NBTTagByteArray_v1_12_R1((NBTTagByteArray) base);
                case 8:
                    return new NBTTagString_v1_12_R1((NBTTagString) base);
                case 9:
                    return new NBTTagList_v1_12_R1((net.minecraft.server.v1_12_R1.NBTTagList) base);
                case 10:
                    return new NBTTagCompound_v1_12_R1((net.minecraft.server.v1_12_R1.NBTTagCompound) base);
                case 11:
                    return new NBTTagIntArray_v1_12_R1((NBTTagIntArray) base);
                case 12:
                    return new NBTTagLongArray_v1_12_R1((NBTTagLongArray) base);
                default:
                    return null;
            }
        }
    }

}
