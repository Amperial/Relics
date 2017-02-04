/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

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
        setStacks(stacks);
        setLore((lore, prefix) -> {
            lore.add(prefix + getSlot().getDisplayName());
            lore.add(prefix + "  " + getOperation().getSymbol() + getAmount() + " " + getMinecraftType().getDisplayName());
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
        }

        @Override
        public MinecraftAttribute loadFromConfig(String name, ConfigurationSection config) {
            // Load minecraft type, amount, operation, slot and stacks
            Type minecraftType = Type.valueOf(config.getString("minecraft-type"));
            double amount = config.getDouble("amount", 0);
            Operation operation = Operation.valueOf(config.getString("operation"));
            Slot slot = Slot.valueOf(config.getString("slot"));
            boolean stacks = config.getBoolean("stacks");

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
