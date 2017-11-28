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
package com.herocraftonline.items.api.storage.config;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Used to access, reload, and save a YamlConfiguration file.
 *
 * @author Austin Payne
 */
public interface ConfigAccessor {

    /**
     * Checks if the configuration file exists.
     *
     * @return {@code true} if the config exists, else {@code false}
     */
    boolean exists();

    /**
     * Reloads the configuration file from disk.
     *
     * @return the config accessor
     */
    ConfigAccessor reloadConfig();

    /**
     * Gets the config.
     *
     * @return the configuration file
     */
    FileConfiguration getConfig();

    /**
     * Saves the config to disk.
     *
     * @return the config accessor
     */
    ConfigAccessor saveConfig();

    /**
     * Generates the default config if it hasn't already been generated.
     *
     * @param createEmpty whether an empty config file should be generated if not found in plugin resources
     * @return the config accessor
     */
    ConfigAccessor saveDefaultConfig(boolean createEmpty);

    /**
     * Gets the config type.
     *
     * @return the type of the configuration file
     */
    Config getConfigType();

}
