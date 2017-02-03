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
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.Damage;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

public class DamageAttribute extends BasicAttribute implements Damage {

    private int damage;
    private int variation;

    public DamageAttribute(String name, int damage, int variation) {
        super(name, DefaultAttributeType.DAMAGE);
        this.damage = damage;
        this.variation = variation;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getVariation() {
        return variation;
    }

    @Override
    public void setVariation(int variation) {
        this.variation = variation;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("damage", getDamage());
        compound.setInt("variation", getVariation());
    }

    public static class DamageFactory extends BasicAttributeFactory<Damage> {

        public DamageFactory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Damage loadFromConfig(String name, ConfigurationSection config) {
            // Load damage and variation
            int damage = config.getInt("damage");
            int variation = config.getInt("variation");

            // Create damage attribute
            return new DamageAttribute(name, damage, variation);
        }

        @Override
        public Damage loadFromNBT(String name, NBTTagCompound compound) {
            // Load damage
            int damage = compound.getInt("damage");
            int variation = compound.getInt("variation");

            // Create damage attribute
            return new DamageAttribute(name, damage, variation);
        }

    }

}
