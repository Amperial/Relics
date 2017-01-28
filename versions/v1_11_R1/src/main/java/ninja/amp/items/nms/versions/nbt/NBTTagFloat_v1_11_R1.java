package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagFloat;

public class NBTTagFloat_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagFloat implements NBTTagFloat{

    public NBTTagFloat_v1_11_R1(float v) {
        super(v);
    }

    @Override
    public NBTTagFloat newInstance(float data) {
        return new NBTTagFloat_v1_11_R1(data);
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
