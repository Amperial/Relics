package ninja.amp.items.nms.nbt;

public interface NBTTagInt extends NBTNumber {

    static NBTTagInt create(int data) {
        return ((NBTTagInt) NBTBase.NBT_INSTANCES[3]).newInstance(data);
    }

    NBTTagInt newInstance(int data);

}
