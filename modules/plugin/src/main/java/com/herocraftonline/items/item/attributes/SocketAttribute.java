/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
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
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

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

    public SocketAttribute(String name, AttributeType<Socket> type, SocketColor color, Set<SocketColor> accepts) {
        super(name, type);

        this.color = color;
        this.accepts = accepts;

        setLore((lore, prefix) -> {
            // TODO: Configurable
            ChatColor c = getColor().getChatColor();
            if (hasGem()) {
                lore.add(prefix + c + "[ " + gem.getDisplayName() + c + " ]");
                getGem().getLore().addTo(lore, prefix + "  ");
            } else {
                lore.add(prefix + c + "[ " + ChatColor.GRAY + "-" + c + " ]");
            }
        });
    }

    public SocketAttribute(String name, SocketColor color, Set<SocketColor> accepts) {
        this(name, DefaultAttribute.SOCKET, color, accepts);
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

    public static class Factory extends BaseAttributeFactory<Socket> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Socket loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and accepts
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
                Attribute gem = itemManager.loadAttribute("gem", gemSection);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }

        @Override
        public Socket loadFromNBT(String name, NBTTagCompound compound) {
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
                accepts = color.getAccepts();
            }

            // Create socket
            Socket socket = new SocketAttribute(name, color, accepts);

            // Load gem
            if (compound.hasKey("gem")) {
                NBTTagCompound gemCompound = compound.getCompound("gem");
                Attribute gem = itemManager.loadAttribute("gem", gemCompound);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }
    }

}
