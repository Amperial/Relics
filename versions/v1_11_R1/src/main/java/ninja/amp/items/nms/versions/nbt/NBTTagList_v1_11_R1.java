package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;

public class NBTTagList_v1_11_R1 implements NBTTagList {

    private net.minecraft.server.v1_11_R1.NBTTagList list;

    public NBTTagList_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagList list) {
        this.list = list;
    }

    public net.minecraft.server.v1_11_R1.NBTTagList getList() {
        return list;
    }

    @Override
    public NBTTagList newInstance() {
        return new NBTTagList_v1_11_R1(new net.minecraft.server.v1_11_R1.NBTTagList());
    }

    @Override
    public void add(NBTBase base) {
        switch (base.getTypeId()) {
            case 0:
                break;
            case 1:
                list.add((NBTTagByte_v1_11_R1) base);
                break;
            case 2:
                list.add((NBTTagShort_v1_11_R1) base);
                break;
            case 3:
                list.add((NBTTagInt_v1_11_R1) base);
                break;
            case 4:
                list.add((NBTTagLong_v1_11_R1) base);
                break;
            case 5:
                list.add((NBTTagFloat_v1_11_R1) base);
                break;
            case 6:
                list.add((NBTTagDouble_v1_11_R1) base);
                break;
            case 7:
                list.add((NBTTagByteArray_v1_11_R1) base);
                break;
            case 8:
                list.add((NBTTagString_v1_11_R1) base);
                break;
            case 9:
                list.add(((NBTTagList_v1_11_R1) base).getList());
                break;
            case 10:
                list.add(((NBTTagCompound_v1_11_R1) base).getCompound());
                break;
            case 11:
                list.add((NBTTagIntArray_v1_11_R1) base);
                break;
            default:
        }
    }

    @Override
    public NBTBase remove(int index) {
        return new NBTBase_v1_11_R1(list.remove(index));
    }

    @Override
    public NBTBase get(int index) {
        return new NBTBase_v1_11_R1(list.get(index));
    }

    @Override
    public int getInt(int index) {
        return list.c(index);
    }

    @Override
    public float getFloat(int index) {
        return list.f(index);
    }

    @Override
    public double getDouble(int index) {
        return list.e(index);
    }

    @Override
    public String getString(int index) {
        return list.getString(index);
    }

    @Override
    public NBTTagCompound getCompound(int index) {
        return new NBTTagCompound_v1_11_R1(list.get(index));
    }

    @Override
    public int[] getIntArray(int index) {
        return list.d(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int getType() {
        return list.g();
    }

    @Override
    public byte getTypeId() {
        return list.getTypeId();
    }

}
