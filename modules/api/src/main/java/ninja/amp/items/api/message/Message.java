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
package ninja.amp.items.api.message;

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
