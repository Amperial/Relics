/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.config;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.storage.config.Config;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import com.herocraftonline.items.api.storage.config.transform.ArgTransform;
import com.herocraftonline.items.api.storage.config.transform.ConfigTransform;
import com.herocraftonline.items.api.storage.config.transform.ReferenceTransform;
import com.herocraftonline.items.api.storage.config.transform.ReplacerTransform;
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

public class ConfigManager implements com.herocraftonline.items.api.storage.config.ConfigManager {

    /**
     * The nesting depth for configs that need to be nested to avoid folders with thousands of files.<br>
     * Used in {@link #getNestedPath(String)}
     */
    private static int NESTING_DEPTH = 0;

    private final ItemPlugin plugin;
    private final Map<Config, ConfigAccessor> configs = new HashMap<>();
    private final List<ConfigTransform> configTransforms = new ArrayList<>();
    private final ConfigTransform replacerTransform;

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
        registerCustomConfigs(EnumSet.allOf(DefaultConfig.class), plugin, true);

        // Create config transforms
        replacerTransform = new ReplacerTransform(plugin);
        configTransforms.add(new ArgTransform(plugin));
        configTransforms.add(replacerTransform);
        configTransforms.add(new ReferenceTransform(plugin));
    }

    @Override
    public void registerCustomConfigs(EnumSet<? extends Config> customConfigs, Plugin plugin, boolean createEmpty) {
        customConfigs.forEach(config -> registerCustomConfig(config, plugin, createEmpty));
    }

    @Override
    public void registerCustomConfig(Config config, Plugin plugin, boolean createEmpty) {
        addConfigAccessor(new ConfigAccessor(plugin, config, this.plugin.getDataFolder()).saveDefaultConfig(createEmpty));
    }

    private void addConfigAccessor(ConfigAccessor configAccessor) {
        configs.put(configAccessor.getConfigType(), configAccessor);
    }

    @Override
    public ConfigAccessor getConfigAccessor(Config configType) {
        if (!configs.containsKey(configType)) {
            registerCustomConfig(configType, plugin, false);
        }
        return configs.get(configType).reloadConfig();
    }

    @Override
    public FileConfiguration getConfig(Config configType) {
        return getConfigAccessor(configType).getConfig();
    }

    @Override
    public ConfigAccessor getPlayerConfigAccessor(Player player) {
        return new ConfigAccessor(plugin, new PlayerConfig(player), plugin.getDataFolder()).saveDefaultConfig(true);
    }

    @Override
    public FileConfiguration getPlayerConfig(Player player) {
        return getPlayerConfigAccessor(player).getConfig();
    }

    @Override
    public ConfigurationSection loadAndTransform(String fileName, Plugin plugin, Object... args) {
        File configFile = new File(this.plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        return transformConfig(YamlConfiguration.loadConfiguration(configFile), args);
    }

    @Override
    public ConfigurationSection transformConfig(ConfigurationSection config, Object... args) {
        if (config.isList("default-args")) {
            config = replacerTransform.transform(config, args);
            Object[] defaultArgs = config.getList("default-args").toArray();
            if (defaultArgs.length > args.length) {
                System.arraycopy(args, 0, defaultArgs, 0, args.length);
                args = defaultArgs;
            }
            config.set("default-args", null);
        }

        // Replace string arguments with numbers where possible
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                try {
                    String itemArg = (String) args[i];
                    double value = Double.valueOf(itemArg);
                    if (itemArg.contains(".")) {
                        args[i] = value;
                    } else {
                        args[i] = (int) value;
                    }
                } catch (NumberFormatException e) {
                    // Arg isn't a number
                }
            }
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
    static String getNestedPath(String file) {
        StringBuilder path = new StringBuilder();
        int depth = Math.min(file.length(), NESTING_DEPTH);
        for (int i = 0; i < depth; i++) {
            path.append(file.charAt(i)).append('/');
        }
        path.append(file);
        return path.toString();
    }

}
