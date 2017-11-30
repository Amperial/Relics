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
package com.herocraftonline.items.api.item.attribute;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Creates attribute instances from config and nbt.
 *
 * @param <T> the type of attribute created by the factory
 * @author Austin Payne
 */
public interface AttributeFactory<T extends Attribute<T>> {

    /**
     * Loads an item attribute with the given name from a configuration section.
     *
     * @param name   the item attribute's name
     * @param config the configuration section
     * @return the item attribute
     */
    T loadFromConfig(Item item, String name, ConfigurationSection config);

    /**
     * Loads an item attribute with the given name from an nbt tag compound.
     *
     * @param name     the item attribute's name
     * @param compound the tag compound
     * @return the item attribute
     */
    T loadFromNBT(Item item, String name, NBTTagCompound compound);

}
