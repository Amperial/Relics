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
package com.herocraftonline.items.item.attributes.triggers.sources.entity;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.item.attributes.triggers.sources.CommandSenderSource;
import org.bukkit.entity.Entity;

public class EntitySource extends CommandSenderSource implements com.herocraftonline.items.api.item.attribute.attributes.triggers.source.entity.EntitySource {

    public EntitySource(Item item, Entity entity) {
        super(item, entity);
    }

    @Override
    public Entity getEntity() {
        return (Entity) getSender();
    }

}
