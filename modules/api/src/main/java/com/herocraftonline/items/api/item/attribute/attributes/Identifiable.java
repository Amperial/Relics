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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.attributes.triggers.Triggerable;
import org.bukkit.inventory.ItemStack;

/**
 * An attribute that holds an encrypted item that can be identified.
 *
 * @author Austin Payne
 */
public interface Identifiable extends Triggerable<Identifiable> {

    /**
     * Decrypts and deserializes the item stack held in the identifiable attribute.
     *
     * @return the identified item stack
     */
    ItemStack identifyItem();

}
