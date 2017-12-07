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
package com.herocraftonline.items.api.storage.value;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.api.storage.nbt.NBTNumber;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.api.storage.value.variables.VariableContainer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class StoredValue<T> {

    private final String key;
    private final Type<T> type;
    private final T def;
    private final boolean cache;
    private final boolean dynamic;

    public StoredValue(String key, Type<T> type, T def, boolean cache, boolean dynamic) {
        this.key = key;
        this.type = type;
        this.def = def;
        this.cache = cache;
        this.dynamic = dynamic;
    }

    public StoredValue(String key, Type<T> type, T def, boolean cache) {
        this(key, type, def, cache, type == STRING);
    }

    public StoredValue(String key, Type<T> type, boolean cache, boolean dynamic) {
        this(key, type, null, cache, dynamic);
    }

    public StoredValue(String key, Type<T> type, boolean cache) {
        this(key, type, null, cache);
    }

    public StoredValue(String key, Type<T> type, T def) {
        this(key, type, def, true);
    }

    public String getKey() {
        return key;
    }

    public T getDefault() {
        return def;
    }

    public Value<T> loadFromConfig(VariableContainer variables, ConfigurationSection config) {
        return loadValue(variables, config, this);
    }

    public Value<T> loadFromNBT(VariableContainer variables, NBTTagCompound compound) {
        return loadValue(variables, compound, this);
    }

    public static final Type<Boolean> BOOLEAN = new Type<>((o) -> o instanceof Boolean, (o) -> (Boolean) o, (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asByte() != 0, Boolean::parseBoolean);
    public static final Type<Double> DOUBLE = new Type<>((o) -> o instanceof Number, (o) -> ((Number) o).doubleValue(), (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asDouble(), Double::parseDouble);
    public static final Type<Integer> INTEGER = new Type<>((o) -> o instanceof Number, (o) -> ((Number) o).intValue(), (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asInt(), Integer::parseInt);
    public static final Type<Long> LONG = new Type<>((o) -> o instanceof Number, (o) -> ((Number) o).longValue(), (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asLong(), Long::parseLong);
    public static final Type<String> STRING = new Type<>((o) -> true, Object::toString, (b) -> b instanceof NBTTagString, (b) -> ((NBTTagString) b).getString(), Function.identity());

    private static final class Type<T> {
        private final Predicate<Object> configCheck;
        private final Function<Object, T> configValue;
        private final Predicate<NBTBase> nbtCheck;
        private final Function<NBTBase, T> nbtValue;
        private final Function<String, T> parse;

        public Type(Predicate<Object> configCheck, Function<Object, T> configValue, Predicate<NBTBase> nbtCheck, Function<NBTBase, T> nbtValue, Function<String, T> parse) {
            this.configCheck = configCheck;
            this.configValue = configValue;
            this.nbtCheck = nbtCheck;
            this.nbtValue = nbtValue;
            this.parse = parse;
        }
    }

    private static <S, V, T> Value<T> loadValue(VariableContainer variables, S storage, BiFunction<S, String, Boolean> contains, BiFunction<S, String, V> get, Function<V, String> toString, Predicate<V> valCheck, Function<V, T> value, Function<String, T> parse, String key, T def, boolean cache, boolean dynamic) {
        if (contains.apply(storage, key)) {
            V val = get.apply(storage, key);
            try {
                if (dynamic || !valCheck.test(val)) {
                    DynamicValue<T> dynamicValue = new DynamicValue<>(variables, key, toString.apply(val), parse, def, cache);
                    variables.addDynamicValue(dynamicValue);
                    return dynamicValue;
                } else {
                    return new StaticValue<>(key, value.apply(val));
                }
            } catch (Exception ignored) {
            }
        }
        return new StaticValue<>(def);
    }

    private static final BiFunction<ConfigurationSection, String, Boolean> CONFIG_CONTAINS = ConfigurationSection::contains;
    private static final BiFunction<ConfigurationSection, String, Object> CONFIG_GET = ConfigurationSection::get;
    private static final Function<Object, String> CONFIG_STRING = Object::toString;

    private static <T> Value<T> loadValue(VariableContainer variables, ConfigurationSection config, StoredValue<T> value) {
        return loadValue(variables, config, CONFIG_CONTAINS, CONFIG_GET, CONFIG_STRING, value.type.configCheck, value.type.configValue, value.type.parse, value.key, value.def, value.cache, value.dynamic);
    }

    private static final BiFunction<NBTTagCompound, String, Boolean> NBT_CONTAINS = NBTTagCompound::hasKey;
    private static final BiFunction<NBTTagCompound, String, NBTBase> NBT_GET = NBTTagCompound::getBase;
    private static final Function<NBTBase, String> NBT_STRING = (b) -> ((NBTTagString) b).getString();

    private static <T> Value<T> loadValue(VariableContainer variables, NBTTagCompound compound, StoredValue<T> value) {
        return loadValue(variables, compound, NBT_CONTAINS, NBT_GET, NBT_STRING, value.type.nbtCheck, value.type.nbtValue, value.type.parse, value.key, value.def, value.cache, value.dynamic);
    }

}
