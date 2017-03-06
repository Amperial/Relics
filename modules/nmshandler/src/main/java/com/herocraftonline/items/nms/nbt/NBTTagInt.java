/*
 * This file is part of Relics NMS API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.nbt;

public interface NBTTagInt extends NBTNumber {

    static NBTTagInt create(int data) {
        return ((NBTTagInt) NBTBase.NBT_INSTANCES[3]).newInstance(data);
    }

    NBTTagInt newInstance(int data);

}
