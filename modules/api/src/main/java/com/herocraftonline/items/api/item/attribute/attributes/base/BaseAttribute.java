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
package com.herocraftonline.items.api.item.attribute.attributes.base;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeLore;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.ChatColor;

/**
 * A base attribute implementation to simplify the creation of attributes.<br>
 * Handles the item attribute name and type, as well as setting the default item lore.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public class BaseAttribute<T extends Attribute<T>> implements Attribute<T> {

    private final Item item;
    private final String name;
    private final AttributeType<T> type;
    private AttributeLore lore;

    public BaseAttribute(Item item, String name, AttributeType<T> type) {
        this.item = item;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.type = type;
        this.lore = AttributeLore.NONE;
    }

    protected Item getItem() {
        return item;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AttributeType<T> getType() {
        return type;
    }

    @Override
    public AttributeLore getLore() {
        return lore;
    }

    protected void setLore(AttributeLore lore) {
        this.lore = lore;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", type.getName());
    }

}
