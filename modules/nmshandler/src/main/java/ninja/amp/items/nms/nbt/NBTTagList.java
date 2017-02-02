package ninja.amp.items.nms.nbt;

public interface NBTTagList extends NBTBase {

    static NBTTagList create() {
        return ((NBTTagList) NBTBase.NBT_INSTANCES[9]).newInstance();
    }

    NBTTagList newInstance();

    void addBase(NBTBase base);

    void addBase(int index, NBTBase base);

    NBTBase removeBase(int index);

    NBTBase getBase(int index);

    int getInt(int index);

    float getFloat(int index);

    double getDouble(int index);

    String getString(int index);

    NBTTagCompound getCompound(int index);

    int[] getIntArray(int index);

    int size();

    boolean isEmpty();

    int getType();

}
