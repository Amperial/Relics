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
package ninja.amp.items.item.attribute.attributes.sockets;

import ninja.amp.items.AmpItems;
import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.ItemLore;
import ninja.amp.items.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Socket extends BasicAttribute {

    private final SocketColor color;
    private final Set<SocketColor> accepts;
    private Gem gem;

    public Socket(AttributeType type, SocketColor color, Set<SocketColor> accepts) {
        super(type);

        this.color = color;
        this.accepts = accepts;

        setLore(new ItemLore() {
            @Override
            public void addTo(List<String> lore) {
                // TODO: Configurable
                ChatColor color = getColor().getChatColor();
                if (hasGem()) {
                    lore.add(color + "<< " + gem.getDisplayName() + color + " >>");
                    Gem gem = getGem();
                    if (gem.hasAttribute()) {
                        List<String> gemLore = new ArrayList<>();
                        gem.getAttribute().getLore().addTo(gemLore);
                        gemLore.forEach((String s) -> lore.add("  " + s));
                    }
                } else {
                    lore.add(color + "<< " + ChatColor.GRAY + "Empty Socket" + color + " >>");
                }
            }
        });
    }

    public SocketColor getColor() {
        return color;
    }

    public boolean hasGem() {
        return gem != null;
    }

    public boolean acceptsGem(Gem gem) {
        return accepts.contains(gem.getColor());
    }

    public Gem getGem() {
        return gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
    }

    public static class SocketFactory extends BasicAttributeFactory {

        public SocketFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public ItemAttribute loadFromConfig(ConfigurationSection config) {
            SocketColor color = SocketColor.fromName(config.getString("color"));

            Set<SocketColor> accepts;
            if (config.isSet("accepts")) {
                accepts = new HashSet<>();
                List<String> colors = config.getStringList("accepts");
                accepts.addAll(colors.stream().map(SocketColor::fromName).collect(Collectors.toList()));
            } else {
                accepts = color.getAccepts();
            }

            return new Socket(DefaultAttributeType.SOCKET, color, accepts);
        }

        @Override
        public ItemAttribute loadFromNBT() {
            return null;
        }

        @Override
        public void saveToNBT() {

        }

    }

}
