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
package ninja.amp.items.item;

import ninja.amp.items.api.item.ItemType;

public enum WeaponType implements ItemType {
    AXE("weapon-axe"),
    BOW("weapon-bow"),
    HOE("weapon-hoe"),
    PICKAXE("weapon-pickaxe"),
    SHOVEL("weapon-shovel"),
    SWORD("weapon-sword");

    private final String name;

    WeaponType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}