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
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;

import java.util.Collection;
import java.util.Map;

public interface AttributeGroup extends ItemAttribute {

    boolean hasAttribute(String name, boolean deep);

    boolean hasAttribute(AttributeType type, boolean deep);

    ItemAttribute getAttribute(String name, boolean deep);

    Collection<? extends ItemAttribute> getAttributes(AttributeType type, boolean deep);

    Collection<? extends ItemAttribute> getAttributes();

    Map<String, ? extends ItemAttribute> getAttributesByName();

    void addAttribute(ItemAttribute... attributes);

}
