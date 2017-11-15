/*
 * This file is part of Relics NMS API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms;

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public abstract class NMSHandler {

    private static String activeVersion;
    private static NMSHandler activeInterface;

    public static String getVersion() {
        if (activeVersion == null) {
            // Get minecraft version
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            activeVersion = packageName.substring(packageName.lastIndexOf('.') + 1);
        }

        return activeVersion;
    }

    public static NMSHandler getInterface() {
        if (activeInterface == null) {
            String version = getVersion();

            try {
                final Class<?> clazz = Class.forName("com.herocraftonline.items.nms.versions.Handler_" + version);
                if (NMSHandler.class.isAssignableFrom(clazz)) {
                    activeInterface = (NMSHandler) clazz.getConstructor().newInstance();
                }
            } catch (ClassNotFoundException e) {
                throw new UnsupportedOperationException("You are attempting to load Relics on version " + version + " which is not supported!");
            } catch (Exception e) {
                throw new RuntimeException("Unknown exception loading version handler", e);
            }
        }

        return activeInterface;
    }

    public abstract NBTTagCompound getTag(ItemStack item);

    public abstract Optional<ItemStack> setTag(ItemStack item, NBTTagCompound compound);

    public abstract NBTTagCompound toNBT(ItemStack item);

    public abstract ItemStack fromNBT(NBTTagCompound compound);

    public abstract Optional<ItemStack> replaceNBT(ItemStack item, NBTTagCompound compound);

    public String serializeItem(ItemStack item) {
        return serializeNBT(toNBT(item));
    }

    public ItemStack deserializeItem(String itemString) {
        return fromNBT(deserializeNBT(itemString));
    }

    public abstract String serializeNBT(NBTTagCompound compound);

    public abstract NBTTagCompound deserializeNBT(String compoundString);

}
