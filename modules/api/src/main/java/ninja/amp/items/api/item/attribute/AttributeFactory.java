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

public interface AttributeFactory<T extends ItemAttribute> {

    T loadFromConfig(String name, ConfigurationSection config);

    T loadFromNBT(String name, NBTTagCompound compound);

}
