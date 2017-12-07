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
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.api.storage.value.variables.VariableContainer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public StoredValue(String key, Type<T> type) {
        this(key, type, null);
    }

    public String getKey() {
        return key;
    }

    public T getDefault() {
        return def;
    }

    @SuppressWarnings("unchecked")
    public Value<T> loadFromConfig(VariableContainer variables, ConfigurationSection config) {
        if (type instanceof ListType) {
            StoredValue<List<Object>> value = (StoredValue<List<Object>>) this;
            return (Value<T>) loadList(variables, config, value);
        } else {
            return loadValue(variables, config, this);
        }
    }

    @SuppressWarnings("unchecked")
    public Value<T> loadFromNBT(VariableContainer variables, NBTTagCompound compound) {
        if (type instanceof ListType) {
            StoredValue<List<Object>> value = (StoredValue<List<Object>>) this;
            return (Value<T>) loadList(variables, compound, value);
        } else {
            return loadValue(variables, compound, this);
        }
    }

    public static final ValueType<Boolean> BOOLEAN = new ValueType<>(Boolean::parseBoolean,
            (o) -> o instanceof Boolean, (o) -> (Boolean) o,
            (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asByte() != 0);
    public static final ValueType<Double> DOUBLE = new ValueType<>(Double::parseDouble,
            (o) -> o instanceof Number, (o) -> ((Number) o).doubleValue(),
            (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asDouble());
    public static final ValueType<Integer> INTEGER = new ValueType<>(Integer::parseInt,
            (o) -> o instanceof Number, (o) -> ((Number) o).intValue(),
            (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asInt());
    public static final ValueType<Long> LONG = new ValueType<>(Long::parseLong,
            (o) -> o instanceof Number, (o) -> ((Number) o).longValue(),
            (b) -> b instanceof NBTNumber, (b) -> ((NBTNumber) b).asLong());
    public static final ValueType<String> STRING = new ValueType<>(Function.identity(),
            (o) -> true, Object::toString, (b) -> b instanceof NBTTagString,
            (b) -> ((NBTTagString) b).getString());
    public static final ListType<String> STRING_LIST = new ListType<>(Function.identity(),
            (o) -> o instanceof List, (o) -> (List<String>) ((List<?>) o).stream().map(String::valueOf).collect(Collectors.toList()),
            (b) -> b instanceof NBTTagList, (b) -> {
        List<String> text = new ArrayList<>();
        NBTTagList list = (NBTTagList) b;
        for (int i = 0; i < list.size(); i++) {
            text.add(list.getString(i));
        }
        return text;
    });

    private static class Type<T> {
        protected final Predicate<Object> configCheck;
        protected final Predicate<NBTBase> nbtCheck;

        public Type(Predicate<Object> configCheck, Predicate<NBTBase> nbtCheck) {
            this.configCheck = configCheck;
            this.nbtCheck = nbtCheck;
        }
    }

    private static class ValueType<T> extends Type<T> {
        protected final Function<String, T> parse;
        protected final Function<Object, T> configValue;
        protected final Function<NBTBase, T> nbtValue;

        public ValueType(Function<String, T> parse, Predicate<Object> configCheck, Function<Object, T> configValue, Predicate<NBTBase> nbtCheck, Function<NBTBase, T> nbtValue) {
            super(configCheck, nbtCheck);
            this.parse = parse;
            this.configValue = configValue;
            this.nbtValue = nbtValue;
        }
    }

    private static class ListType<T> extends Type<List<T>> {
        protected final Function<String, T> parse;
        protected final Function<Object, List<T>> configValue;
        protected final Function<NBTBase, List<T>> nbtValue;

        public ListType(Function<String, T> parse, Predicate<Object> configCheck, Function<Object, List<T>> configValue, Predicate<NBTBase> nbtCheck, Function<NBTBase, List<T>> nbtValue) {
            super(configCheck, nbtCheck);
            this.parse = parse;
            this.configValue = configValue;
            this.nbtValue = nbtValue;
        }
    }

    private static <S, V, T> Value<T> loadValue(VariableContainer variables, S storage, BiFunction<S, String, Boolean> contains, BiFunction<S, String, V> get, Function<V, String> toString, Predicate<V> valCheck, Function<V, T> value, Function<String, T> parse, String key, T def, boolean cache, boolean dynamic) {
        if (contains.apply(storage, key)) {
            V val = get.apply(storage, key);
            try {
                if (dynamic || !valCheck.test(val)) {
                    Value<T> dynamicValue = new DynamicValue<>(variables, key, toString.apply(val), parse, def, cache);
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

    private static <S, V, T> Value<List<T>> loadList(VariableContainer variables, S storage, BiFunction<S, String, Boolean> contains, BiFunction<S, String, V> get, Function<V, List<String>> toList, Predicate<V> valCheck, Function<V, List<T>> value, Function<String, T> parse, String key, List<T> def, boolean cache, boolean dynamic) {
        if (contains.apply(storage, key)) {
            V val = get.apply(storage, key);
            try {
                if (dynamic || !valCheck.test(val)) {
                    Value<List<T>> dynamicList = new DynamicList<>(variables, key, toList.apply(val), parse, def, cache);
                    variables.addDynamicValue(dynamicList);
                    return dynamicList;
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
    private static final Function<Object, List<String>> CONFIG_STRING_LIST = STRING_LIST.configValue;

    private static <T> Value<T> loadValue(VariableContainer variables, ConfigurationSection config, StoredValue<T> value) {
        if (value.type instanceof ValueType) {
            ValueType<T> type = (ValueType<T>) value.type;
            return loadValue(variables, config, CONFIG_CONTAINS, CONFIG_GET, CONFIG_STRING, type.configCheck, type.configValue, type.parse, value.key, value.def, value.cache, value.dynamic);
        }
        return new StaticValue<>(value.def);
    }

    private static <T> Value<List<T>> loadList(VariableContainer variables, ConfigurationSection config, StoredValue<List<T>> value) {
        if (value.type instanceof ListType) {
            ListType<T> type = (ListType<T>) value.type;
            return loadList(variables, config, CONFIG_CONTAINS, CONFIG_GET, CONFIG_STRING_LIST, type.configCheck, type.configValue, type.parse, value.key, value.def, value.cache, value.dynamic);
        }
        return new StaticValue<>(value.def);
    }

    private static final BiFunction<NBTTagCompound, String, Boolean> NBT_CONTAINS = NBTTagCompound::hasKey;
    private static final BiFunction<NBTTagCompound, String, NBTBase> NBT_GET = NBTTagCompound::getBase;
    private static final Function<NBTBase, String> NBT_STRING = (b) -> ((NBTTagString) b).getString();
    private static final Function<NBTBase, List<String>> NBT_STRING_LIST = STRING_LIST.nbtValue;

    private static <T> Value<T> loadValue(VariableContainer variables, NBTTagCompound compound, StoredValue<T> value) {
        if (value.type instanceof ValueType) {
            ValueType<T> type = (ValueType<T>) value.type;
            return loadValue(variables, compound, NBT_CONTAINS, NBT_GET, NBT_STRING, type.nbtCheck, type.nbtValue, type.parse, value.key, value.def, value.cache, value.dynamic);
        }
        return new StaticValue<>(value.def);
    }

    private static <T> Value<List<T>> loadList(VariableContainer variables, NBTTagCompound compound, StoredValue<List<T>> value) {
        if (value.type instanceof ListType) {
            ListType<T> type = (ListType<T>) value.type;
            return loadList(variables, compound, NBT_CONTAINS, NBT_GET, NBT_STRING_LIST, type.nbtCheck, type.nbtValue, type.parse, value.key, value.def, value.cache, value.dynamic);
        }
        return new StaticValue<>(value.def);
    }

}
