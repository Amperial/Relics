/*
 * This file is part of Relics NMS 1_12_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_12_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.nms.NMSHandler;
import com.herocraftonline.items.nms.versions.nbt.*;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;

public class Handler_v1_12_R1 extends NMSHandler {

    private static final Field HANDLE_FIELD;

    static {
        try {
            HANDLE_FIELD = CraftItemStack.class.getDeclaredField("handle");
            HANDLE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Handler_v1_12_R1() {
        NBTBase.NBT_INSTANCES[1] = new NBTTagByte_v1_12_R1((byte) 0);
        NBTBase.NBT_INSTANCES[2] = new NBTTagShort_v1_12_R1((short) 0);
        NBTBase.NBT_INSTANCES[3] = new NBTTagInt_v1_12_R1(0);
        NBTBase.NBT_INSTANCES[4] = new NBTTagLong_v1_12_R1(0L);
        NBTBase.NBT_INSTANCES[5] = new NBTTagFloat_v1_12_R1(0f);
        NBTBase.NBT_INSTANCES[6] = new NBTTagDouble_v1_12_R1(0d);
        NBTBase.NBT_INSTANCES[7] = new NBTTagByteArray_v1_12_R1(new byte[]{});
        NBTBase.NBT_INSTANCES[8] = new NBTTagString_v1_12_R1();
        NBTBase.NBT_INSTANCES[9] = new NBTTagList_v1_12_R1(new NBTTagList());
        NBTBase.NBT_INSTANCES[10] = new NBTTagCompound_v1_12_R1(new NBTTagCompound());
        NBTBase.NBT_INSTANCES[11] = new NBTTagIntArray_v1_12_R1(new int[]{});
        NBTBase.NBT_INSTANCES[12] = new NBTTagObject_v1_12_R1<>(null);
    }

    private net.minecraft.server.v1_12_R1.ItemStack getNmsItem(ItemStack item) {
        if (item instanceof CraftItemStack) {
            CraftItemStack itemStack = (CraftItemStack) item;
            try {
                return (net.minecraft.server.v1_12_R1.ItemStack) HANDLE_FIELD.get(itemStack);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return CraftItemStack.asNMSCopy(item);
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound getTagCompound(ItemStack item) {
        return getTagCompound(getNmsItem(item));
    }

    private com.herocraftonline.items.api.storage.nbt.NBTTagCompound getTagCompound(net.minecraft.server.v1_12_R1.ItemStack item) {
        if (item.hasTag()) {
            NBTTagCompound tag = item.getTag();
            if (tag instanceof NBTTagCompound_v1_12_R1) {
                return (NBTTagCompound_v1_12_R1) tag;
            } else {
                NBTTagCompound_v1_12_R1 apiTag = new NBTTagCompound_v1_12_R1(tag);
                item.setTag(apiTag);
                return apiTag;
            }
        } else {
            return new NBTTagCompound_v1_12_R1();
        }
    }

    @Override
    public ItemStack setTagCompound(ItemStack item, com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        if (item instanceof CraftItemStack) {
            CraftItemStack itemStack = (CraftItemStack) item;
            try {
                net.minecraft.server.v1_12_R1.ItemStack handle = (net.minecraft.server.v1_12_R1.ItemStack) HANDLE_FIELD.get(itemStack);
                handle.setTag((NBTTagCompound_v1_12_R1) compound);
                return item;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        nmsItem.setTag((NBTTagCompound_v1_12_R1) compound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound toTagCompound(ItemStack item) {
        NBTTagCompound_v1_12_R1 tag = new NBTTagCompound_v1_12_R1();
        getNmsItem(item).save(tag);
        return tag;
    }

    @Override
    public ItemStack fromTagCompound(com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack((NBTTagCompound) compound));
    }

    @Override
    public String serializeTagCompound(com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            NBTCompressedStreamTools.a((NBTTagCompound) compound, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound deserializeTagCompound(String compoundString) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(compoundString));

        try {
            return new NBTTagCompound_v1_12_R1(NBTCompressedStreamTools.a(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
