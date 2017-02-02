/*
 * This file is part of AmpItems NMS 1_11_R1 Compatibility.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems NMS 1_11_R1 Compatibility is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems NMS 1_11_R1 Compatibility is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems NMS 1_11_R1 Compatibility.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagByteArray;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagIntArray;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;
import ninja.amp.items.nms.nbt.NBTBase;

public final class NBTUtil {

    private NBTUtil() {
    }

    public static NBTBase fromNMSBase(net.minecraft.server.v1_11_R1.NBTBase base) {
        if (base instanceof NBTBase) {
            return (NBTBase) base;
        } else {
            switch (base.getTypeId()) {
                case 1:
                    return new NBTTagByte_v1_11_R1((NBTTagByte) base);
                case 2:
                    return new NBTTagShort_v1_11_R1((NBTTagShort) base);
                case 3:
                    return new NBTTagInt_v1_11_R1((NBTTagInt) base);
                case 4:
                    return new NBTTagLong_v1_11_R1((NBTTagLong) base);
                case 5:
                    return new NBTTagFloat_v1_11_R1((NBTTagFloat) base);
                case 6:
                    return new NBTTagDouble_v1_11_R1((NBTTagDouble) base);
                case 7:
                    return new NBTTagByteArray_v1_11_R1((NBTTagByteArray) base);
                case 8:
                    return new NBTTagString_v1_11_R1((NBTTagString) base);
                case 9:
                    return new NBTTagList_v1_11_R1((net.minecraft.server.v1_11_R1.NBTTagList) base);
                case 10:
                    return new NBTTagCompound_v1_11_R1((net.minecraft.server.v1_11_R1.NBTTagCompound) base);
                case 11:
                    return new NBTTagIntArray_v1_11_R1((NBTTagIntArray) base);
                default:
                    return null;
            }
        }
    }

}
