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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;
import com.herocraftonline.items.api.item.attribute.ItemLore;
import com.herocraftonline.items.nms.nbt.NBTTagCompound;
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
