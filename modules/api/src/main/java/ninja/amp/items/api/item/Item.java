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
package ninja.amp.items.api.item;

import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeContainer;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface Item extends AttributeContainer, Equippable, Clickable {

    String getName();

    UUID getId();

    Material getMaterial();

    ItemType getType();

    ItemStack getItem();

    ItemStack updateItem(ItemStack item);

    void addAttribute(ItemAttribute... attributes);

    void removeAttribute(ItemAttribute attribute);

    void saveToNBT(NBTTagCompound compound);

}
