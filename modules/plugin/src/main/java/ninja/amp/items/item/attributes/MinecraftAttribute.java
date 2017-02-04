/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class MinecraftAttribute extends BasicAttribute implements ninja.amp.items.api.item.attribute.attributes.MinecraftAttribute {

    private final Type minecraftType;
    private double amount;
    private Operation operation;
    private Slot slot;
    private boolean stacks;
    private UUID uuid;

    public MinecraftAttribute(String name, AttributeType type, Type minecraftType, double amount, Operation operation, Slot slot, boolean stacks) {
        super(name, type);

        this.minecraftType = minecraftType;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
        this.setStacks(stacks);

        setLore((lore, prefix) -> {
            lore.add(prefix + ChatColor.GRAY + getSlot().getDisplayName());
            String stacking = getStacks() ? ChatColor.BLUE + "+" : ChatColor.GRAY.toString();
            lore.add(prefix + "  " + stacking + getOperation().format(getAmount()) + " " + getMinecraftType().getDisplayName());
        });
    }

    public MinecraftAttribute(String name, Type minecraftType, double amount, Operation operation, Slot slot, boolean stacks) {
        this(name, DefaultAttributeType.MINECRAFT, minecraftType, amount, operation, slot, stacks);
    }

    @Override
    public Type getMinecraftType() {
        return minecraftType;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Slot getSlot() {
        return slot;
    }

    @Override
    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    @Override
    public boolean getStacks() {
        return stacks;
    }

    @Override
    public void setStacks(boolean stacks) {
        this.stacks = stacks;
        uuid = stacks ? UUID.randomUUID() : minecraftType.getUUID();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("minecraft-type", getMinecraftType().name());
        compound.setDouble("amount", getAmount());
        compound.setString("operation", getOperation().name());
        compound.setString("slot", getSlot().name());
        compound.setBoolean("stacks", getStacks());
    }

    public static class MinecraftAttributeFactory extends BasicAttributeFactory<MinecraftAttribute> {

        public MinecraftAttributeFactory(ItemPlugin plugin) {
            super(plugin);

            // Load config strings for minecraft attribute types and slots
            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributeType.MINECRAFT);
            for (Type type : Type.values()) {
                if (config.isString("type." + type.name())) {
                    type.setDisplayName(config.getString("type." + type.name()));
                }
                type.setDisplayName(ChatColor.translateAlternateColorCodes('&', type.getDisplayName()));
            }
            for (Slot slot : Slot.values()) {
                if (config.isString("slot." + slot.name())) {
                    slot.setDisplayName(config.getString("slot." + slot.name()));
                }
                slot.setDisplayName(ChatColor.translateAlternateColorCodes('&', slot.getDisplayName()));
            }
        }

        @Override
        public MinecraftAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load minecraft type, amount, operation, slot and stacks
            Type minecraftType = Type.valueOf(config.getString("minecraft-type"));
            double amount = config.getDouble("amount", 0);
            Operation operation = Operation.valueOf(config.getString("operation", "ADD_NUMBER"));
            Slot slot = Slot.valueOf(config.getString("slot", "ANY"));
            boolean stacks = config.getBoolean("stacks", true);

            // Create minecraft attribute
            return new MinecraftAttribute(name, minecraftType, amount, operation, slot, stacks);
        }

        @Override
        public MinecraftAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load minecraft type, amount, operation, slot and stacks
            Type minecraftType = Type.valueOf(compound.getString("minecraft-type"));
            double amount = compound.getDouble("amount");
            Operation operation = Operation.valueOf(compound.getString("operation"));
            Slot slot = Slot.valueOf(compound.getString("slot"));
            boolean stacks = compound.getBoolean("stacks");

            // Create minecraft attribute
            return new MinecraftAttribute(name, minecraftType, amount, operation, slot, stacks);
        }

    }

}
