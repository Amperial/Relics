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
package com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.CommandSenderSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;

/**
 * A permission trigger that can only trigger if the source has permission.
 *
 * @author Austin Payne
 */
public interface Permission extends ConditionTrigger<Permission> {

    /**
     * Gets the permission required to trigger the condition trigger.
     *
     * @return the bukkit permission
     */
    org.bukkit.permissions.Permission getPermission();

    @Override
    default boolean test(TriggerSource source) {
        return source.ofType(CommandSenderSource.class)
                .map(commandSenderSource -> commandSenderSource.getSender()
                .hasPermission(getPermission())).orElse(false);
    }

}
