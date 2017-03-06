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

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.config.transform.ArgTransform;
import com.herocraftonline.items.api.config.transform.ConfigTransform;
import com.herocraftonline.items.api.config.transform.ReferenceTransform;
import com.herocraftonline.items.api.config.transform.replacer.ReplacerTransform;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains custom plugin configs.
 *
 * @author Austin Payne
 */
public class ConfigManager {

    /**
     * The nesting depth for configs that need to be nested to avoid folders with thousands of files.<br>
     * Used in {@link #getNestedPath(String)}
     */
    private static int NESTING_DEPTH;

    private final ItemPlugin plugin;
    private final Map<Config, ConfigAccessor> configs = new HashMap<>();
    private final List<ConfigTransform> configTransforms = new ArrayList<>();

    /**
     * Creates a new config manager.
     *
     * @param plugin the item plugin instance
     */
    public ConfigManager(ItemPlugin plugin) {
        this.plugin = plugin;

        // Save main config
        plugin.saveDefaultConfig();

        // Load nesting depth
        NESTING_DEPTH = plugin.getConfig().getInt("config.nesting-depth", 0);

        // Register custom configs
        registerCustomConfigs(EnumSet.allOf(DefaultConfig.class), plugin);

        // Create config transforms
        configTransforms.add(new ArgTransform(plugin));
        configTransforms.add(new ReplacerTransform(plugin));
        configTransforms.add(new ReferenceTransform(plugin));
    }

    /**
     * Adds a config accessor for each custom config.
     *
     * @param customConfigs the custom configs to register
     * @param plugin        the plugin containing the default resources for the configs
     */
    public void registerCustomConfigs(EnumSet<? extends Config> customConfigs, Plugin plugin) {
        customConfigs.forEach(config -> registerCustomConfig(config, plugin));
    }

    /**
     * Adds a config accessor for a custom config.
     *
     * @param config the custom config to register
     * @param plugin the plugin containing the default resources for the config
     */
    public void registerCustomConfig(Config config, Plugin plugin) {
        addConfigAccessor(new ConfigAccessor(plugin, config, this.plugin.getDataFolder()).saveDefaultConfig());
    }

    /**
     * Adds a config accessor to the config manager.
     *
     * @param configAccessor the config accessor
     */
    private void addConfigAccessor(ConfigAccessor configAccessor) {
        configs.put(configAccessor.getConfigType(), configAccessor);
    }

    /**
     * Gets a certain config accessor.
     *
     * @param configType the type of the configuration file
     * @return the config accessor
     */
    public ConfigAccessor getConfigAccessor(Config configType) {
        return configs.get(configType).reloadConfig();
    }

    /**
     * Gets a certain configuration file.
     *
     * @param configType the type of the configuration file
     * @return the configuration file
     */
    public FileConfiguration getConfig(Config configType) {
        return getConfigAccessor(configType).getConfig();
    }

    /**
     * Gets a player's config accessor.
     *
     * @param player the player
     * @return the player's config accessor
     */
    public ConfigAccessor getPlayerConfigAccessor(Player player) {
        return new ConfigAccessor(plugin, new PlayerConfig(player), plugin.getDataFolder());
    }

    /**
     * Gets a player's configuration file.
     *
     * @param player the player
     * @return the player's configuration file
     */
    public FileConfiguration getPlayerConfig(Player player) {
        return getPlayerConfigAccessor(player).getConfig();
    }

    /**
     * Loads the configuration file at the specified path and passes it through the config transformers.
     *
     * @param fileName the file name of the configuration to transform
     * @param plugin   the plugin containing the default resources for the config
     * @param args     the args to be passed to the config transformers
     * @return the loaded and transformed configuration section
     */
    public ConfigurationSection loadAndTransform(String fileName, Plugin plugin, Object... args) {
        File configFile = new File(this.plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        return transformConfig(YamlConfiguration.loadConfiguration(configFile), args);
    }

    /**
     * Passes the configuration section through the config transformers.
     *
     * @param config the configuration section to transform
     * @param args   the args to be passed to the config transformers
     * @return the transformed configuration section
     */
    public ConfigurationSection transformConfig(ConfigurationSection config, Object... args) {
        if (config.isList("default-args")) {
            Object[] defaultArgs = config.getList("default-args").toArray();
            if (defaultArgs.length > args.length) {
                System.arraycopy(args, 0, defaultArgs, 0, args.length);
                args = defaultArgs;
            }
            config.set("default-args", null);
        }
        for (ConfigTransform transform : configTransforms) {
            config = transform.transform(config, args);
        }
        return config;
    }

    /**
     * Gets the nested path of a certain file based on its name and the {@link #NESTING_DEPTH}.
     * The path is constructed by nesting the file inside folders where the name of each consecutive folder
     * is the character in the file name at that position. For example, if the nesting depth is 4, the nested path
     * of the file "config.yml" would be "c/o/n/f/config.yml". The main use case for this is to avoid performance issues
     * when storing thousands of player configs in files named after their player name or UUID.
     *
     * @param file the file name
     * @return the nested path that should be used for the file
     */
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
