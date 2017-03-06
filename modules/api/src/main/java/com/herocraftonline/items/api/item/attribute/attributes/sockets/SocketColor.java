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
package com.herocraftonline.items.api.item.attribute.attributes.sockets;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Possible colors of socket and gem attributes.<br>
 * These mirror the chat colors available in minecraft.
 *
 * @author Austin Payne
 */
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

    static {
        colorByName = new HashMap<>();
        for (SocketColor color : values()) {
            colorByName.put(color.getName(), color);
        }
    }

    private final String name;
    private final ChatColor color;
    private final Set<SocketColor> accepts;

    SocketColor(String name) {
        this.name = name;
        this.color = ChatColor.valueOf(name());
        this.accepts = new HashSet<>();
    }

    /**
     * Gets a socket color by its name.
     *
     * @param name the name
     * @return the socket color
     */
    public static SocketColor fromName(String name) {
        return colorByName.get(name);
    }

    /**
     * Gets a socket color by its associated chat color.
     *
     * @param color the chat color
     * @return the socket color
     */
    public static SocketColor fromChatColor(ChatColor color) {
        return valueOf(color.name());
    }

    /**
     * Gets the name of the socket color.
     *
     * @return the socket color's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the chat color of the socket color.
     *
     * @return the socket color's chat color
     */
    public ChatColor getChatColor() {
        return color;
    }

    /**
     * Gets the default colors of gem accepted by the socket color.
     *
     * @return the socket color's default accepts
     */
    public Set<SocketColor> getAccepts() {
        return accepts;
    }

    /**
     * Adds colors to the default accepted gem colors.
     *
     * @param color the colors to add
     */
    public void addAccepts(SocketColor color) {
        this.accepts.add(color);
    }

}
