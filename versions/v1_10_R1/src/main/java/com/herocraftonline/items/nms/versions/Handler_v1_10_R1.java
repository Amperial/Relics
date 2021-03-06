/*
 * This file is part of Relics NMS 1_10_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_10_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.nms.NMSHandler;
import com.herocraftonline.items.nms.versions.nbt.*;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutAnimation;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Consumer;

public class Handler_v1_10_R1 extends NMSHandler {

    private static final Field ITEM_HANDLE;

    static {
        try {
            ITEM_HANDLE = CraftItemStack.class.getDeclaredField("handle");
            ITEM_HANDLE.setAccessible(true);
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

    private CraftItemStack getCraftItem(ItemStack item) {
        if (item instanceof CraftItemStack) {
            return (CraftItemStack) item;
        } else {
            return CraftItemStack.asCraftCopy(item);
        }
    }

    private net.minecraft.server.v1_10_R1.ItemStack getNMSItem(ItemStack item) {
        CraftItemStack itemStack = getCraftItem(item);
        try {
            return (net.minecraft.server.v1_10_R1.ItemStack) ITEM_HANDLE.get(itemStack);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return CraftItemStack.asNMSCopy(item);
        }
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound getTag(ItemStack item) {
        return getTag(getNMSItem(item));
    }

    private com.herocraftonline.items.api.storage.nbt.NBTTagCompound getTag(net.minecraft.server.v1_10_R1.ItemStack item) {
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

    private Optional<ItemStack> modifyItem(ItemStack item, Consumer<net.minecraft.server.v1_10_R1.ItemStack> modify) {
        if (item instanceof CraftItemStack) {
            CraftItemStack itemStack = (CraftItemStack) item;
            try {
                net.minecraft.server.v1_10_R1.ItemStack handle = (net.minecraft.server.v1_10_R1.ItemStack) ITEM_HANDLE.get(itemStack);
                modify.accept(handle);
                return Optional.empty();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        net.minecraft.server.v1_10_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
        modify.accept(nmsCopy);
        return Optional.of(CraftItemStack.asCraftCopy(CraftItemStack.asBukkitCopy(nmsCopy)));
    }

    @Override
    public Optional<ItemStack> setTag(ItemStack item, com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        return modifyItem(item, itemStack -> itemStack.setTag((NBTTagCompound) compound));
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound toNBT(ItemStack item) {
        NBTTagCompound_v1_10_R1 tag = new NBTTagCompound_v1_10_R1();
        getNMSItem(item).save(tag);
        return tag;
    }

    @Override
    public ItemStack fromNBT(com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        net.minecraft.server.v1_10_R1.ItemStack nmsItem = net.minecraft.server.v1_10_R1.ItemStack.createStack((NBTTagCompound) compound);
        return CraftItemStack.asCraftCopy(CraftItemStack.asBukkitCopy(nmsItem));
    }

    @Override
    public Optional<ItemStack> replaceNBT(ItemStack item, com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        return modifyItem(item, itemStack -> itemStack.c((NBTTagCompound) compound));
    }

    @Override
    public String serializeNBT(com.herocraftonline.items.api.storage.nbt.NBTTagCompound compound) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            NBTCompressedStreamTools.a((NBTTagCompound) compound, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public com.herocraftonline.items.api.storage.nbt.NBTTagCompound deserializeNBT(String compoundString) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(compoundString));

        try {
            return new NBTTagCompound_v1_10_R1(NBTCompressedStreamTools.a(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void playArmSwing(Player player, boolean mainArm) {
        sendPacket(new PacketPlayOutAnimation(getPlayerHandle(player), mainArm ? 0 : 3));
    }

    private EntityPlayer getPlayerHandle(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    private void sendPacket(Packet packet) {
        Bukkit.getOnlinePlayers().forEach(player -> sendPacket(player, packet));
    }

    private void sendPacket(Player player, Packet packet) {
        getPlayerHandle(player).playerConnection.sendPacket(packet);
    }

}
