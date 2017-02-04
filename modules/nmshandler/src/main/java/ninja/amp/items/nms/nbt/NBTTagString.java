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

public interface NBTTagString extends NBTBase {

    static NBTTagString create() {
        return ((NBTTagString) NBTBase.NBT_INSTANCES[8]).newInstance();
    }

    static NBTTagString create(String data) {
        return ((NBTTagString) NBTBase.NBT_INSTANCES[8]).newInstance(data);
    }

    NBTTagString newInstance();

    NBTTagString newInstance(String data);

    String getString();

}
