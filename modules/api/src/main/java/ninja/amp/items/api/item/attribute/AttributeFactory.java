/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item.attribute;

import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Creates item attribute instances from config and nbt.
 *
 * @param <T> the item attribute that this factory creates
 * @author Austin Payne
 */
public interface AttributeFactory<T extends ItemAttribute> {

    /**
     * Loads an item attribute with the given name from a configuration section.
     *
     * @param name   the item attribute's name
     * @param config the configuration section
     * @return the item attribute
     */
    T loadFromConfig(String name, ConfigurationSection config);

    /**
     * Loads an item attribute with the given name from an nbt tag compound.
     *
     * @param name     the item attribute's name
     * @param compound the tag compound
     * @return the item attribute
     */
    T loadFromNBT(String name, NBTTagCompound compound);

}
