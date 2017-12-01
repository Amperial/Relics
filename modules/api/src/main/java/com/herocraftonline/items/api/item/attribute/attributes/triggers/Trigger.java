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

import java.util.List;

/**
 * An triggerable attribute that is able to trigger other triggerable attributes.
 *
 * @author Austin Payne
 */
public interface Trigger<T extends Triggerable<T>> extends Triggerable<T> {

    /**
     * Get the targets of the trigger, defined by their attribute names.
     *
     * @return the trigger's target triggerable attribute names
     */
    List<String> getTargets();

    /**
     * Checks if the trigger's targets are considered separate or not when triggering.
     *
     * @return {@code true} if the trigger handles targets separately, else {@code false}
     */
    boolean handleSeparately();

}
