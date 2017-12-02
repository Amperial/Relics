/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.gems;

import org.bukkit.ChatColor;

import java.util.Collection;
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
    AQUA,
    BLACK,
    BLUE,
    DARK_AQUA,
    DARK_BLUE,
    DARK_GRAY,
    DARK_GREEN,
    DARK_PURPLE,
    DARK_RED,
    GOLD,
    GRAY,
    GREEN,
    LIGHT_PURPLE,
    RED,
    WHITE,
    YELLOW;

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

    SocketColor() {
        this.name = name().toLowerCase().replace('_', '-');
        this.color = ChatColor.valueOf(name());
        this.accepts = new HashSet<>();
        this.accepts.add(this);
    }

    /**
     * Gets a socket color by its name or enum value.
     *
     * @param name the name
     * @return the socket color
     */
    public static SocketColor fromName(String name) {
        SocketColor color = colorByName.get(name);
        return color == null ? valueOf(name) : color;
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
     * Gets the default colors of gem accepted by the socket color.<br>
     * A socket color will by default only accept itself.<br>
     * Individual socket attributes can ignore this and define their own accepted colors or rules.
     *
     * @return the socket color's default accepted colors
     */
    public Set<SocketColor> getAcceptedColors() {
        return accepts;
    }

    /**
     * Sets the default colors of gem accepted by the socket color.<br>
     * See {@link this#getAcceptedColors()}
     *
     * @param accepts the accepted socket colors
     */
    public void setAcceptedColors(Collection<SocketColor> accepts) {
        this.accepts.clear();
        this.accepts.addAll(accepts);
    }

}
