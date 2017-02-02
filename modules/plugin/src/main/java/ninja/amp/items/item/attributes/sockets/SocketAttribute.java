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
package ninja.amp.items.item.attributes.sockets;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.sockets.Gem;
import ninja.amp.items.api.item.attribute.attributes.sockets.Socket;
import ninja.amp.items.api.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import ninja.amp.items.nms.nbt.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SocketAttribute extends BasicAttribute implements Socket {

    private SocketColor color;
    private Set<SocketColor> accepts;
    private Gem gem;

    public SocketAttribute(String name, AttributeType type, SocketColor color, Set<SocketColor> accepts) {
        super(name, type);

        this.color = color;
        this.accepts = accepts;

        setLore((lore, prefix) -> {
            // TODO: Configurable
            ChatColor c = getColor().getChatColor();
            if (hasGem()) {
                lore.add(prefix + c + "<< " + gem.getDisplayName() + c + " >>");
                getGem().getAttributes().getLore().addTo(lore, prefix + "  ");
            } else {
                lore.add(prefix + c + "<< " + ChatColor.GRAY + "Empty Socket" + c + " >>");
            }
        });
    }

    public SocketAttribute(String name, SocketColor color, Set<SocketColor> accepts) {
        this(name, DefaultAttributeType.SOCKET, color, accepts);
    }

    @Override
    public SocketColor getColor() {
        return color;
    }

    @Override
    public void setColor(SocketColor color) {
        this.color = color;
    }

    @Override
    public Set<SocketColor> getAccepts() {
        return accepts;
    }

    @Override
    public void addAccepts(SocketColor... accepts) {
        Collections.addAll(this.accepts, accepts);
    }

    @Override
    public boolean acceptsGem(Gem gem) {
        return accepts.contains(gem.getColor());
    }

    @Override
    public boolean hasGem() {
        return gem != null;
    }

    @Override
    public Gem getGem() {
        return gem;
    }

    @Override
    public void setGem(Gem gem) {
        this.gem = gem;
    }

    @Override
    public boolean canEquip(Player player) {
        return !hasGem() || getGem().canEquip(player);
    }

    @Override
    public void equip(Player player) {
        if (hasGem()) {
            getGem().equip(player);
        }
    }

    @Override
    public void unEquip(Player player) {
        if (hasGem()) {
            getGem().unEquip(player);
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("color", getColor().getName());
        if (!getAccepts().equals(getColor().getAccepts())) {
            NBTTagList list = NBTTagList.create();
            for (SocketColor color : getAccepts()) {
                list.addBase(NBTTagString.create(color.getName()));
            }
            compound.setBase("accepts", list);
        }
        if (hasGem()) {
            NBTTagCompound gemCompound = NBTTagCompound.create();
            getGem().saveToNBT(gemCompound);
            compound.setBase("gem", gemCompound);
        }
    }

    public static class Factory extends BasicAttributeFactory<Socket> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Socket loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and accepts
            String name = config.getName();
            SocketColor color = SocketColor.fromName(config.getString("color"));
            Set<SocketColor> accepts;
            if (config.isList("accepts")) {
                accepts = new HashSet<>();
                List<String> colors = config.getStringList("accepts");
                accepts.addAll(colors.stream().map(SocketColor::fromName).collect(Collectors.toList()));
            } else {
                accepts = color.getAccepts();
            }

            // Create socket
            Socket socket = new SocketAttribute(name, color, accepts);

            // Load gem
            if (config.isConfigurationSection("gem")) {
                ConfigurationSection gemSection = config.getConfigurationSection("gem");
                ItemAttribute gem = itemManager.loadAttribute(gemSection);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }

        @Override
        public Socket loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and accepts
            String name = compound.getString("name");
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

            // Create socket
            Socket socket = new SocketAttribute(name, color, accepts);

            // Load gem
            if (compound.hasKey("gem")) {
                NBTTagCompound gemCompound = compound.getCompound("gem");
                ItemAttribute gem = itemManager.loadAttribute(gemCompound);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }

    }

}
