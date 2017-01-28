package ninja.amp.items.nms.nbt;

public interface NBTTagFloat extends NBTNumber {

    static NBTTagFloat create(float data) {
        return ((NBTTagFloat) NBTBase.NBT_INSTANCES[5]).newInstance(data);
    }

    NBTTagFloat newInstance(float data);

}
