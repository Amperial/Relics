/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.storage.nbt;

import java.util.Set;

public interface NBTTagCompound extends NBTBase {

    static NBTTagCompound create() {
        return ((NBTTagCompound) NBT_INSTANCES[10]).newInstance();
    }

    NBTTagCompound newInstance();

    boolean hasKey(String key);

    Set<String> getKeySet();

    boolean getBoolean(String key);

    byte getByte(String key);

    short getShort(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    double getDouble(String key);

    byte[] getByteArray(String key);

    int[] getIntArray(String key);

    String getString(String key);

    Object getObject(String key);

    NBTTagList getList(String key, int typeId);

    NBTTagCompound getCompound(String key);

    NBTBase getBase(String key);

    void setBoolean(String key, boolean value);

    void setByte(String key, byte value);

    void setShort(String key, short value);

    void setInt(String key, int value);

    void setLong(String key, long value);

    void setFloat(String key, float value);

    void setDouble(String key, double value);

    void setByteArray(String key, byte[] value);

    void setIntArray(String key, int[] value);

    void setString(String key, String value);

    void setObject(String key, Object value);

    void setBase(String key, NBTBase value);

    void remove(String key);

    boolean isEmpty();

}
