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
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.BasicAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeFactory;
import ninja.amp.items.api.item.attribute.attributes.sockets.Gem;
import ninja.amp.items.api.item.attribute.attributes.sockets.SocketColor;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GemAttribute extends BasicAttribute implements Gem {

    private SocketColor color;
    private AttributeGroup attributes;
    private Item item;

    public GemAttribute(String name, SocketColor color, AttributeGroup attributes) {
        super(name, DefaultAttributeType.GEM);
        this.color = color;
        this.attributes = attributes;

        setLore((lore, prefix) -> getAttributes().getLore().addTo(lore, prefix));
    }

    @Override
    public String getDisplayName() {
        // TODO: Configurable
        return color.getChatColor() + "(" + ChatColor.GRAY + getName() + color.getChatColor() + ")";
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
    public boolean hasItem() {
        return item != null;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public AttributeGroup getAttributes() {
        return attributes;
    }

    @Override
    public boolean canEquip(Player player) {
        return getAttributes().canEquip(player);
    }

    @Override
    public void onEquip(Player player) {
        getAttributes().onEquip(player);
    }

    @Override
    public void onUnEquip(Player player) {
        getAttributes().onUnEquip(player);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("color", getColor().getName());
        NBTTagCompound attributes = NBTTagCompound.create();
        getAttributes().saveToNBT(attributes);
        compound.setBase("attributes", attributes);
        if (hasItem()) {
            NBTTagCompound item = NBTTagCompound.create();
            getItem().saveToNBT(item);
            compound.setBase("item", item);
        }
    }

    public static class Factory extends BasicAttributeFactory<Gem> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Gem loadFromConfig(ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attributes
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            SocketColor color = SocketColor.fromName(config.getString("color"));
            AttributeGroup attributes = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig(config);

            // Create gem
            Gem gem = new GemAttribute(name, color, attributes);

            // Load item
            if (config.isConfigurationSection("item")) {
                ConfigurationSection itemConfig = config.getConfigurationSection("item");
                Item item = itemManager.getItem(itemConfig);
                gem.setItem(item);
            }

            return gem;
        }

        @Override
        public Gem loadFromNBT(NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attribute
            String name = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            NBTTagCompound attributesCompound = compound.getCompound("attributes");
            AttributeGroup attributes = (AttributeGroup) itemManager.loadAttribute(attributesCompound);

            // Create gem
            Gem gem = new GemAttribute(name, color, attributes);

            // Load item
            if (compound.hasKey("item")) {
                NBTTagCompound itemCompound = compound.getCompound("item");
                Item item = itemManager.getItem(itemCompound);
                gem.setItem(item);
            }

            return gem;
        }

    }

}
