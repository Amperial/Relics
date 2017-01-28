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
