package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTTagByteArray;

public class NBTTagByteArray_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagByteArray implements NBTTagByteArray {

    public NBTTagByteArray_v1_11_R1(byte[] abyte) {
        super(abyte);
    }

    @Override
    public NBTTagByteArray newInstance(byte[] data) {
        return new NBTTagByteArray_v1_11_R1(data);
    }

    @Override
    public byte[] getByteArray() {
        return c();
    }

}
