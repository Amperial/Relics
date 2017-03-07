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

public interface NBTTagByteArray extends NBTBase {

    static NBTTagByteArray create(byte[] data) {
        return ((NBTTagByteArray) NBT_INSTANCES[7]).newInstance(data);
    }

    NBTTagByteArray newInstance(byte[] data);

    byte[] getByteArray();

}
