/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.api.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Used to access a YamlConfiguration file.
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
     * @param plugin     The plugin instance
     * @param configType The type of the configuration file
     * @param parent     The parent file
     */
    public ConfigAccessor(Plugin plugin, Config configType, File parent) {
        this.plugin = plugin;
        this.configType = configType;
        this.configFile = new File(parent, configType.getFileName());
    }

    /**
     * Creates a new config accessor with the parent file of the plugin instance.
     *
     * @param plugin     The plugin instance
     * @param configType The type of the configuration file
     */
    public ConfigAccessor(Plugin plugin, Config configType) {
        this(plugin, configType, plugin.getDataFolder());
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
     * @return The config accessor
     */
    @SuppressWarnings("deprecation")
    public ConfigAccessor reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        InputStream defConfigStream = plugin.getResource(configType.getFileName());
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        }
        return this;
    }

    /**
     * Gets the config.
     *
     * @return The configuration file
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
     * @return The config accessor
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
     * @return The config accessor
     */
    public ConfigAccessor saveDefaultConfig() {
        if (!configFile.exists()) {
            plugin.saveResource(configType.getFileName(), false);
        }
        return this;
    }

    /**
     * Gets the config type.
     *
     * @return The type of the configuration file
     */
    public Config getConfigType() {
        return configType;
    }

}
