package ninja.amp.items.nms.versions.nbt;

import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;

import java.util.Set;

public class NBTTagCompound_v1_11_R1 implements NBTTagCompound {

    private net.minecraft.server.v1_11_R1.NBTTagCompound compound;

    public NBTTagCompound_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagCompound compound) {
        this.compound = compound;
    }

    public net.minecraft.server.v1_11_R1.NBTTagCompound getCompound() {
        return compound;
    }

    @Override
    public NBTTagCompound newInstance() {
        return new NBTTagCompound_v1_11_R1(new net.minecraft.server.v1_11_R1.NBTTagCompound());
    }

    @Override
    public Set<String> getKeySet() {
        return compound.c();
    }

    @Override
    public void set(String key, NBTBase value) {
        switch (value.getTypeId()) {
            case 0:
                break;
            case 1:
                compound.set(key, (NBTTagByte_v1_11_R1) value);
                break;
            case 2:
                compound.set(key, (NBTTagShort_v1_11_R1) value);
                break;
            case 3:
                compound.set(key, (NBTTagInt_v1_11_R1) value);
                break;
            case 4:
                compound.set(key, (NBTTagLong_v1_11_R1) value);
                break;
            case 5:
                compound.set(key, (NBTTagFloat_v1_11_R1) value);
                break;
            case 6:
                compound.set(key, (NBTTagDouble_v1_11_R1) value);
                break;
            case 7:
                compound.set(key, (NBTTagByteArray_v1_11_R1) value);
                break;
            case 8:
                compound.set(key, (NBTTagString_v1_11_R1) value);
                break;
            case 9:
                compound.set(key, ((NBTTagList_v1_11_R1) value).getList());
                break;
            case 10:
                compound.set(key, ((NBTTagCompound_v1_11_R1) value).getCompound());
                break;
            case 11:
                compound.set(key, (NBTTagIntArray_v1_11_R1) value);
                break;
            default:
        }
    }

    @Override
    public void setByte(String key, byte value) {
        compound.setByte(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        compound.setShort(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        compound.setInt(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        compound.setLong(key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        compound.setFloat(key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        compound.setDouble(key, value);
    }

    @Override
    public void setString(String key, String value) {
        compound.setString(key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        compound.setByteArray(key, value);
    }

    @Override
    public void setIntArray(String key, int[] value) {
        compound.setIntArray(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        compound.setBoolean(key, value);
    }

    @Override
    public NBTBase get(String key) {
        return new NBTBase_v1_11_R1(compound.get(key));
    }

    @Override
    public boolean hasKey(String key) {
        return compound.hasKey(key);
    }

    @Override
    public byte getByte(String key) {
        return compound.getByte(key);
    }

    @Override
    public short getShort(String key) {
        return compound.getShort(key);
    }

    @Override
    public int getInt(String key) {
        return compound.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return compound.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return compound.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return compound.getDouble(key);
    }

    @Override
    public String getString(String key) {
        return compound.getString(key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return compound.getByteArray(key);
    }

    @Override
    public int[] getIntArray(String key) {
        return compound.getIntArray(key);
    }

    @Override
    public NBTTagCompound getCompound(String key) {
        return new NBTTagCompound_v1_11_R1(compound.getCompound(key));
    }

    @Override
    public NBTTagList getList(String key, int typeId) {
        return new NBTTagList_v1_11_R1(compound.getList(key, typeId));
    }

    @Override
    public boolean getBoolean(String key) {
        return compound.getBoolean(key);
    }

    @Override
    public void remove(String key) {
        compound.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return compound.isEmpty();
    }

    @Override
    public byte getTypeId() {
        return compound.getTypeId();
    }

}
