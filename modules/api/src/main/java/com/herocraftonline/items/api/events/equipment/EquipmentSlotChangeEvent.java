package com.herocraftonline.items.api.events.equipment;

import com.herocraftonline.items.api.equipment.Equipment;
import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EquipmentSlotChangeEvent extends EquipmentEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final String slotName;
    private final Item currentItem;
    private Item newItem;
    private boolean cancelled;

    public EquipmentSlotChangeEvent(LivingEntity equipmentHolder, Equipment equipment, String slotName, Item currentItem, Item newItem) {
        super(equipmentHolder, equipment);
        this.slotName = slotName;
        this.currentItem = currentItem;
        this.newItem = newItem;
    }

    public String getSlotName() {
        return slotName;
    }

    public boolean hasCurrentItem() {
        return currentItem != null;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public Item getNewItem() {
        return newItem;
    }

    public void setNewItem(Item newItem) {
        this.newItem = newItem;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
