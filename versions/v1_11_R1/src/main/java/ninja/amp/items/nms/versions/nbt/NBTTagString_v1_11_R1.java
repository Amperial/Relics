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
