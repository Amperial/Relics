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
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.item.attribute.AttributeFactory;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;

public enum DefaultAttributeType implements AttributeType {
    GEM("gem", 3),
    GROUP("group", 4),
    MODEL("model", Integer.MAX_VALUE),
    RARITY("rarity", 0),
    SOCKET("socket", 2),
    TEXT("text", 1);

    private final String name;
    private final String fileName;
    private int lorePosition;
    private AttributeFactory<? extends ItemAttribute> factory;

    DefaultAttributeType(String name, int lorePosition) {
        this.name = name;
        this.fileName = "attributes/" + name + ".yml";
        this.lorePosition = lorePosition;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getLorePosition() {
        return lorePosition;
    }

    @Override
    public void setLorePosition(int position) {
        this.lorePosition = position;
    }

    @Override
    public AttributeFactory<? extends ItemAttribute> getFactory() {
        return factory;
    }

    public void setFactory(AttributeFactory<? extends ItemAttribute> factory) {
        this.factory = factory;
    }

}
