/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.storage.nbt;

public interface NBTTagShort extends NBTNumber {

    static NBTTagShort create(short data) {
        return ((NBTTagShort) NBT_INSTANCES[2]).newInstance(data);
    }

    NBTTagShort newInstance(short data);

}
