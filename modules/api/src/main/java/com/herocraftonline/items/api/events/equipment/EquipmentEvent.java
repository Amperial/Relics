package com.herocraftonline.items.api.events.equipment;

import com.herocraftonline.items.api.equipment.Equipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public abstract class EquipmentEvent extends Event {

    private LivingEntity equipmentHolder;
    private Equipment equipment;

    public EquipmentEvent(LivingEntity equipmentHolder, Equipment equipment) {
        this.equipmentHolder = equipmentHolder;
        this.equipment = equipment;
    }

    public LivingEntity getEquipmentHolder() {
        return equipmentHolder;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
