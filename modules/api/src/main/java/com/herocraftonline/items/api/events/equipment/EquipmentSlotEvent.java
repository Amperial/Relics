package com.herocraftonline.items.api.events.equipment;

import com.herocraftonline.items.api.equipment.Equipment;
import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public abstract class EquipmentSlotEvent extends EquipmentEvent {

    private static final HandlerList handlers = new HandlerList();

    private Equipment.Slot slot;

    public EquipmentSlotEvent(Equipment equipment, Equipment.Slot slot) {
        super(equipment);
        this.slot = slot;
    }

    public Equipment.Slot getSlot() {
        return slot;
    }
}
