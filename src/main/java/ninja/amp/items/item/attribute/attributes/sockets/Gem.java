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
package ninja.amp.items.item.attribute.attributes.sockets;

import ninja.amp.items.item.attribute.ItemAttribute;
import org.bukkit.ChatColor;

public class Gem {

    private final String name;
    private final String displayName;
    private final SocketColor color;
    private ItemAttribute attribute;

    public Gem(String name, String displayName, SocketColor color) {
        this.name = name;
        // TODO: Configurable
        this.displayName = color.getChatColor() + "(" + ChatColor.GRAY + displayName + color.getChatColor() + ")";
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SocketColor getColor() {
        return color;
    }

    public boolean hasAttribute() {
        return attribute != null;
    }

    public ItemAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ItemAttribute attribute) {
        this.attribute = attribute;
    }

}
