/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.item.attribute.attributes;

import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.ItemLore;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("name", name);
        compound.setString("type", type.getName());
    }

    public void setLore(ItemLore lore) {
        this.lore = lore;
    }

    @Override
    public boolean canEquip(Player player) {
        return true;
    }

    @Override
    public void equip(Player player) {
    }

    @Override
    public void unEquip(Player player) {
    }

}
