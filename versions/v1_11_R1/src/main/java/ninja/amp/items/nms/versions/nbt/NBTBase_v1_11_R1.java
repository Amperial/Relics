package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTBase;

public class NBTBase_v1_11_R1 implements NBTBase {

    private net.minecraft.server.v1_11_R1.NBTBase base;

    public NBTBase_v1_11_R1(net.minecraft.server.v1_11_R1.NBTBase base) {
        this.base = base;
    }

    public net.minecraft.server.v1_11_R1.NBTBase getBase() {
        return base;
    }

    @Override
    public String toString() {
        return base.toString();
    }

    @Override
    public byte getTypeId() {
        return base.getTypeId();
    }

    @Override
    public net.minecraft.server.v1_11_R1.NBTBase clone() {
        return base.clone();
    }

}
