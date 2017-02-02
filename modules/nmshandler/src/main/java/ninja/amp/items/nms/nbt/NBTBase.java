package ninja.amp.items.nms.nbt;

public interface NBTBase {

    NBTBase[] NBT_INSTANCES = new NBTBase[13];

    String toString();

    byte getTypeId();

}
