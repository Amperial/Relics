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
package ninja.amp.items.api.item.attribute.attributes.sockets;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum SocketColor {
    AQUA("aqua"),
    BLACK("black"),
    BLUE("blue"),
    DARK_AQUA("dark-aqua"),
    DARK_BLUE("dark-blue"),
    DARK_GRAY("dark-gray"),
    DARK_GREEN("dark-green"),
    DARK_PURPLE("dark-purple"),
    DARK_RED("dark-red"),
    GOLD("gold"),
    GRAY("gray"),
    GREEN("green"),
    LIGHT_PURPLE("light-purple"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    private static final Map<String, SocketColor> colorByName;

    private final String name;
    private final ChatColor color;
    private final Set<SocketColor> accepts;

    SocketColor(String name) {
        this.name = name;
        this.color = ChatColor.valueOf(name());
        this.accepts = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public ChatColor getChatColor() {
        return color;
    }

    public Set<SocketColor> getAccepts() {
        return accepts;
    }

    public void addAccepts(SocketColor color) {
        this.accepts.add(color);
    }

    public static SocketColor fromName(String name) {
        return colorByName.get(name);
    }

    public static SocketColor fromChatColor(ChatColor color) {
        return valueOf(color.name());
    }

    static {
        colorByName = new HashMap<>();
        for (SocketColor color : values()) {
            colorByName.put(color.getName(), color);
        }
    }

}
