/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.storage.value.variables;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagDouble;
import com.herocraftonline.items.api.storage.nbt.NBTTagInt;
import com.herocraftonline.items.api.storage.value.Value;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseVariableContainer implements VariableContainer {

    private static final String VARIABLES_TAG = "variables";

    private final Map<String, Object> variables;
    private final List<Value> values;

    public BaseVariableContainer() {
        this.variables = new HashMap<>();
        this.values = new ArrayList<>();
    }

    @Override
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    @Override
    public Optional<Object> getValue(String name) {
        return Optional.ofNullable(variables.get(name));
    }

    @Override
    public void setValue(String name, Object value) {
        variables.put(name, value);
        resetDynamicValues();
    }

    @Override
    public void addDynamicValue(Value value) {
        values.add(value);
    }

    @Override
    public void resetDynamicValues() {
        values.forEach(Value::reset);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        NBTTagCompound variableCompound = NBTTagCompound.create();
        for (Map.Entry<String, Object> variable : variables.entrySet()) {
            Object value = variable.getValue();
            // TODO: Allow for more variable types in the future, handle loading better
            if (value instanceof Integer) {
                variableCompound.setInt(variable.getKey(), (Integer) value);
            } else if (value instanceof Double) {
                variableCompound.setDouble(variable.getKey(), (Double) value);
            }
        }
        compound.setBase(VARIABLES_TAG, variableCompound);
    }

    public static VariableContainer loadFromNBT(NBTTagCompound compound) {
        VariableContainer variables = new BaseVariableContainer();
        NBTTagCompound variableCompound = compound.getCompound(VARIABLES_TAG);
        for (String variable : variableCompound.getKeySet()) {
            // TODO: Allow for more variable types in the future, handle loading better
            NBTBase base = variableCompound.getBase(variable);
            if (base instanceof NBTTagInt) {
                Integer var = variableCompound.getInt(variable);
                variables.setValue(variable, var);
            } else if (base instanceof NBTTagDouble) {
                Double var = variableCompound.getDouble(variable);
                variables.setValue(variable, var);
            }
        }
        return variables;
    }

    public static VariableContainer loadFromConfig(ConfigurationSection config) {
        VariableContainer variables = new BaseVariableContainer();
        if (config.isConfigurationSection(VARIABLES_TAG)) {
            ConfigurationSection variableConfig = config.getConfigurationSection(VARIABLES_TAG);
            for (String variable : variableConfig.getKeys(false)) {
                // TODO: Allow for more variable types in the future, handle loading better
                if (variableConfig.isInt(variable)) {
                    variables.setValue(variable, variableConfig.getInt(variable));
                } else if (variableConfig.isDouble(variable)) {
                    variables.setValue(variable, variableConfig.getDouble(variable));
                }
            }
        }
        return variables;
    }

}
