package com.herocraftonline.items.api.events.equipment;

import com.herocraftonline.items.api.equipment.Equipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public abstract class EquipmentEvent extends Event {

    private Equipment equipment;

    public EquipmentEvent(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
