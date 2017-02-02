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


import ninja.amp.items.nms.nbt.NBTTagIntArray;

public class NBTTagIntArray_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagIntArray implements NBTTagIntArray {

    public NBTTagIntArray_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagIntArray aint) {
        super(aint.d());
    }

    public NBTTagIntArray_v1_11_R1(int[] aint) {
        super(aint);
    }

    @Override
    public NBTTagIntArray newInstance(int[] data) {
        return new NBTTagIntArray_v1_11_R1(data);
    }

    @Override
    public int[] getIntArray() {
        return d();
    }

    @Override
    public NBTTagIntArray_v1_11_R1 c() {
        return new NBTTagIntArray_v1_11_R1(d());
    }

    @Override
    public NBTTagIntArray_v1_11_R1 clone() {
        return this.c();
    }

}
