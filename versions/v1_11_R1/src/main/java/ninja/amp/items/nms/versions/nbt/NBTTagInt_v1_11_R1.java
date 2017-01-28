package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagInt;

public class NBTTagInt_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagInt implements NBTTagInt {

    public NBTTagInt_v1_11_R1(int i) {
        super(i);
    }

    @Override
    public NBTTagInt newInstance(int data) {
        return new NBTTagInt_v1_11_R1(data);
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
