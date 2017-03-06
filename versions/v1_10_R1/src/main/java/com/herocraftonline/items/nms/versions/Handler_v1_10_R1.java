/*
 * This file is part of Relics NMS 1_10_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_10_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions;

import com.herocraftonline.items.nms.NMSHandler;
import com.herocraftonline.items.nms.nbt.NBTBase;
import com.herocraftonline.items.nms.versions.nbt.*;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class Handler_v1_10_R1 extends NMSHandler {

    private static final Field HANDLE_FIELD;

    static {
        try {
            HANDLE_FIELD = CraftItemStack.class.getDeclaredField("handle");
            HANDLE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Handler_v1_10_R1() {
        NBTBase.NBT_INSTANCES[1] = new NBTTagByte_v1_10_R1((byte) 0);
        NBTBase.NBT_INSTANCES[2] = new NBTTagShort_v1_10_R1((short) 0);
        NBTBase.NBT_INSTANCES[3] = new NBTTagInt_v1_10_R1(0);
        NBTBase.NBT_INSTANCES[4] = new NBTTagLong_v1_10_R1(0L);
        NBTBase.NBT_INSTANCES[5] = new NBTTagFloat_v1_10_R1(0f);
        NBTBase.NBT_INSTANCES[6] = new NBTTagDouble_v1_10_R1(0d);
        NBTBase.NBT_INSTANCES[7] = new NBTTagByteArray_v1_10_R1(new byte[]{});
        NBTBase.NBT_INSTANCES[8] = new NBTTagString_v1_10_R1();
        NBTBase.NBT_INSTANCES[9] = new NBTTagList_v1_10_R1(new NBTTagList());
        NBTBase.NBT_INSTANCES[10] = new NBTTagCompound_v1_10_R1(new NBTTagCompound());
        NBTBase.NBT_INSTANCES[11] = new NBTTagIntArray_v1_10_R1(new int[]{});
        NBTBase.NBT_INSTANCES[12] = new NBTTagObject_v1_10_R1<>(null);
    }

    @Override
    public com.herocraftonline.items.nms.nbt.NBTTagCompound getTagCompound(ItemStack item) {
        if (item instanceof CraftItemStack) {
            try {
                return getTagCompound((net.minecraft.server.v1_10_R1.ItemStack) HANDLE_FIELD.get(item));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return getTagCompound(CraftItemStack.asNMSCopy(item));
    }

    private com.herocraftonline.items.nms.nbt.NBTTagCompound getTagCompound(net.minecraft.server.v1_10_R1.ItemStack item) {
        if (item.hasTag()) {
            NBTTagCompound tag = item.getTag();
            if (tag instanceof NBTTagCompound_v1_10_R1) {
                return (NBTTagCompound_v1_10_R1) tag;
            } else {
                NBTTagCompound_v1_10_R1 apiTag = new NBTTagCompound_v1_10_R1(tag);
                item.setTag(apiTag);
                return apiTag;
            }
        } else {
            return new NBTTagCompound_v1_10_R1();
        }
    }

    @Override
    public ItemStack setTagCompound(ItemStack item, com.herocraftonline.items.nms.nbt.NBTTagCompound compound) {
        if (item instanceof CraftItemStack) {
            CraftItemStack itemStack = (CraftItemStack) item;
            try {
                net.minecraft.server.v1_10_R1.ItemStack handle = (net.minecraft.server.v1_10_R1.ItemStack) HANDLE_FIELD.get(itemStack);
                handle.setTag((NBTTagCompound_v1_10_R1) compound);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return item;
        } else {
            net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            nmsItem.setTag((NBTTagCompound_v1_10_R1) compound);
            return CraftItemStack.asBukkitCopy(nmsItem);
        }
    }

}
