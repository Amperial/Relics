package ninja.amp.items.nms.nbt;

public interface NBTTagByte extends NBTNumber {

    static NBTTagByte create(byte data) {
        return ((NBTTagByte) NBTBase.NBT_INSTANCES[1]).newInstance(data);
    }

    NBTTagByte newInstance(byte data);

}
