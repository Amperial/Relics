/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.config;

import ninja.amp.items.AmpItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains custom plugin configs.
 *
 * @author Austin Payne
 */
public class ConfigManager {

    private final Map<Config, ConfigAccessor> configs = new HashMap<>();

    /**
     * Creates a new config manager.
     *
     * @param plugin The amp items plugin instance
     */
    public ConfigManager(AmpItems plugin) {
        // Save main config
        plugin.saveDefaultConfig();

        // Register custom configs
        registerCustomConfigs(EnumSet.allOf(DefaultConfig.class), plugin);

        // TODO: Temporary
        registerCustomConfig(new ItemConfig("item"), plugin);
    }

    /**
     * Adds a config accessor for each custom config.
     *
     * @param customConfigs The custom configs to register
     * @param plugin        The plugin that owns the configs
     */
    public void registerCustomConfigs(EnumSet<? extends Config> customConfigs, JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        for (Config config : customConfigs) {
            addConfigAccessor(new ConfigAccessor(plugin, config, dataFolder).saveDefaultConfig());
        }
    }

    /**
     * Adds a config accessor for a custom config.
     *
     * @param config The custom config to register
     * @param plugin The plugin that owns the config
     */
    public void registerCustomConfig(Config config, JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        addConfigAccessor(new ConfigAccessor(plugin, config, dataFolder).saveDefaultConfig());
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
        return configs.get(configType).reloadConfig().getConfig();
    }

}
