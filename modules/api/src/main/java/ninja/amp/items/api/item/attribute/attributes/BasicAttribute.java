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
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.ItemLore;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;

/**
 * A basic item attribute implementation to simplify the creation of item attributes.<br>
 * Handles the item attribute name and type, as well as setting the default item lore.
 *
 * @author Austin Payne
 */
public class BasicAttribute implements ItemAttribute {

    private final String name;
    private final AttributeType type;
    private ItemLore lore;

    public BasicAttribute(String name, AttributeType type) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.type = type;
        this.lore = ItemLore.NONE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AttributeType getType() {
        return type;
    }

    @Override
    public ItemLore getLore() {
        return lore;
    }

    protected void setLore(ItemLore lore) {
        this.lore = lore;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", type.getName());
    }

}
