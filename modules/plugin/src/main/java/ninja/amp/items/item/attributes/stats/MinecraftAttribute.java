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
package ninja.amp.items.item.attributes.stats;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.Equippable;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.stats.BasicStatAttribute;
import ninja.amp.items.api.item.attribute.attributes.stats.GenericAttribute;
import ninja.amp.items.api.item.attribute.attributes.stats.StatSpecifier;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MinecraftAttribute extends BasicStatAttribute<GenericAttribute.GenericAttributeStatType> implements GenericAttribute, Equippable {

    private final Type minecraftType;
    private Slot slot;
    private Operation operation;
    private boolean stacking;
    private UUID uuid;
    private double amount;

    public MinecraftAttribute(String name, AttributeType attributeType, GenericAttributeStatType statType, Type minecraftType, Slot slot, Operation operation, boolean stacking, double amount) {
        super(name, attributeType, statType);

        this.minecraftType = minecraftType;
        this.slot = slot;
        this.operation = operation;
        this.setStacking(stacking);
        this.amount = amount;
    }

    public MinecraftAttribute(String name, Type minecraftType, Slot slot, Operation operation, boolean stacking, double amount) {
        this(name, DefaultAttributeType.MINECRAFT, GenericAttribute.STAT_TYPE, minecraftType, slot, operation, stacking, amount);
    }

    @Override
    public Type getMinecraftType() {
        return minecraftType;
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
    public Operation getOperation() {
        return operation;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean isStacking() {
        return stacking;
    }

    @Override
    public void setStacking(boolean stacking) {
        this.stacking = stacking;
        this.uuid = stacking ? UUID.randomUUID() : minecraftType.getUUID();
    }

    @Override
    public UUID getUUID() {
        return uuid;
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
    public StatSpecifier<GenericAttributeStatType> getStatSpecifier() {
        return new GenericAttributeSpecifier(this);
    }

    @Override
    public boolean canEquip(Player player) {
        return true;
    }

    @Override
    public boolean onEquip(Player player) {
        return true;
    }

    @Override
    public boolean onUnEquip(Player player) {
        return true;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("minecraft-type", getMinecraftType().name());
        compound.setString("slot", getSlot().name());
        compound.setString("operation", getOperation().name());
        compound.setBoolean("stacking", isStacking());
        compound.setDouble("amount", getAmount());
    }

    public static class Factory extends BasicAttributeFactory<MinecraftAttribute> {

        public Factory(ItemPlugin plugin) {
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
            // Load minecraft type, slot, operation, stacking, and amount
            Type minecraftType = Type.valueOf(config.getString("minecraft-type"));
            Slot slot = Slot.valueOf(config.getString("slot", "ANY"));
            Operation operation = Operation.valueOf(config.getString("operation", "ADD_NUMBER"));
            boolean stacking = config.getBoolean("stacking", true);
            double amount = config.getDouble("amount", 0);

            // Create minecraft attribute
            return new MinecraftAttribute(name, minecraftType, slot, operation, stacking, amount);
        }

        @Override
        public MinecraftAttribute loadFromNBT(String name, NBTTagCompound compound) {
            // Load minecraft type, slot, operation, stacking, and amount
            Type minecraftType = Type.valueOf(compound.getString("minecraft-type"));
            Slot slot = Slot.valueOf(compound.getString("slot"));
            Operation operation = Operation.valueOf(compound.getString("operation"));
            boolean stacking = compound.getBoolean("stacking");
            double amount = compound.getDouble("amount");

            // Create minecraft attribute
            return new MinecraftAttribute(name, minecraftType, slot, operation, stacking, amount);
        }

    }

}
