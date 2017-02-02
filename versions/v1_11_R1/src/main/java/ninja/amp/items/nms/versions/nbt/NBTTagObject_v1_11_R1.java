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

import ninja.amp.items.nms.nbt.NBTTagObject;

public class NBTTagObject_v1_11_R1<T> extends NBTTagByte_v1_11_R1 implements NBTTagObject<T> {

    private T object;

    public NBTTagObject_v1_11_R1(T object) {
        super((byte) 0);

        this.object = object;
    }

    @Override
    public <B> NBTTagObject<B> newInstance(B item) {
        return new NBTTagObject_v1_11_R1<>(item);
    }

    @Override
    public T getObject() {
        return object;
    }

    @Override
    public NBTTagObject_v1_11_R1<T> c() {
        return new NBTTagObject_v1_11_R1<>(object);
    }

}
