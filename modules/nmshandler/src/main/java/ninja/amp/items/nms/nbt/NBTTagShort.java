package ninja.amp.items.nms.nbt;

public interface NBTTagShort extends NBTNumber {

    static NBTTagShort create(short data) {
        return ((NBTTagShort) NBTBase.NBT_INSTANCES[2]).newInstance(data);
    }

    NBTTagShort newInstance(short data);

}
