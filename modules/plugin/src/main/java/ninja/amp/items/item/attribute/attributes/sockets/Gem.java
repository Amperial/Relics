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
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Gem extends BasicAttribute {

    private final String name;
    private final String displayName;
    private final SocketColor color;
    private ItemAttribute attribute;

    public Gem(String name, SocketColor color) {
        super(DefaultAttributeType.GEM);
        this.name = name;
        // TODO: Configurable
        this.displayName = color.getChatColor() + "(" + ChatColor.GRAY + name + color.getChatColor() + ")";
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SocketColor getColor() {
        return color;
    }

    public boolean hasAttribute() {
        return attribute != null;
    }

    public ItemAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ItemAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString("type", DefaultAttributeType.GEM.getName());
        compound.setString("name", getName());
        compound.setString("color", getColor().getName());
        if (hasAttribute()) {
            NBTTagCompound attribute = NBTTagCompound.create();
            getAttribute().saveToNBT(attribute);
            compound.set("attribute", attribute);
        }
    }

    public static class GemFactory extends BasicAttributeFactory<Gem> {

        public GemFactory(AmpItems plugin) {
            super(plugin);
        }

        @Override
        public Gem loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load gem
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            SocketColor color = SocketColor.fromName(config.getString("color"));
            Gem gem = new Gem(name, color);

            // Load attribute
            if (config.isConfigurationSection("attribute")) {
                ConfigurationSection attribute = config.getConfigurationSection("attribute");
                String type = attribute.getString("type");
                if (itemManager.hasAttributeType(type)) {
                    gem.setAttribute(itemManager.getAttributeType(type).getFactory().loadFromConfig(attribute));
                }
            }

            return gem;
        }

        @Override
        public Gem loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load gem
            String name = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            Gem gem = new Gem(name, color);

            // Load attribute
            if (compound.hasKey("attribute")) {
                NBTTagCompound attribute = compound.getCompound("attribute");
                String type = attribute.getString("type");
                if (itemManager.hasAttributeType(type)) {
                    gem.setAttribute(itemManager.getAttributeType(type).getFactory().loadFromNBT(attribute));
                }
            }

            return gem;
        }

    }

}
