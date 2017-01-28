package ninja.amp.items.nms.versions;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.versions.nbt.*;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Handler_v1_11_R1 extends NMSHandler {

    public Handler_v1_11_R1() {
        NBTBase.NBT_INSTANCES[1] = new NBTTagByte_v1_11_R1((byte)0);
        NBTBase.NBT_INSTANCES[2] = new NBTTagShort_v1_11_R1((short)0);
        NBTBase.NBT_INSTANCES[3] = new NBTTagInt_v1_11_R1(0);
        NBTBase.NBT_INSTANCES[4] = new NBTTagLong_v1_11_R1(0L);
        NBTBase.NBT_INSTANCES[5] = new NBTTagFloat_v1_11_R1(0f);
        NBTBase.NBT_INSTANCES[6] = new NBTTagDouble_v1_11_R1(0d);
        NBTBase.NBT_INSTANCES[7] = new NBTTagByteArray_v1_11_R1(new byte[]{});
        NBTBase.NBT_INSTANCES[8] = new NBTTagString_v1_11_R1();
        NBTBase.NBT_INSTANCES[9] = new NBTTagList_v1_11_R1(new NBTTagList());
        NBTBase.NBT_INSTANCES[10] = new NBTTagCompound_v1_11_R1(new NBTTagCompound());
        NBTBase.NBT_INSTANCES[11] = new NBTTagIntArray_v1_11_R1(new int[]{});
    }

    @Override
    public ninja.amp.items.nms.nbt.NBTTagCompound getTagCompound(ItemStack item) {
        net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return new NBTTagCompound_v1_11_R1(nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
    }

    @Override
    public ItemStack setTagCompound(ItemStack item, ninja.amp.items.nms.nbt.NBTTagCompound compound) {
        net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        nmsItem.setTag(((NBTTagCompound_v1_11_R1) compound).getCompound());
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

}
