package com.herocraftonline.items.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class BaseEquipment implements com.herocraftonline.items.api.equipment.Equipment {

    protected final ItemPlugin plugin;
    private final UUID holderId;
    private final Map<String, Slot> slotsByName;
    private final Map<Integer, Slot> slotsByIndex;
    private final Map<ItemType, Collection<Slot>> slotsByType;

    public BaseEquipment(ItemPlugin plugin, LivingEntity holder) {
        this.plugin = plugin;
        this.holderId = holder.getUniqueId();
        this.slotsByName = new HashMap<>();
        this.slotsByIndex = new HashMap<>();
        this.slotsByType = new HashMap<>();
    }

    @Override
    public UUID getHolderId() {
        return holderId;
    }

    @Override
    public boolean hasSlot(String name) {
        return slotsByName.containsKey(name.toLowerCase());
    }

    @Override
    public boolean hasSlot(ItemType type) {
        return slotsByType.containsKey(type);
    }

    @Override
    public Slot getSlot(String name) {
        return slotsByName.get(name);
    }

    @Override
    public Collection<Slot> getSlots(ItemType type) {
        return hasSlot(type) ? Collections.unmodifiableCollection(slotsByType.get(type)) : Collections.emptySet();
    }

    @Override
    public Collection<Slot> getSlots() {
        return slotsByName.values();
    }

    @Override
    public boolean isSlotOpen(String name) {
        return false;
    }

    @Override
    public boolean isSlotOpen(ItemType type) {
        return false;
    }

    @Override
    public boolean isEquipped(Item item) {
        return false;
    }

    @Override
    public boolean equip(Item item, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean equip(Item item, ItemStack itemStack, Slot slot) {
        return false;
    }

    @Override
    public boolean replaceEquip(Item item, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean unEquip(Item item, ItemStack itemStack) {
        return false;
    }

    @Override
    public void unEquipAll() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    public abstract LivingEntity getHolder();
}
