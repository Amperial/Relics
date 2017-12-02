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

public interface NBTTagInt extends NBTNumber {

    static NBTTagInt create(int data) {
        return ((NBTTagInt) NBT_INSTANCES[3]).newInstance(data);
    }

    NBTTagInt newInstance(int data);

}
