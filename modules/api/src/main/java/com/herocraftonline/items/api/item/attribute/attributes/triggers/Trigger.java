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
package com.herocraftonline.items.api.item.attribute.attributes.triggers;

import com.herocraftonline.items.api.item.attribute.Attribute;

import java.util.Set;

public interface Trigger<T extends Trigger<T>> extends Attribute<T> {

    Set<Triggerable> getTriggerables();

}
