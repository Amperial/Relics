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

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.config.transform.ArgTransform;
import ninja.amp.items.api.config.transform.ConfigTransform;
import ninja.amp.items.api.config.transform.ReferenceTransform;
import ninja.amp.items.api.config.transform.replacer.ReplacerTransform;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

    private static int NESTING_DEPTH;

    private final Map<Config, ConfigAccessor> configs = new HashMap<>();
    private final List<ConfigTransform> configTransforms = new ArrayList<>();

    /**
     * Creates a new config manager.
     *
     * @param plugin The item plugin instance
     */
    public ConfigManager(ItemPlugin plugin) {
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

    @SuppressWarnings("deprecation")
    public ConfigurationSection loadAndTransform(String fileName, Plugin plugin, Object... args) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        return transformConfig(YamlConfiguration.loadConfiguration(configFile), args);
    }

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
