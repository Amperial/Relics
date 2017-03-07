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
package com.herocraftonline.items.api.item.attribute.attributes.gems;

import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeContainer;

import java.util.Set;

/**
 * An attribute that can contain a gem.
 *
 * @author Austin Payne
 */
public interface Socket extends Attribute<Socket>, AttributeContainer {

    /**
     * Gets the color of the socket.
     *
     * @return the socket's color
     */
    SocketColor getColor();

    /**
     * Sets the color of the socket.
     *
     * @param color the color
     */
    void setColor(SocketColor color);

    /**
     * Gets the gem colors that the socket accepts.
     *
     * @return the colors accepted
     */
    Set<SocketColor> getAccepts();

    /**
     * Adds colors to the accepted gem colors.
     *
     * @param accepts the colors to add
     */
    void addAccepts(SocketColor... accepts);

    /**
     * Checks if a gem can be accepted by the socket.
     *
     * @param gem the gem
     * @return {@code true} if the gem can be accepted, else {@code false}
     */
    boolean acceptsGem(Gem gem);

    /**
     * Checks if the socket currently contains a gem.
     *
     * @return {@code true} if the socket has a gem, else {@code false}
     */
    boolean hasGem();

    /**
     * Gets the gem contained by the socket.
     *
     * @return the socket's gem
     */
    Gem getGem();

    /**
     * Sets the gem contained by the socket.
     *
     * @param gem the gem
     */
    void setGem(Gem gem);

}
