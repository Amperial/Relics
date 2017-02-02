package ninja.amp.items.nms.nbt;

public interface NBTTagObject<T> extends NBTBase {

    static <A> NBTTagObject<A> create(A data) {
        return ((NBTTagObject<?>) NBTBase.NBT_INSTANCES[12]).newInstance(data);
    }

    <B> NBTTagObject<B> newInstance(B item);

    T getObject();

}
