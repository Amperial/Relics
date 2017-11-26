/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Equippable;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.Minecraft;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.stats.BaseStatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatSpecifier;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatTotal;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatType;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class MinecraftAttribute extends BaseStatAttribute<Minecraft> implements Minecraft, Equippable {

    private static final StatType<Minecraft> STAT_TYPE = new MinecraftStatType();

    /**
     * Handles totalling the generic minecraft attributes of an item and adding them to the item's lore.
     */
    protected static class MinecraftStatType implements StatType<Minecraft> {
        @Override
        public Position getPosition() {
            return Position.BOTTOM;
        }

        @Override
        public StatTotal<Minecraft> newTotal(StatSpecifier<Minecraft> specifier) {
            return new StatTotal<Minecraft>(specifier) {
                double amount = 0;

                @Override
                public void addStat(Minecraft stat) {
                    amount += stat.getAmount();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    MinecraftStatSpecifier specifier = (MinecraftStatSpecifier) getStatSpecifier();
                    lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, amount));
                }

                @Override
                public boolean shouldAddLore() {
                    return amount > 0 || amount < 0;
                }
            };
        }

        @Override
        public void addTo(List<String> lore, Map<StatSpecifier<Minecraft>, StatTotal<Minecraft>> stats) {
            stats.entrySet().stream()
                    .collect(Collectors.groupingBy(e -> ((MinecraftStatSpecifier) e.getKey()).slot, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                    .entrySet().forEach(slot -> {
                lore.add(ChatColor.GRAY + slot.getKey().getDisplayName());
                slot.getValue().forEach(total -> total.addTo(lore, "  "));
            });
        }

        public String formatModifier(Operation operation, Type type, boolean stacking, double amount) {
            return operation.prefix(amount, stacking) + operation.amount(amount) + operation.suffix() + " " + type.getDisplayName();
        }
    }

    /**
     * A generic minecraft attribute stat specifier that separates stats by slot, type, operation, and stacking.
     */
    protected static class MinecraftStatSpecifier implements StatSpecifier<Minecraft> {
        protected final Slot slot;
        protected final Type type;
        protected final Operation operation;
        protected final boolean stacking;

        public MinecraftStatSpecifier(Minecraft attribute) {
            this.slot = attribute.getSlot();
            this.type = attribute.getMinecraftType();
            this.operation = attribute.getOperation();
            this.stacking = attribute.isStacking();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MinecraftStatSpecifier)) return false;
            MinecraftStatSpecifier that = (MinecraftStatSpecifier) o;
            return stacking == that.stacking &&
                    slot == that.slot &&
                    type == that.type &&
                    operation == that.operation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(slot, type, operation, stacking);
        }
    }

    private final Type minecraftType;
    private Slot slot;
    private Operation operation;
    private boolean stacking;
    private UUID uuid;
    private double amount;

    public MinecraftAttribute(String name, AttributeType<Minecraft> attributeType, StatType<Minecraft> statType, Type minecraftType, Slot slot, Operation operation, boolean stacking, double amount) {
        super(name, attributeType, statType);

        this.minecraftType = minecraftType;
        this.slot = slot;
        this.operation = operation;
        this.setStacking(stacking);
        this.amount = amount;
    }

    public MinecraftAttribute(String name, Type minecraftType, Slot slot, Operation operation, boolean stacking, double amount) {
        this(name, DefaultAttribute.MINECRAFT, STAT_TYPE, minecraftType, slot, operation, stacking, amount);
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
    public StatSpecifier<Minecraft> getStatSpecifier() {
        return new MinecraftStatSpecifier(this);
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

    public static class Factory extends BaseAttributeFactory<Minecraft> {
        public Factory(ItemPlugin plugin) {
            super(plugin);

            // Load config strings for minecraft attribute types and slots
            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttribute.MINECRAFT);
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
