package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagShort;

public class NBTTagShort_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagShort implements NBTTagShort {

    public NBTTagShort_v1_11_R1(short data) {
        super(data);
    }

    @Override
    public NBTTagShort newInstance(short data) {
        return new NBTTagShort_v1_11_R1(data);
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
