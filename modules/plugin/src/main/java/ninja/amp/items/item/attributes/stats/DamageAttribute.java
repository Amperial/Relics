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
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.stats.Damage;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

public class DamageAttribute extends MinecraftAttribute implements Damage {

    private double variation;

    public DamageAttribute(String name, Slot slot, Operation operation, boolean stacking, double damage, double variation) {
        super(name, DefaultAttributeType.DAMAGE, Damage.STAT_TYPE, Type.ATTACK_DAMAGE, slot, operation, stacking, damage);

        this.variation = variation;
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

    public static class Factory extends BasicAttributeFactory<Damage> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Damage loadFromConfig(String name, ConfigurationSection config) {
            // Load slot, operation, stacking, damage, and variation
            Slot slot = Slot.valueOf(config.getString("slot", "ANY"));
            Operation operation = Operation.valueOf(config.getString("operation", "ADD_NUMBER"));
            boolean stacking = config.getBoolean("stacking", true);
            double damage = config.getDouble("damage", 0);
            double variation = config.getDouble("variation", 0);

            // Create damage attribute
            return new DamageAttribute(name, slot, operation, stacking, damage, variation);
        }

        @Override
        public Damage loadFromNBT(String name, NBTTagCompound compound) {
            // Load slot, operation, stacking, damage, and variation
            Slot slot = Slot.valueOf(compound.getString("slot"));
            Operation operation = Operation.valueOf(compound.getString("operation"));
            boolean stacking = compound.getBoolean("stacking");
            double damage = compound.getDouble("amount");
            double variation = compound.getDouble("variation");

            // Create damage attribute
            return new DamageAttribute(name, slot, operation, stacking, damage, variation);
        }

    }

}
