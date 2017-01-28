package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagString;

public class NBTTagString_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagString implements NBTTagString {

    public NBTTagString_v1_11_R1() {
        super();
    }

    public NBTTagString_v1_11_R1(String data) {
        super(data);
    }

    @Override
    public NBTTagString newInstance() {
        return new NBTTagString_v1_11_R1();
    }

    @Override
    public NBTTagString newInstance(String data) {
        return new NBTTagString_v1_11_R1(data);
    }

    @Override
    public String getString() {
        return c_();
    }

    @Override
    public NBTBase clone() {
        return super.c();
    }

}
