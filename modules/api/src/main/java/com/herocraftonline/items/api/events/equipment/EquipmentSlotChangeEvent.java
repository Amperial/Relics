package com.herocraftonline.items.api.events.equipment;

import com.herocraftonline.items.api.equipment.Equipment;
import com.herocraftonline.items.api.item.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EquipmentSlotChangeEvent extends EquipmentSlotEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Item newItem;
    private Action action;
    private boolean cancelled;

    public EquipmentSlotChangeEvent(Equipment equipment, Equipment.Slot slot, Item newItem) {
        super(equipment, slot);
        this.newItem = newItem;
        if (getSlot().hasItem()) {
            if (newItem == null) {
                action = Action.EMPTY;
            } else {
                action = Action.REPLACE;
            }
        } else {
            if (newItem != null) {
                action = Action.FILL;
            } else {
                throw new IllegalStateException("Event call for empty slot being given a `null` item");
            }
        }
    }

    public Action getAction() {
        return action;
    }

    public boolean isFillingSlot() {
        return action == Action.FILL;
    }

    public boolean isEmptyingSlot() {
        return action == Action.EMPTY;
    }

    public boolean isReplacingItem() {
        return action == Action.REPLACE;
    }

    public boolean hasCurrentItem() {
        return getSlot().hasItem();
    }

    public Item getNewItem() {
        return newItem;
    }

    public Item getCurrentItem() {
        return getSlot().getItem();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
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

    public enum Action {
        FILL,
        EMPTY,
        REPLACE,
    }
}
