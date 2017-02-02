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

    public abstract NBTTagCompound getTagCompound(ItemStack item);

    public abstract ItemStack setTagCompoundCopy(ItemStack item, NBTTagCompound compound);

    public abstract void setTagCompoundDirect(ItemStack item, NBTTagCompound compound);

}
