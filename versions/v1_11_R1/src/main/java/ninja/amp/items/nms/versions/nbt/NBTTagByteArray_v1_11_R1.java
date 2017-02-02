package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTTagByteArray;

public class NBTTagByteArray_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagByteArray implements NBTTagByteArray {

    public NBTTagByteArray_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagByteArray data) {
        super(data.c());
    }

    public NBTTagByteArray_v1_11_R1(byte[] data) {
        super(data);
    }

    @Override
    public NBTTagByteArray newInstance(byte[] data) {
        return new NBTTagByteArray_v1_11_R1(data);
    }

    @Override
    public byte[] getByteArray() {
        return c();
    }

    @Override
    public NBTTagByteArray_v1_11_R1 clone() {
        byte[] data = c();
        byte[] abyte = new byte[data.length];
        System.arraycopy(data, 0, abyte, 0, data.length);
        return new NBTTagByteArray_v1_11_R1(abyte);
    }

}
