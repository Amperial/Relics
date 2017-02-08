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
package ninja.amp.items.item;

import ninja.amp.items.api.item.Clickable;
import ninja.amp.items.api.item.Equippable;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemFactory;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.Model;
import ninja.amp.items.api.item.attribute.attributes.stats.GenericAttribute;
import ninja.amp.items.api.item.attribute.attributes.stats.StatAttribute;
import ninja.amp.items.api.item.attribute.attributes.stats.StatGroup;
import ninja.amp.items.api.item.attribute.attributes.stats.StatType;
import ninja.amp.items.item.attributes.DefaultAttributeType;
import ninja.amp.items.nms.NMSHandler;
import ninja.amp.items.nms.nbt.NBTBase;
import ninja.amp.items.nms.nbt.NBTTagCompound;
import ninja.amp.items.nms.nbt.NBTTagList;
import ninja.amp.items.nms.nbt.NBTTagObject;
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

    private static final String ITEM_TAG = "amp-item";

    private final String name;
    private final UUID id;
    private final Material material;
    private final ItemType type;
    private final AttributeGroup attributes;

    private CustomItem(String name, UUID id, Material material, ItemType type, AttributeGroup attributes) {
        this.name = name;
        this.id = id;
        this.material = material;
        this.type = type;
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
    public boolean hasAttribute(Class<?> clazz) {
        return attributes.hasAttribute(clazz);
    }

    @Override
    public boolean hasAttributeDeep(Class<?> clazz) {
        return attributes.hasAttributeDeep(clazz);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<ItemAttribute> getAttributes(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Class<T> clazz) {
        return attributes.getAttribute(clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz) {
        return attributes.getAttribute(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Class<T> clazz) {
        return attributes.getAttributeDeep(clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(String name, Class<T> clazz) {
        return attributes.getAttributeDeep(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttribute(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributeDeep(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Class<T> clazz) {
        return attributes.getAttributes(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz) {
        return attributes.getAttributesDeep(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributes(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributesDeep(predicate, clazz);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEach(action, predicate);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEachDeep(action, predicate);
    }

    @Override
    public void addAttribute(ItemAttribute... attributes) {
        this.attributes.addAttribute(attributes);
    }

    @Override
    public void removeAttribute(ItemAttribute attribute) {
        attributes.removeAttribute(attribute);
    }

    @Override
    public boolean canEquip(Player player) {
        return getAttributesDeep(attribute -> attribute instanceof Equippable).stream().allMatch(attribute -> ((Equippable) attribute).canEquip(player));
    }

    @Override
    public void onEquip(Player player) {
        forEachDeep(attribute -> ((Equippable) attribute).onEquip(player), attribute -> attribute instanceof Equippable);
    }

    @Override
    public void onUnEquip(Player player) {
        forEachDeep(attribute -> ((Equippable) attribute).onUnEquip(player), attribute -> attribute instanceof Equippable);
    }

    @Override
    public void onClick(PlayerInteractEvent event, boolean equipped) {
        forEachDeep(attribute -> ((Clickable) attribute).onClick(event, equipped), attribute -> attribute instanceof Clickable);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());

        // Update ItemStack
        item = updateItem(item);

        return item;
    }

    @Override
    @SuppressWarnings("unchecked")
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
        Optional<ItemAttribute> modelOptional = attributes.getAttribute(DefaultAttributeType.MODEL);
        if (modelOptional.isPresent()) {
            Model model = (Model) modelOptional.get();
            item.setDurability(model.getModelDamage());
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        // Check for generic attributes
        if (hasAttributeDeep(ItemAttribute.type(GenericAttribute.class))) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        // Set ItemMeta
        item.setItemMeta(meta);

        // Get NBTTagCompound
        NBTTagCompound compound = NMSHandler.getInterface().getTagCompoundDirect(item);

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

        // Update modifiers
        forEachDeep(attribute -> setModifier(modifiers, (GenericAttribute) attribute), ItemAttribute.type(GenericAttribute.class));

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
        item = NMSHandler.getInterface().setTagCompoundDirect(item, compound);

        return item;
    }

    @SuppressWarnings("unchecked")
    private void addStatGroup(List<String> lore, StatType.Position position) {
        StatGroup stats = new StatGroup();
        attributes.forEachDeep(a -> {
            if (((StatAttribute) a).getStatType().getPosition() == position) {
                stats.addStat((StatAttribute) a);
            }
        }, a -> a instanceof StatAttribute);
        stats.addTo(lore, "");
    }

    private void setModifier(NBTTagList modifiers, GenericAttribute attribute) {
        NBTTagCompound modifier = NBTTagCompound.create();
        modifier.setString("AttributeName", attribute.getMinecraftType().getName());
        modifier.setString("Name", ITEM_TAG + ":" + attribute.getName());
        modifier.setDouble("Amount", attribute.getAmount());
        if (attribute.getSlot() == GenericAttribute.Slot.ANY) {
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
            ItemType type = new ItemType(config.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig("attributes", config);

            // Create Item
            return new CustomItem(name, id, material, type, attribute);
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
            ItemType type = new ItemType(compound.getString("item-type"));
            AttributeGroup attribute = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromNBT("attributes", compound);

            // Create Item
            Item item = new CustomItem(name, id, material, type, attribute);
            compound.setBase("item-instance", NBTTagObject.create(item));
            return item;
        }

        @Override
        public Item loadFromItemStack(ItemStack itemStack) {
            // Get ItemStack NBT
            NBTTagCompound compound = NMSHandler.getInterface().getTagCompoundDirect(itemStack);
            NBTTagCompound itemTag = compound.getCompound(ITEM_TAG);

            // Load from NBT
            Item item = loadFromNBT(itemTag);

            // Set ItemStack NBT
            NMSHandler.getInterface().setTagCompoundDirect(itemStack, compound);

            return item;
        }

        @Override
        public boolean isItem(ItemStack itemStack) {
            return NMSHandler.getInterface().getTagCompoundDirect(itemStack).hasKey(ITEM_TAG);
        }

    }

}
