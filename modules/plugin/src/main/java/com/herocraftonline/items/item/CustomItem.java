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
package com.herocraftonline.items.item;

import com.herocraftonline.items.api.item.Clickable;
import com.herocraftonline.items.api.item.Equippable;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemFactory;
import com.herocraftonline.items.api.item.ItemType;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.Group;
import com.herocraftonline.items.api.item.attribute.attributes.Minecraft;
import com.herocraftonline.items.api.item.attribute.attributes.Model;
import com.herocraftonline.items.api.item.attribute.attributes.requirements.Requirement;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatGroup;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatType;
import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagObject;
import com.herocraftonline.items.nms.NMSHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CustomItem implements Item {

    private static final String ITEM_TAG = "relics-item";

    private final String name;
    private final UUID id;
    private final Material material;
    private final boolean unbreakable;
    private final ItemType type;
    private final Group attributes;
    private boolean equipped = false;

    private CustomItem(String name, UUID id, Material material, ItemType type, boolean unbreakable, Group attributes) {
        this.name = name;
        this.id = id;
        this.material = material;
        this.type = type;
        this.unbreakable = unbreakable;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable || hasAttribute(DefaultAttribute.MODEL);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttribute(Class<? extends Attribute> type) {
        return attributes.hasAttribute(type);
    }

    @Override
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public Optional<Attribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<Attribute> getAttribute(Predicate<Attribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type) {
        return attributes.getAttribute(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, String name) {
        return attributes.getAttribute(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttribute(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<Attribute> getAttributes(Predicate<Attribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type) {
        return attributes.getAttributes(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributes(type, predicate);
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEach(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Consumer<T> action) {
        attributes.forEach(type, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEach(type, predicate, action);
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttributeDeep(Class<? extends Attribute> type) {
        return attributes.hasAttributeDeep(type);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type) {
        return attributes.getAttributeDeep(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, String name) {
        return attributes.getAttributeDeep(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributeDeep(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type) {
        return attributes.getAttributesDeep(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributesDeep(type, predicate);
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEachDeep(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Consumer<T> action) {
        attributes.forEachDeep(type, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEachDeep(type, predicate, action);
    }

    @Override
    public void addAttribute(Attribute... attributes) {
        this.attributes.addAttribute(attributes);
    }

    @Override
    public void removeAttribute(Attribute attribute) {
        attributes.removeAttribute(attribute);
    }

    @Override
    public boolean isEquipped() {
        return equipped;
    }

    @Override
    public boolean canEquip(Player player) {
        return getAttributesDeep(Requirement.class).stream().allMatch(a -> a.test(player, this));
    }

    @Override
    public boolean onEquip(Player player) {
        equipped = true;
        Collection<Attribute> attributes = getAttributesDeep(attribute -> attribute instanceof Equippable);
        boolean update = false;
        for (Attribute attribute : attributes) {
            if (((Equippable) attribute).onEquip(player)) {
                update = true;
            }
        }
        return update;
    }

    @Override
    public boolean onUnEquip(Player player) {
        equipped = false;
        Collection<Attribute> attributes = getAttributesDeep(attribute -> attribute instanceof Equippable);
        boolean update = false;
        for (Attribute attribute : attributes) {
            if (((Equippable) attribute).onUnEquip(player)) {
                update = true;
            }
        }
        return update;
    }

    @Override
    public void onClick(PlayerInteractEvent event, Item item) {
        forEachDeep(attribute -> attribute instanceof Clickable, attribute -> ((Clickable) attribute).onClick(event, item));
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());

        // Update ItemStack
        item = updateItem(item);

        return item;
    }

    @Override
    @SuppressWarnings("unchecked, deprecation")
    public ItemStack updateItem(ItemStack item) {
        // Get ItemMeta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());

        // Build lore
        List<String> lore = new ArrayList<>();
        // Add stats to top of lore
        if (attributes.hasAttributeDeep(StatAttribute.class)) {
            addStatGroup(lore, StatType.Position.FARTHEST_TOP);
            addStatGroup(lore, StatType.Position.TOP);
        }
        // Add lore of all attributes
        attributes.getLore().addTo(lore, "");
        // Add stats to bottom of lore
        if (attributes.hasAttributeDeep(StatAttribute.class)) {
            lore.add("");
            addStatGroup(lore, StatType.Position.BOTTOM);
            addStatGroup(lore, StatType.Position.FARTHEST_BOTTOM);
        }
        meta.setLore(lore);

        // Check for model attribute
        Optional<Attribute> modelOptional = attributes.getAttribute(DefaultAttribute.MODEL);
        if (modelOptional.isPresent()) {
            Model model = (Model) modelOptional.get();
            item.setDurability(model.getModelDamage());
        }

        // Check for unbreakable
        if (isUnbreakable()) {
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        // Check for generic attributes
        if (hasAttributeDeep(Minecraft.class)) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        // Set ItemMeta
        item.setItemMeta(meta);

        // Get NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(item);

        // Set item compound
        NBTTagCompound itemTag = NBTTagCompound.create();
        saveToNBT(itemTag);
        compound.setBase(ITEM_TAG, itemTag);

        // Get attribute modifiers
        NBTTagList modifiers;
        if (compound.hasKey("AttributeModifiers")) {
            modifiers = compound.getList("AttributeModifiers", 10);
        } else {
            modifiers = NBTTagList.create();
        }

        // Temporarily tag modifiers to remove unused ones later
        for (int i = 0; i < modifiers.size(); i++) {
            NBTTagCompound modifier = modifiers.getCompound(i);
            if (modifier.getString("Name").startsWith(ITEM_TAG)) {
                modifier.setBoolean("Updated", false);
            }
        }

        // Update modifiers if item is equipped
        if (isEquipped()) {
            forEachDeep(Minecraft.class, attribute -> setModifier(modifiers, attribute));
        }

        // Remove unused modifiers
        int i = 0;
        while (i < modifiers.size()) {
            NBTTagCompound modifier = modifiers.getCompound(i);
            if (modifier.getString("Name").startsWith(ITEM_TAG) && !modifier.getBoolean("Updated")) {
                modifiers.removeBase(i);
            } else {
                modifier.remove("Updated");
                i++;
            }
        }

        // Set attribute modifiers
        compound.setBase("AttributeModifiers", modifiers);

        // Set NBTTagCompound
        item = NMSHandler.getInterface().setTagCompound(item, compound);

        return item;
    }

    @SuppressWarnings("unchecked")
    private void addStatGroup(List<String> lore, StatType.Position position) {
        StatGroup stats = new StatGroup();
        attributes.forEachDeep(a -> a instanceof StatAttribute, a -> {
            if (((StatAttribute) a).getStatType().getPosition() == position) {
                stats.addStat((StatAttribute) a);
            }
        });
        stats.addTo(lore, "");
    }

    private void setModifier(NBTTagList modifiers, Minecraft attribute) {
        NBTTagCompound modifier = NBTTagCompound.create();
        modifier.setString("AttributeName", attribute.getMinecraftType().getName());
        modifier.setString("Name", ITEM_TAG + ":" + attribute.getName());
        modifier.setDouble("Amount", attribute.getAmount());
        if (attribute.getSlot() == Minecraft.Slot.ANY) {
            modifier.remove("Slot");
        } else {
            modifier.setString("Slot", attribute.getSlot().getName());
        }
        modifier.setInt("Operation", attribute.getOperation().ordinal());
        modifier.setLong("UUIDMost", attribute.getUUID().getMostSignificantBits());
        modifier.setLong("UUIDLeast", attribute.getUUID().getLeastSignificantBits());
        modifier.setBoolean("Updated", true);
        modifiers.addBase(modifier);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        attributes.saveToNBT(compound);
        compound.setString("name", getName());
        compound.setString("id", getId().toString());
        compound.setString("material", getMaterial().name());
        compound.setBoolean("unbreakable", isUnbreakable());
        compound.setString("item-type", getType().getName());
        compound.setBase("item-instance", NBTTagObject.create(this));
    }

    public static class DefaultItemFactory implements ItemFactory {
        private ItemManager itemManager;

        public DefaultItemFactory(ItemManager itemManager) {
            this.itemManager = itemManager;
        }

        @Override
        public Item loadFromConfig(ConfigurationSection config) {
            // Load name, id, material, type, and attributes
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            UUID id = UUID.randomUUID();
            Material material = Material.getMaterial(config.getString("material"));
            boolean unbreakable = config.getBoolean("unbreakable", false);
            ItemType type = new ItemType(config.getString("item-type"));
            Group attribute = DefaultAttribute.GROUP.getFactory().loadFromConfig("attributes", config);

            // Create Item
            return new CustomItem(name, id, material, type, unbreakable, attribute);
        }

        @Override
        public Item loadFromNBT(NBTTagCompound compound) {
            if (compound.hasKey("item-instance")) {
                NBTBase itemInstance = compound.getBase("item-instance");
                if (itemInstance instanceof NBTTagObject) {
                    return (Item) ((NBTTagObject) itemInstance).getObject();
                }
            }

            // Load name, id, material, type, and attributes
            String name = compound.getString("name");
            UUID id = UUID.fromString(compound.getString("id"));
            Material material = Material.getMaterial(compound.getString("material"));
            boolean unbreakable = compound.hasKey("unbreakable") && compound.getBoolean("unbreakable");
            ItemType type = new ItemType(compound.getString("item-type"));
            Group attribute = DefaultAttribute.GROUP.getFactory().loadFromNBT("attributes", compound);

            // Create Item
            Item item = new CustomItem(name, id, material, type, unbreakable, attribute);
            compound.setBase("item-instance", NBTTagObject.create(item));
            return item;
        }

        @Override
        public Item loadFromItemStack(ItemStack itemStack) {
            // Get ItemStack NBT
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(itemStack);

            // TODO: Remove old item tag
            if (compound.hasKey("amp-item")) {
                compound.setBase(ITEM_TAG, compound.getCompound("amp-item"));
                compound.remove("amp-item");
            }

            // Get custom item compound
            NBTTagCompound itemTag = compound.getCompound(ITEM_TAG);

            // Load from NBT
            Item item = loadFromNBT(itemTag);

            // Set ItemStack NBT
            NMSHandler.getInterface().setTagCompound(itemStack, compound);

            return item;
        }

        @Override
        public boolean isItem(ItemStack itemStack) {
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompound(itemStack);
            // TODO: Remove old item tag
            return compound.hasKey("amp-item") || compound.hasKey(ITEM_TAG);
        }
    }

}
