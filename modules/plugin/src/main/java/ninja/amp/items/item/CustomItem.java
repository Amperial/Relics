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
package ninja.amp.items.item;

import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemFactory;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeGroup;
import ninja.amp.items.api.item.attribute.attributes.MinecraftAttribute;
import ninja.amp.items.api.item.attribute.attributes.Model;
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
        return attributes.canEquip(player);
    }

    @Override
    public void onEquip(Player player) {
        attributes.onEquip(player);
    }

    @Override
    public void onUnEquip(Player player) {
        attributes.onUnEquip(player);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());

        // Update ItemStack
        item = updateItem(item);

        return item;
    }

    @Override
    public ItemStack updateItem(ItemStack item) {
        // Set ItemMeta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        List<String> lore = new ArrayList<>();
        attributes.getLore().addTo(lore, "");
        Optional<ItemAttribute> modelOptional = attributes.getAttribute(DefaultAttributeType.MODEL);
        if (modelOptional.isPresent()) {
            Model model = (Model) modelOptional.get();
            item.setDurability(model.getModelDamage());
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        meta.setLore(lore);
        if (hasAttributeDeep(ItemAttribute.type(MinecraftAttribute.class))) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
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
        forEachDeep(attribute -> updateModifier(modifiers, (MinecraftAttribute) attribute), ItemAttribute.type(MinecraftAttribute.class));

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

    private NBTTagCompound getModifier(NBTTagList modifiers, MinecraftAttribute attribute) {
        String attributeName = attribute.getMinecraftType().getName();
        String name = ITEM_TAG + ":" + attribute.getName();
        for (int i = 0; i < modifiers.size(); i++) {
            NBTTagCompound modifier = modifiers.getCompound(i);
            if (modifier.getString("AttributeName").equals(attributeName)) {
                if (modifier.getString("Name").equals(name)) {
                    return modifier;
                }
            }
        }
        NBTTagCompound modifier = NBTTagCompound.create();
        modifier.setString("AttributeName", attributeName);
        modifier.setString("Name", name);
        modifiers.addBase(modifier);
        return modifier;
    }

    private void updateModifier(NBTTagList modifiers, MinecraftAttribute attribute) {
        NBTTagCompound modifier = getModifier(modifiers, attribute);
        modifier.setDouble("Amount", attribute.getAmount());
        if (attribute.getSlot() == MinecraftAttribute.Slot.ANY) {
            modifier.remove("Slot");
        } else {
            modifier.setString("Slot", attribute.getSlot().getName());
        }
        modifier.setInt("Operation", attribute.getOperation().ordinal());
        modifier.setLong("UUIDMost", attribute.getUUID().getMostSignificantBits());
        modifier.setLong("UUIDLeast", attribute.getUUID().getLeastSignificantBits());
        modifier.setBoolean("Updated", true);
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
            ItemType type = itemManager.getItemType(config.getString("item-type"));
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
            ItemType type = itemManager.getItemType(compound.getString("item-type"));
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
