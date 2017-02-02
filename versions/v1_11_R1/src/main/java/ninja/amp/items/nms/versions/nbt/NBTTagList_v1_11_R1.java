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

import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;

public class NBTTagList_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagList implements NBTTagList {

    public NBTTagList_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagList list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            add(list.h(i).clone());
        }
    }

    public NBTTagList_v1_11_R1() {
    }

    @Override
    public NBTTagList newInstance() {
        return new NBTTagList_v1_11_R1();
    }

    @Override
    public void addBase(NBTBase base) {
        add((net.minecraft.server.v1_11_R1.NBTBase) base);
    }

    @Override
    public void addBase(int index, NBTBase base) {
        a(index, (net.minecraft.server.v1_11_R1.NBTBase) base);
    }

    @Override
    public NBTBase removeBase(int index) {
        return NBTUtil.fromNMSBase(remove(index));
    }

    @Override
    public NBTBase getBase(int index) {
        net.minecraft.server.v1_11_R1.NBTBase base = h(index);
        if (base instanceof NBTBase) {
            return (NBTBase) base;
        } else {
            NBTBase apiBase = NBTUtil.fromNMSBase(base);
            addBase(index, apiBase);
            return apiBase;
        }
    }

    @Override
    public int getInt(int index) {
        return c(index);
    }

    @Override
    public float getFloat(int index) {
        return f(index);
    }

    @Override
    public double getDouble(int index) {
        return e(index);
    }

    @Override
    public NBTTagCompound getCompound(int index) {
        net.minecraft.server.v1_11_R1.NBTTagCompound compound = super.get(index);
        if (compound instanceof NBTTagCompound_v1_11_R1) {
            return (NBTTagCompound_v1_11_R1) compound;
        } else {
            NBTTagCompound_v1_11_R1 apiCompound = new NBTTagCompound_v1_11_R1(compound);
            a(index, apiCompound);
            return apiCompound;
        }
    }

    @Override
    public int[] getIntArray(int index) {
        return d(index);
    }

    @Override
    public int getType() {
        return g();
    }

    @Override
    public NBTTagList_v1_11_R1 d() {
        NBTTagList_v1_11_R1 clone = new NBTTagList_v1_11_R1();

        int size = size();
        for (int i = 0; i < size; i++) {
            clone.add(h(i).clone());
        }

        return clone;
    }

    @Override
    public NBTTagList_v1_11_R1 clone() {
        return this.d();
    }

}
