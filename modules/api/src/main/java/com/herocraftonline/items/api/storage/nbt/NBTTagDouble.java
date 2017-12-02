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

public interface NBTTagDouble extends NBTNumber {

    static NBTTagDouble create(double data) {
        return ((NBTTagDouble) NBT_INSTANCES[6]).newInstance(data);
    }

    NBTTagDouble newInstance(double data);

}
