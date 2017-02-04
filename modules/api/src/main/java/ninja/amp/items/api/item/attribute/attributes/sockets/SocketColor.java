/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
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
