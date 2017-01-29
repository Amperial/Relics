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
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.item.attribute.AttributeType;
import ninja.amp.items.item.attribute.ItemLore;
import ninja.amp.items.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import ninja.amp.items.nms.nbt.NBTTagString;
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

    public Set<SocketColor> getAccepts() {
        return accepts;
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

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", DefaultAttributeType.SOCKET.getName());
        compound.setString("color", getColor().getName());
        if (!getAccepts().equals(getColor().getAccepts())) {
            NBTTagList list = NBTTagList.create();
            for (SocketColor color : getAccepts()) {
                list.add(NBTTagString.create(color.getName()));
            }
            compound.set("accepts", list);
        }
        if (hasGem()) {
            NBTTagCompound gemCompound = NBTTagCompound.create();
            getGem().saveToNBT(gemCompound);
            compound.set("gem", gemCompound);
        }
    }

    public static class SocketFactory extends BasicAttributeFactory<Socket> {

        public SocketFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public Socket loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load socket
            SocketColor color = SocketColor.fromName(config.getString("color"));
            Set<SocketColor> accepts;
            if (config.isList("accepts")) {
                accepts = new HashSet<>();
                List<String> colors = config.getStringList("accepts");
                accepts.addAll(colors.stream().map(SocketColor::fromName).collect(Collectors.toList()));
            } else {
                accepts = color.getAccepts();
            }
            Socket socket = new Socket(DefaultAttributeType.SOCKET, color, accepts);

            // Load gem
            if (config.isConfigurationSection("gem")) {
                ConfigurationSection gem = config.getConfigurationSection("gem");
                String type = gem.getString("type");
                if (itemManager.hasAttributeType(type)) {
                    socket.setGem((Gem) itemManager.getAttributeType(type).getFactory().loadFromConfig(gem));
                }
            }

            return socket;
        }

        @Override
        public Socket loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load socket
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            Set<SocketColor> accepts;
            if (compound.hasKey("accepts")) {
                accepts = new HashSet<>();
                NBTTagList list = compound.getList("accepts", 8);
                for (int i = 0; i < list.size(); i++) {
                    accepts.add(SocketColor.fromName(list.getString(i)));
                }
            } else {
                accepts = color.getAccepts();
            }
            Socket socket = new Socket(DefaultAttributeType.SOCKET, color, accepts);

            // Load gem
            if (compound.hasKey("gem")) {
                NBTTagCompound gem = compound.getCompound("gem");
                String type = gem.getString("type");
                if (itemManager.hasAttributeType(type)) {
                    socket.setGem((Gem) itemManager.getAttributeType(type).getFactory().loadFromNBT(gem));
                }
            }

            return socket;
        }

    }

}
