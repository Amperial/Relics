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

import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.api.storage.value.replacer.Replaceable;
import com.herocraftonline.items.api.storage.value.replacer.VariableReplaceable;
import com.herocraftonline.items.api.storage.value.variables.VariableContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DynamicList<T> implements Value<List<T>> {

    private final String key;
    private final List<String> value;
    private final List<T> def;

    private final Replaceable replaceable;
    private final Function<String, T> parse;
    private final boolean cache;
    private List<T> last;

    public DynamicList(VariableContainer variables, String key, List<String> value, Function<String, T> parse, List<T> def, boolean cache) {
        this.key = key;
        this.value = value;
        this.def = def;

        this.replaceable = new VariableReplaceable(variables);
        this.parse = parse;
        this.cache = cache;
        this.last = null;
    }

    @Override
    public List<T> getValue() {
        if (last == null || !cache) {
            try {
                List<T> list = new ArrayList<>();
                for (String string : value) {
                    list.add(parse.apply(replaceable.replace(string)));
                }
                last = list;
            } catch (Exception e) {
                last = def;
            }
        }
        return last;
    }

    @Override
    public void reset() {
        this.last = null;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        if (key != null) {
            NBTTagList list = NBTTagList.create();
            for (String string : value) {
                list.addBase(NBTTagString.create(string));
            }
            compound.setBase(key, list);
        }
    }

}
