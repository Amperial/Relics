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
package com.herocraftonline.items.api.config;

/**
 * An interface for a custom config file in a plugin.
 *
 * @author Austin Payne
 */
public interface Config {

    /**
     * Gets the config's file name.
     *
     * @return the file name of the config
     */
    String getFileName();

}
