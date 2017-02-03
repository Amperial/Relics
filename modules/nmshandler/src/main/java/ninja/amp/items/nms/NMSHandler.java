/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems NMS API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems NMS API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems NMS API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.nms;

import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public abstract class NMSHandler {

    private static NMSHandler activeInterface;

    public static NMSHandler getInterface() {
        if (activeInterface == null) {
            // Get minecraft version
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            if (version.equals("craftbukkit")) {
                version = "pre";
            }

            try {
                final Class<?> clazz = Class.forName("ninja.amp.items.nms.versions.Handler_" + version);
                if (NMSHandler.class.isAssignableFrom(clazz)) {
                    activeInterface = (NMSHandler) clazz.getConstructor().newInstance();
                }
            } catch (ClassNotFoundException e) {
                throw new UnsupportedOperationException("You are attempting to load AmpItems on version " + version + " which is not supported!");
            } catch (Exception e) {
                throw new RuntimeException("Unknown exception loading version handler", e);
            }
        }

        return activeInterface;
    }

    public abstract NBTTagCompound getTagCompoundCopy(ItemStack item);

    public abstract NBTTagCompound getTagCompoundDirect(ItemStack item);

    public abstract ItemStack setTagCompoundCopy(ItemStack item, NBTTagCompound compound);

    public abstract ItemStack setTagCompoundDirect(ItemStack item, NBTTagCompound compound);

}
