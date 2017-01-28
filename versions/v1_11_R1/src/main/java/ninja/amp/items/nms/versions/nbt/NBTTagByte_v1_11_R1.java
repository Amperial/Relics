package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagByte;

public class NBTTagByte_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagByte implements NBTTagByte {

    public NBTTagByte_v1_11_R1(byte b) {
        super(b);
    }

    public NBTTagByte newInstance(byte data) {
        return new NBTTagByte_v1_11_R1(data);
    }

    @Override
    public long asLong() {
        return d();
    }

    @Override
    public int asInt() {
        return e();
    }

    @Override
    public short asShort() {
        return f();
    }

    @Override
    public byte asByte() {
        return g();
    }

    @Override
    public float asFloat() {
        return i();
    }

    @Override
    public NBTBase clone() {
        return super.c();
    }

}
