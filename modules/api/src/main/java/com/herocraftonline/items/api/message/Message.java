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
package com.herocraftonline.items.api.message;

/**
 * An interface for an enum of the messages in a plugin.
 *
 * @author Austin Payne
 */
public interface Message {

    /**
     * Gets the message string.
     *
     * @return the message
     */
    String getMessage();

    /**
     * Sets the message string.
     *
     * @param message the message
     */
    void setMessage(String message);

    /**
     * Gets the path to the message.
     *
     * @return the path to the message
     */
    String getPath();

    /**
     * Gets the default message string of the message.
     *
     * @return the default message
     */
    String getDefault();

    @Override
    String toString();

}
