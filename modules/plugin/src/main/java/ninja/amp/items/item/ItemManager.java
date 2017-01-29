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
package ninja.amp.items.item;

import ninja.amp.items.AmpItems;
import ninja.amp.items.config.DefaultConfig;
import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.item.attribute.attributes.sockets.SocketColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private final AmpItems plugin;
    private final Map<String, AttributeType> attributeTypes;

    public ItemManager(AmpItems plugin) {
        this.plugin = plugin;
        this.attributeTypes = new HashMap<>();

        registerAttributeTypes(EnumSet.allOf(DefaultAttributeType.class), plugin);

        // TODO: Go through attributes.yml lore-order to assign lore positions
        FileConfiguration attributes = plugin.getConfigManager().getConfig(DefaultConfig.ATTRIBUTES);
        List<String> loreOrder = attributes.getStringList("lore-order");

        // Load default socket accepts
        FileConfiguration sockets = plugin.getConfigManager().getConfig(DefaultConfig.SOCKETS);
        if (sockets.isConfigurationSection("colors")) {
            ConfigurationSection colors = sockets.getConfigurationSection("colors");
            for (SocketColor color : SocketColor.values()) {
                if (colors.isSet(color.getName() + ".accepts")) {
                    List<String> accepts = colors.getStringList(color.getName() + ".accepts");
                    for (String accept : accepts) {
                        SocketColor acceptColor = SocketColor.fromName(accept);
                        if (acceptColor != null) {
                            color.addAccepts(acceptColor);
                        }
                    }
                }
            }
        }
    }

    public boolean hasAttributeType(String type) {
        return attributeTypes.containsKey(type);
    }

    public AttributeType getAttributeType(String type) {
        return attributeTypes.get(type);
    }

    public Map<String, AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void registerAttributeTypes(EnumSet<? extends AttributeType> types, JavaPlugin plugin) {
        types.forEach((AttributeType type) -> registerAttributeType(type, plugin));
    }

    public void registerAttributeType(AttributeType type, JavaPlugin plugin) {
        attributeTypes.put(type.getName(), type);
        this.plugin.getConfigManager().registerCustomConfig(type, plugin);
    }

}
