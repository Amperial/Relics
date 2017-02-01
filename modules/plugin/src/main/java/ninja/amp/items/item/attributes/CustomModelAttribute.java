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
import ninja.amp.items.api.item.attribute.attributes.CustomModel;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

public class CustomModelAttribute extends BasicAttribute implements CustomModel {

    private short modelDamage;

    public CustomModelAttribute(String name, short modelDamage) {
        super(name, DefaultAttributeType.MODEL);

        this.modelDamage = modelDamage;
    }

    @Override
    public short getModelDamage() {
        return modelDamage;
    }

    @Override
    public void setModelDamage(short modelDamage) {
        this.modelDamage = modelDamage;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setShort("model-damage", getModelDamage());
    }

    public static class Factory extends BasicAttributeFactory<CustomModel> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public CustomModel loadFromConfig(ConfigurationSection config) {
            // Load name and model damage
            String name = config.getName();
            short modelDamage = (short) config.getInt("model-damage");

            // Create custom model attribute
            return new CustomModelAttribute(name, modelDamage);
        }

        @Override
        public CustomModel loadFromNBT(NBTTagCompound compound) {
            // Load name and model damage
            String name = compound.getString("name");
            short modelDamage = compound.getShort("model-damage");

            // Create custom model attribute
            return new CustomModelAttribute(name, modelDamage);
        }

    }

}
