/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.config;

import com.herocraftonline.items.api.storage.config.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigAccessor implements com.herocraftonline.items.api.storage.config.ConfigAccessor {

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

    @Override
    public boolean exists() {
        return configFile.exists();
    }

    @Override
    public ConfigAccessor reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        InputStream defConfigStream = plugin.getResource(configType.getFileName());
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
        return this;
    }

    @Override
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    @Override
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

    @Override
    public ConfigAccessor saveDefaultConfig(boolean createEmpty) {
        if (!configFile.exists()) {
            try {
                plugin.saveResource(configType.getFileName(), false);
            } catch (Exception resource) {
                if (createEmpty) {
                    try {
                        if (configFile.getParentFile().mkdirs() && configFile.createNewFile()) {
                            plugin.getLogger().log(Level.INFO, "Generated empty config file at " + configFile);
                        }
                    } catch (IOException e) {
                        plugin.getLogger().log(Level.SEVERE, "Error generating empty config file at " + configFile);
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }

    @Override
    public Config getConfigType() {
        return configType;
    }

}
