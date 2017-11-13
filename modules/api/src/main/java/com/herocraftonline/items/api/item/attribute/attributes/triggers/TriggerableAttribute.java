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
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TriggerableAttribute<T extends Attribute<T>> extends BaseAttribute<T> implements Triggerable {

    public TriggerableAttribute(String name, AttributeType<T> type) {
        super(name, type);
    }

    @Override
    public TriggerResult execute() {
        return TriggerResult.NONE;
    }

    @Override
    public TriggerResult execute(Location location) {
        return execute();
    }

    @Override
    public TriggerResult execute(Entity entity) {
        return execute(entity.getLocation());
    }

    @Override
    public TriggerResult execute(Entity entity, Entity target) {
        return execute(target);
    }

}
