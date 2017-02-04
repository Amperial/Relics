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
package ninja.amp.items.api.config;

/**
 * An interface for a custom config file in a plugin.
 *
 * @author Austin Payne
 */
public interface Config {

    /**
     * Gets the config's file name.
     *
     * @return The file name of the config
     */
    String getFileName();

}
