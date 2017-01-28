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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ConfigUtil {

    private ConfigUtil() {
    }

    @SuppressWarnings("unchecked")
    public static List<ConfigurationSection> getConfigurationSectionList(ConfigurationSection config, String path) {
        List<ConfigurationSection> list = new LinkedList<>();

        config.getList(path).stream()
                .filter(object -> object instanceof Map)
                .forEach(object -> {
                    MemoryConfiguration mc = new MemoryConfiguration();
                    mc.addDefaults((Map) object);
                    list.add(mc);
                });

        return list;
    }

}
