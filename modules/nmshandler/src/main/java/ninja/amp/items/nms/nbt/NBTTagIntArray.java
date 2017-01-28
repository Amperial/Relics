package ninja.amp.items.nms.nbt;

public interface NBTTagIntArray extends NBTBase {

    static NBTTagIntArray create(int[] data) {
        return ((NBTTagIntArray) NBTBase.NBT_INSTANCES[11]).newInstance(data);
    }

    NBTTagIntArray newInstance(int[] data);

    int[] getIntArray();

}
