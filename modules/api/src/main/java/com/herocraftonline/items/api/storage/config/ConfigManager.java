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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;

/**
 * Contains custom plugin configs.
 *
 * @author Austin Payne
 */
public interface ConfigManager {

    /**
     * Adds a config accessor for each custom config.
     *
     * @param customConfigs the custom configs to register
     * @param plugin        the plugin containing the default resources for the configs
     * @param createEmpty   whether an empty config file should be generated if not found in plugin resources
     */
    void registerCustomConfigs(EnumSet<? extends Config> customConfigs, Plugin plugin, boolean createEmpty);

    /**
     * Adds a config accessor for a custom config.
     *
     * @param config      the custom config to register
     * @param plugin      the plugin containing the default resources for the config
     * @param createEmpty whether an empty config file should be generated if not found in plugin resources
     */
    void registerCustomConfig(Config config, Plugin plugin, boolean createEmpty);

    /**
     * Gets a certain config accessor.
     *
     * @param configType the type of the configuration file
     * @return the config accessor
     */
    ConfigAccessor getConfigAccessor(Config configType);

    /**
     * Gets a certain configuration file.
     *
     * @param configType the type of the configuration file
     * @return the configuration file
     */
    FileConfiguration getConfig(Config configType);

    /**
     * Gets a player's config accessor.
     *
     * @param player the player
     * @return the player's config accessor
     */
    ConfigAccessor getPlayerConfigAccessor(Player player);

    /**
     * Gets a player's configuration file.
     *
     * @param player the player
     * @return the player's configuration file
     */
    FileConfiguration getPlayerConfig(Player player);

    /**
     * Loads the configuration file at the specified path and passes it through the config transformers.
     *
     * @param fileName the file name of the configuration to transform
     * @param plugin   the plugin containing the default resources for the config
     * @param args     the args to be passed to the config transformers
     * @return the loaded and transformed configuration section
     */
    ConfigurationSection loadAndTransform(String fileName, Plugin plugin, Object... args);

    /**
     * Passes the configuration section through the config transformers.
     *
     * @param config the configuration section to transform
     * @param args   the args to be passed to the config transformers
     * @return the transformed configuration section
     */
    ConfigurationSection transformConfig(ConfigurationSection config, Object... args);

}
