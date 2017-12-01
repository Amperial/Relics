/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.variable;

import java.util.Collection;

public interface VariableContainer {

    boolean hasVariable(String name);

    <T> Variable<T> getVariable(String name, Class<T> type);

    Collection<String> getVariables();

    <T> T getValue(String name, Class<T> type);

    <T> void setValue(String name, T value);

}
