/*
 * This file is part of AmpItems NMS 1_11_R1 Compatibility.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems NMS 1_11_R1 Compatibility is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems NMS 1_11_R1 Compatibility is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems NMS 1_11_R1 Compatibility.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.nms.versions;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.versions.nbt.*;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class Handler_v1_11_R1 extends NMSHandler {

    private static final Field HANDLE_FIELD;

    static {
        try {
            HANDLE_FIELD = CraftItemStack.class.getDeclaredField("handle");
            HANDLE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Handler_v1_11_R1() {
        NBTBase.NBT_INSTANCES[1] = new NBTTagByte_v1_11_R1((byte) 0);
        NBTBase.NBT_INSTANCES[2] = new NBTTagShort_v1_11_R1((short) 0);
        NBTBase.NBT_INSTANCES[3] = new NBTTagInt_v1_11_R1(0);
        NBTBase.NBT_INSTANCES[4] = new NBTTagLong_v1_11_R1(0L);
        NBTBase.NBT_INSTANCES[5] = new NBTTagFloat_v1_11_R1(0f);
        NBTBase.NBT_INSTANCES[6] = new NBTTagDouble_v1_11_R1(0d);
        NBTBase.NBT_INSTANCES[7] = new NBTTagByteArray_v1_11_R1(new byte[]{});
        NBTBase.NBT_INSTANCES[8] = new NBTTagString_v1_11_R1();
        NBTBase.NBT_INSTANCES[9] = new NBTTagList_v1_11_R1(new NBTTagList());
        NBTBase.NBT_INSTANCES[10] = new NBTTagCompound_v1_11_R1(new NBTTagCompound());
        NBTBase.NBT_INSTANCES[11] = new NBTTagIntArray_v1_11_R1(new int[]{});
        NBTBase.NBT_INSTANCES[12] = new NBTTagObject_v1_11_R1<>(null);
    }

    @Override
    public ninja.amp.items.nms.nbt.NBTTagCompound getTagCompoundCopy(ItemStack item) {
        return getTagCompound(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public ninja.amp.items.nms.nbt.NBTTagCompound getTagCompoundDirect(ItemStack item) {
        if (item instanceof CraftItemStack) {
            try {
                return getTagCompound((net.minecraft.server.v1_11_R1.ItemStack) HANDLE_FIELD.get(item));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return getTagCompoundCopy(item);
    }

    private ninja.amp.items.nms.nbt.NBTTagCompound getTagCompound(net.minecraft.server.v1_11_R1.ItemStack item) {
        if (item.hasTag()) {
            NBTTagCompound tag = item.getTag();
            if (tag instanceof NBTTagCompound_v1_11_R1) {
                return (NBTTagCompound_v1_11_R1) tag;
            } else {
                NBTTagCompound_v1_11_R1 apiTag = new NBTTagCompound_v1_11_R1(tag);
                item.setTag(apiTag);
                return apiTag;
            }
        } else {
            return new NBTTagCompound_v1_11_R1();
        }
    }

    @Override
    public ItemStack setTagCompoundCopy(ItemStack item, ninja.amp.items.nms.nbt.NBTTagCompound compound) {
        net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        nmsItem.setTag((NBTTagCompound_v1_11_R1) compound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public void setTagCompoundDirect(ItemStack item, ninja.amp.items.nms.nbt.NBTTagCompound compound) {
        if (item instanceof CraftItemStack) {
            CraftItemStack itemStack = (CraftItemStack) item;
            try {
                net.minecraft.server.v1_11_R1.ItemStack handle = (net.minecraft.server.v1_11_R1.ItemStack) HANDLE_FIELD.get(itemStack);
                handle.setTag((NBTTagCompound_v1_11_R1) compound);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            // TODO: Research how to handle this case. Shouldn't be a problem for now
        }
    }

}
