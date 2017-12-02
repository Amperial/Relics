/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeContainer;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Socket;
import com.herocraftonline.items.api.item.attribute.attributes.gems.SocketColor;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SocketAttribute extends BaseAttributeContainer<Socket> implements Socket {

    private SocketColor color;
    private Set<SocketColor> accepts;
    private Gem gem;

    public SocketAttribute(Item item, String name, AttributeType<Socket> type, String textLeft, String textRight, String gemIndent, SocketColor color, Set<SocketColor> accepts) {
        super(item, name, type);

        this.color = color;
        this.accepts = accepts;

        setLore((lore, prefix) -> {
            ChatColor c = getColor().getChatColor();
            lore.add(prefix + c + textLeft + (hasGem() ? gem.getDisplayName() : ChatColor.GRAY + "-") + c + textRight);
            if (hasGem()) {
                getGem().getLore().addTo(lore, prefix + gemIndent);
            }
        });
    }

    public SocketAttribute(Item item, String name, String textLeft, String textRight, String gemIndent, SocketColor color, Set<SocketColor> accepts) {
        this(item, name, DefaultAttributes.SOCKET, textLeft, textRight, gemIndent, color, accepts);
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
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return hasGem() && predicate.test(getGem());
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        if (hasGem()) {
            Gem gem = getGem();
            return predicate.test(gem) || gem.hasAttributeDeep(predicate);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate) {
        if (hasGem() && type.isAssignableFrom(getGem().getClass())) {
            T gem = (T) getGem();
            if (predicate.test(gem)) {
                return Optional.of(gem);
            }
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate) {
        if (hasGem()) {
            if (type.isAssignableFrom(getGem().getClass())) {
                T gem = (T) getGem();
                if (predicate.test(gem)) {
                    return Optional.of(gem);
                }
            }
            return getGem().getAttributeDeep(type, predicate);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Attribute> getAttributes() {
        if (hasGem()) {
            return Collections.singleton(getGem());
        }
        return Collections.emptyList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> clazz) {
        if (hasGem()) {
            Gem gem = getGem();
            List<T> attributes = new ArrayList<>(gem.getAttributesDeep(clazz));
            if (clazz.isAssignableFrom(gem.getClass())) {
                attributes.add((T) gem);
            }
            return attributes;
        }
        return Collections.emptyList();
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        if (hasGem()) {
            action.accept(getGem());
        }
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        if (hasGem()) {
            Gem gem = getGem();
            action.accept(gem);
            gem.forEachDeep(action);
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("color", getColor().getName());
        if (!getAccepts().equals(getColor().getAcceptedColors())) {
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

    public static class Factory extends BaseAttributeFactory<Socket> {
        private final String textLeft;
        private final String textRight;
        private final String gemIndent;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            // Load default socket accepts
            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttributes.SOCKET);
            if (config.isConfigurationSection("colors")) {
                ConfigurationSection colors = config.getConfigurationSection("colors");
                for (SocketColor color : SocketColor.values()) {
                    if (colors.isSet(color.getName() + ".accepts")) {
                        color.setAcceptedColors(colors.getStringList(color.getName() + ".accepts").stream()
                                .map(SocketColor::fromName).filter(acceptColor -> acceptColor != null).collect(Collectors.toSet()));
                    }
                }
            }

            // Load left/right lore text
            textLeft = ChatColor.translateAlternateColorCodes('&', config.getString("text-left", "[ "));
            textRight = ChatColor.translateAlternateColorCodes('&', config.getString("text-right", " ]"));
            gemIndent = ChatColor.translateAlternateColorCodes('&', config.getString("gem-text-indent", "  "));
        }

        @Override
        public Socket loadFromConfig(Item item, String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and accepts
            SocketColor color = SocketColor.fromName(config.getString("color", "yellow"));
            Set<SocketColor> accepts;
            if (config.isList("accepts")) {
                accepts = config.getStringList("accepts").stream()
                        .map(SocketColor::fromName).filter(acceptColor -> acceptColor != null).collect(Collectors.toSet());
            } else {
                accepts = color.getAcceptedColors();
            }

            // Create socket
            Socket socket = new SocketAttribute(item, name, textLeft, textRight, gemIndent, color, accepts);

            // Load gem
            if (config.isConfigurationSection("gem")) {
                ConfigurationSection gemSection = config.getConfigurationSection("gem");
                Attribute gem = itemManager.loadAttribute(item, "gem", gemSection);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }

        @Override
        public Socket loadFromNBT(Item item, String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and accepts
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            Set<SocketColor> accepts;
            if (compound.hasKey("accepts")) {
                accepts = new HashSet<>();
                NBTTagList list = compound.getList("accepts", 8);
                for (int i = 0; i < list.size(); i++) {
                    accepts.add(SocketColor.fromName(list.getString(i)));
                }
            } else {
                accepts = color.getAcceptedColors();
            }

            // Create socket
            Socket socket = new SocketAttribute(item, name, textLeft, textRight, gemIndent, color, accepts);

            // Load gem
            if (compound.hasKey("gem")) {
                NBTTagCompound gemCompound = compound.getCompound("gem");
                Attribute gem = itemManager.loadAttribute(item, "gem", gemCompound);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }
    }

}
