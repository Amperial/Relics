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

import ninja.amp.items.nms.nbt.NBTTagLong;

public class NBTTagLong_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagLong implements NBTTagLong {

    public NBTTagLong_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagLong l) {
        super(l.d());
    }

    public NBTTagLong_v1_11_R1(long l) {
        super(l);
    }

    @Override
    public NBTTagLong newInstance(long data) {
        return new NBTTagLong_v1_11_R1(data);
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
    public NBTTagLong_v1_11_R1 c() {
        return new NBTTagLong_v1_11_R1(d());
    }

    @Override
    public NBTTagLong_v1_11_R1 clone() {
        return this.c();
    }

}
