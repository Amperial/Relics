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

public interface NBTTagLong extends NBTNumber {

    static NBTTagLong create(long data) {
        return ((NBTTagLong) NBT_INSTANCES[4]).newInstance(data);
    }

    NBTTagLong newInstance(long data);

}
