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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * Used to access, reload, and save a YamlConfiguration file.
 *
 * @author Austin Payne
 */
public class ConfigAccessor {

    private final Plugin plugin;
    private final Config configType;
    private final File configFile;
    private FileConfiguration fileConfiguration;

    /**
     * Creates a new config accessor.
     *
     * @param plugin     the plugin instance
     * @param configType the type of the configuration file
     * @param parent     the parent file
     */
    public ConfigAccessor(Plugin plugin, Config configType, File parent) {
        this.plugin = plugin;
        this.configType = configType;
        this.configFile = new File(parent, configType.getFileName());
    }

    /**
     * Checks if the configuration file exists.
     *
     * @return {@code true} if the config exists, else {@code false}
     */
    public boolean exists() {
        return configFile.exists();
    }

    /**
     * Reloads the configuration file from disk.
     *
     * @return the config accessor
     */
    public ConfigAccessor reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        InputStream defConfigStream = plugin.getResource(configType.getFileName());
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
        return this;
    }

    /**
     * Gets the config.
     *
     * @return the configuration file
     */
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    /**
     * Saves the config to disk.
     *
     * @return the config accessor
     */
    public ConfigAccessor saveConfig() {
        if (fileConfiguration != null) {
            try {
                fileConfiguration.save(configFile);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
            }
        }
        return this;
    }

    /**
     * Generates the default config if it hasn't already been generated.
     *
     * @return the config accessor
     */
    public ConfigAccessor saveDefaultConfig() {
        if (!configFile.exists()) {
            try {
                plugin.saveResource(configType.getFileName(), false);
            } catch (Exception resource) {
                plugin.getLogger().log(Level.INFO, "Could not save default config for " + configFile);
                try {
                    configFile.getParentFile().mkdirs();
                    if (configFile.createNewFile()) {
                        plugin.getLogger().log(Level.INFO, "Generated empty config for " + configFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    /**
     * Gets the config type.
     *
     * @return the type of the configuration file
     */
    public Config getConfigType() {
        return configType;
    }

}
