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

public interface NBTTagDouble extends NBTNumber {

    static NBTTagDouble create(double data) {
        return ((NBTTagDouble) NBT_INSTANCES[6]).newInstance(data);
    }

    NBTTagDouble newInstance(double data);

}
