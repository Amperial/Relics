/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item.attributes.sockets;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.BasicAttributeContainer;
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

public class SocketAttribute extends BasicAttributeContainer implements Socket {

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
                lore.add(prefix + c + "[ " + gem.getDisplayName() + c + " ]");
                getGem().getLore().addTo(lore, prefix + "  ");
            } else {
                lore.add(prefix + c + "[ " + ChatColor.GRAY + "-" + c + " ]");
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
    public boolean hasAttribute(Predicate<ItemAttribute> predicate) {
        return hasGem() && predicate.test(getGem());
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        if (hasGem()) {
            Gem gem = getGem();
            return predicate.test(gem) || gem.hasAttributeDeep(predicate);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz) {
        if (hasGem() && clazz.isAssignableFrom(getGem().getClass())) {
            T gem = (T) getGem();
            if (predicate.test(gem)) {
                return Optional.of(gem);
            }
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        if (hasGem()) {
            if (clazz.isAssignableFrom(getGem().getClass())) {
                T gem = (T) getGem();
                if (predicate.test(gem)) {
                    return Optional.of(gem);
                }
            }
            return getGem().getAttributeDeep(predicate, clazz);
        }
        return Optional.empty();
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        if (hasGem()) {
            return Collections.singleton(getGem());
        }
        return Collections.emptyList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz) {
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
    public void forEach(Consumer<ItemAttribute> action) {
        if (hasGem()) {
            action.accept(getGem());
        }
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
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

    public static class Factory extends BasicAttributeFactory<Socket> {

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
                ItemAttribute gem = itemManager.loadAttribute("gem", gemSection);
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
                ItemAttribute gem = itemManager.loadAttribute("gem", gemCompound);
                if (gem != null && gem instanceof Gem) {
                    socket.setGem((Gem) gem);
                }
            }

            return socket;
        }

    }

}
