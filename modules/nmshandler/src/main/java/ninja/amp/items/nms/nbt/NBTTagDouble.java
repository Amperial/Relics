/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems NMS API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.nms.nbt;

public interface NBTTagDouble extends NBTNumber {

    static NBTTagDouble create(double data) {
        return ((NBTTagDouble) NBTBase.NBT_INSTANCES[6]).newInstance(data);
    }

    NBTTagDouble newInstance(double data);

}
