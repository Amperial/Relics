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

public interface NBTTagFloat extends NBTNumber {

    static NBTTagFloat create(float data) {
        return ((NBTTagFloat) NBT_INSTANCES[5]).newInstance(data);
    }

    NBTTagFloat newInstance(float data);

}
