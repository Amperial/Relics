package ninja.amp.items.nms.nbt;

public interface NBTBase {

    String[] NBT_TYPES = {"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};
    NBTBase[] NBT_INSTANCES = new NBTBase[12];

    String toString();

    byte getTypeId();

}
