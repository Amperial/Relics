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
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.Damage;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

public class DamageAttribute extends MinecraftAttribute implements Damage {

    private double variation;

    public DamageAttribute(String name, double damage, double variation, Operation operation, Slot slot, boolean stacks) {
        super(name, DefaultAttributeType.DAMAGE, Type.ATTACK_DAMAGE, damage, operation, slot, stacks);

        this.variation = variation;
        setLore((lore, prefix) -> {
            lore.add(prefix + getSlot().getDisplayName());
            lore.add(prefix + "  " + getOperation().getSymbol() + getAmount() + " Attack Damage");
            lore.add(prefix + "   Damage Spread: +/- " + getVariation());
        });
    }

    @Override
    public double getDamage() {
        return getAmount();
    }

    @Override
    public void setDamage(double damage) {
        setAmount(damage);
    }

    @Override
    public double getVariation() {
        return variation;
    }

    @Override
    public void setVariation(double variation) {
        this.variation = variation;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setDouble("variation", getVariation());
    }

    public static class DamageFactory extends BasicAttributeFactory<Damage> {

        public DamageFactory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Damage loadFromConfig(String name, ConfigurationSection config) {
            // Load damage, variation, operation, slot and stacks
            double damage = config.getDouble("damage");
            double variation = config.getDouble("variation", 0);
            Operation operation = Operation.valueOf(config.getString("operation"));
            Slot slot = Slot.valueOf(config.getString("slot"));
            boolean stacks = config.getBoolean("stacks");

            // Create damage attribute
            return new DamageAttribute(name, damage, variation, operation, slot, stacks);
        }

        @Override
        public Damage loadFromNBT(String name, NBTTagCompound compound) {
            // Load damage, variation, operation, slot and stacks
            double damage = compound.getDouble("amount");
            double variation = compound.getDouble("variation");
            Operation operation = Operation.valueOf(compound.getString("operation"));
            Slot slot = Slot.valueOf(compound.getString("slot"));
            boolean stacks = compound.getBoolean("stacks");

            // Create damage attribute
            return new DamageAttribute(name, damage, variation, operation, slot, stacks);
        }

    }

}
