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

public interface NBTTagList extends NBTBase {

    static NBTTagList create() {
        return ((NBTTagList) NBT_INSTANCES[9]).newInstance();
    }

    NBTTagList newInstance();

    void addBase(NBTBase base);

    void addBase(int index, NBTBase base);

    NBTBase removeBase(int index);

    NBTBase getBase(int index);

    int getInt(int index);

    float getFloat(int index);

    double getDouble(int index);

    String getString(int index);

    NBTTagCompound getCompound(int index);

    int[] getIntArray(int index);

    int size();

    boolean isEmpty();

    int getType();

}
