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
package com.herocraftonline.items.api.storage.config;

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
