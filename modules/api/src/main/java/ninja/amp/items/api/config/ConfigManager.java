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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains custom plugin configs.
 *
 * @author Austin Payne
 */
public class ConfigManager {

    private static int NESTING_DEPTH;

    private final Map<Config, ConfigAccessor> configs = new HashMap<>();

    /**
     * Creates a new config manager.
     *
     * @param plugin The item plugin instance
     */
    public ConfigManager(Plugin plugin) {
        // Save main config
        plugin.saveDefaultConfig();

        // Load nesting depth
        NESTING_DEPTH = plugin.getConfig().getInt("config.nesting-depth", 0);

        // Register custom configs
        registerCustomConfigs(EnumSet.allOf(DefaultConfig.class), plugin);
    }

    /**
     * Adds a config accessor for each custom config.
     *
     * @param customConfigs The custom configs to register
     * @param plugin        The plugin that owns the configs
     */
    public void registerCustomConfigs(EnumSet<? extends Config> customConfigs, Plugin plugin) {
        customConfigs.forEach(config -> registerCustomConfig(config, plugin));
    }

    /**
     * Adds a config accessor for a custom config.
     *
     * @param config The custom config to register
     * @param plugin The plugin that owns the config
     */
    public void registerCustomConfig(Config config, Plugin plugin) {
        addConfigAccessor(new ConfigAccessor(plugin, config).saveDefaultConfig());
    }

    /**
     * Adds a config accessor to the config manager.
     *
     * @param configAccessor The config accessor
     */
    private void addConfigAccessor(ConfigAccessor configAccessor) {
        configs.put(configAccessor.getConfigType(), configAccessor);
    }

    /**
     * Gets a certain config accessor.
     *
     * @param configType The type of the configuration file
     * @return The config accessor
     */
    public ConfigAccessor getConfigAccessor(Config configType) {
        return configs.get(configType).reloadConfig();
    }

    /**
     * Gets a certain configuration file.
     *
     * @param configType The type of the configuration file
     * @return The configuration file
     */
    public FileConfiguration getConfig(Config configType) {
        return getConfigAccessor(configType).getConfig();
    }

    public static String getNestedPath(String file) {
        StringBuilder path = new StringBuilder();
        int depth = Math.min(file.length(), NESTING_DEPTH);
        for (int i = 0; i < depth; i++) {
            path.append(file.charAt(i)).append('/');
        }
        path.append(file);
        return path.toString();
    }

}
