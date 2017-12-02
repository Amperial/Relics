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
package com.herocraftonline.items.api.item.attribute.attributes.triggers;

import org.bukkit.event.block.Action;

import java.util.Set;

/**
 * A trigger attribute triggered by player interact events.
 *
 * @author Austin Payne
 */
public interface PlayerInteract extends Trigger<PlayerInteract> {

    /**
     * The interact actions that this player interact trigger can be triggered by.
     *
     * @return the player interact actions
     */
    Set<Action> getActions();

}
