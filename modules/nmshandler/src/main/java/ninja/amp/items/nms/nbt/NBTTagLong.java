package ninja.amp.items.nms.nbt;

public interface NBTTagLong extends NBTNumber {

    static NBTTagLong create(long data) {
        return ((NBTTagLong) NBTBase.NBT_INSTANCES[4]).newInstance(data);
    }

    NBTTagLong newInstance(long data);

}
