/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.api.item.attribute;

import ninja.amp.items.api.equipment.Equip;
import ninja.amp.items.nms.nbt.NBTTagCompound;

import java.util.function.Predicate;

public interface ItemAttribute extends Equip {

    String getName();

    AttributeType getType();

    ItemLore getLore();

    void saveToNBT(NBTTagCompound compound);

    static Predicate<ItemAttribute> type(Class<?> clazz) {
        return attribute -> clazz.isAssignableFrom(attribute.getClass());
    }

}
