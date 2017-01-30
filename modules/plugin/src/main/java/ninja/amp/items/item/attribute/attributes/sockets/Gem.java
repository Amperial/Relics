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
import ninja.amp.items.item.Item;
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.item.attribute.ItemAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.item.attribute.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Gem extends BasicAttribute {

    private final String displayName;
    private final SocketColor color;
    private final String item;
    private ItemAttribute attribute;

    public Gem(String name, SocketColor color, String item) {
        super(name, DefaultAttributeType.GEM);
        // TODO: Configurable
        this.displayName = color.getChatColor() + "(" + ChatColor.GRAY + getName() + color.getChatColor() + ")";
        this.color = color;
        this.item = item;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SocketColor getColor() {
        return color;
    }

    public String getItem() {
        return item;
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
    public boolean canEquip(Player player) {
        return hasAttribute() && getAttribute().canEquip(player);
    }

    @Override
    public void equip(Player player) {
        if (hasAttribute()) {
            getAttribute().equip(player);
        }
    }

    @Override
    public void unEquip(Player player) {
        if (hasAttribute()) {
            getAttribute().unEquip(player);
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("color", getColor().getName());
        compound.setString("item", getItem());
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

            // Load name, color, and item
            SocketColor color = SocketColor.fromName(config.getString("color"));
            String itemName = config.getString("item");
            if (!itemManager.hasItem(itemName)) {
                return null;
            }
            Item item = itemManager.getItem(itemName);
            String name = item.getName();

            // Create gem
            Gem gem = new Gem(name, color, itemName);

            // Load attribute
            gem.setAttribute(item.getAttributes());

            return gem;
        }

        @Override
        public Gem loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and item
            String name = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            String item = compound.getString("item");

            // Create gem
            Gem gem = new Gem(name, color, item);

            // Load attribute
            if (compound.hasKey("attribute")) {
                NBTTagCompound attributeCompound = compound.getCompound("attribute");
                ItemAttribute attribute = itemManager.loadAttribute(attributeCompound);
                if (attribute != null) {
                    gem.setAttribute(attribute);
                }
            }

            return gem;
        }

    }

}
