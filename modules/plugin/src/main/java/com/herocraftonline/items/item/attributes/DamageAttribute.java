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
import com.herocraftonline.items.api.item.attribute.attributes.Damage;
import com.herocraftonline.items.api.item.attribute.attributes.Minecraft;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatSpecifier;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatTotal;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatType;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class DamageAttribute extends MinecraftAttribute implements Damage {

    private static final StatType<Minecraft> STAT_TYPE = new DamageStatType();

    /**
     * Handles totalling the damage attributes of an item and adding them to the item's lore.
     */
    protected static class DamageStatType extends MinecraftStatType {
        @Override
        public StatTotal<Minecraft> newTotal(StatSpecifier<Minecraft> specifier) {
            return new StatTotal<Minecraft>(specifier) {
                double damage = 0;
                double variation = 0;

                @Override
                public void addStat(Minecraft stat) {
                    damage += ((Damage) stat).getDamage();
                    variation += ((Damage) stat).getVariation();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    MinecraftStatSpecifier specifier = (MinecraftStatSpecifier) getStatSpecifier();
                    if (variation > 0) {
                        double lower = damage - variation;
                        double higher = damage + variation;
                        lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, lower, higher));
                    } else {
                        lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, damage));
                    }
                }

                @Override
                public boolean shouldAddLore() {
                    return damage > 0 || damage < 0 || variation > 0;
                }
            };
        }

        public String formatModifier(Operation operation, Type type, boolean stacking, double lower, double higher) {
            return operation.prefix(lower, stacking) + operation.amount(lower) + "-" + operation.amount(higher) + operation.suffix() + " " + type.getDisplayName();
        }
    }

    private double variation;

    public DamageAttribute(String name, Slot slot, Operation operation, boolean stacking, double damage, double variation) {
        super(name, DefaultAttribute.DAMAGE, STAT_TYPE, Type.ATTACK_DAMAGE, slot, operation, stacking, damage);

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

    public static class Factory extends BaseAttributeFactory<Minecraft> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Damage loadFromConfig(String name, ConfigurationSection config) {
            // Load slot, operation, stacking, damage, and variation
            Slot slot = Slot.valueOf(config.getString("slot", "ANY"));
            Operation operation = Operation.valueOf(config.getString("operation", "ADD_NUMBER"));
            boolean stacking = config.getBoolean("stacking", true);
            double damage = Math.max(config.getDouble("damage", 0), 0);
            double variation = Math.abs(config.getDouble("variation", 0));

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
