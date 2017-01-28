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

public class BasicAttribute implements ItemAttribute {

    private AttributeType type;
    private ItemLore lore;

    public BasicAttribute(AttributeType type) {
        this.type = type;
        this.lore = ItemLore.NONE;
    }

    @Override
    public AttributeType getType() {
        return type;
    }

    @Override
    public ItemLore getLore() {
        return lore;
    }

    public void setLore(ItemLore lore) {
        this.lore = lore;
    }

}
