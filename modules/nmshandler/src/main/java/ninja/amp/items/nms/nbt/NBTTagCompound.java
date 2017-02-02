/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems NMS API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems NMS API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems NMS API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.nms.nbt;

import java.util.Set;

public interface NBTTagCompound extends NBTBase {

    static NBTTagCompound create() {
        return ((NBTTagCompound) NBTBase.NBT_INSTANCES[10]).newInstance();
    }

    NBTTagCompound newInstance();

    Set<String> getKeySet();

    void setBase(String key, NBTBase value);

    void setByte(String key, byte value);

    void setShort(String key, short value);

    void setInt(String key, int value);

    void setLong(String key, long value);

    void setFloat(String key, float value);

    void setDouble(String key, double value);

    void setString(String key, String value);

    void setByteArray(String key, byte[] value);

    void setIntArray(String key, int[] value);

    void setBoolean(String key, boolean value);

    NBTBase getBase(String key);

    boolean hasKey(String key);

    byte getByte(String key);

    short getShort(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    double getDouble(String key);

    String getString(String key);

    byte[] getByteArray(String key);

    int[] getIntArray(String key);

    NBTTagCompound getCompound(String key);

    NBTTagList getList(String key, int typeId);

    boolean getBoolean(String key);

    void remove(String key);

    boolean isEmpty();

}
