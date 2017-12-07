/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.IncreaseVariable;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

public class IncreaseVariableAttribute extends BaseAttribute<IncreaseVariable> implements IncreaseVariable {

    private final Value<String> variable;
    private final Value<Integer> amount;

    public IncreaseVariableAttribute(Item item, String name, Value<String> variable, Value<Integer> amount) {
        super(item, name, DefaultAttributes.INCREASE_VARIABLE);

        this.variable = variable;
        this.amount = amount;
    }

    @Override
    public String getVariable() {
        return variable.getValue();
    }

    @Override
    public int getAmount() {
        return amount.getValue();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        variable.saveToNBT(compound);
        amount.saveToNBT(compound);
    }

    public static class Factory extends BaseAttributeFactory<IncreaseVariable> {
        private static final StoredValue<String> VARIABLE = new StoredValue<>("variable", StoredValue.STRING, "");
        private static final StoredValue<Integer> AMOUNT = new StoredValue<>("amount", StoredValue.INTEGER, 0);

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public IncreaseVariable loadFromConfig(Item item, String name, ConfigurationSection config) {
            Value<String> variable = VARIABLE.loadFromConfig(item, config);
            Value<Integer> amount = AMOUNT.loadFromConfig(item, config);

            return new IncreaseVariableAttribute(item, name, variable, amount);
        }

        @Override
        public IncreaseVariable loadFromNBT(Item item, String name, NBTTagCompound compound) {
            Value<String> variable = VARIABLE.loadFromNBT(item, compound);
            Value<Integer> amount = AMOUNT.loadFromNBT(item, compound);

            return new IncreaseVariableAttribute(item, name, variable, amount);
        }
    }

}
