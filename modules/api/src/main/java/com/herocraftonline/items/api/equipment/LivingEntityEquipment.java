package com.herocraftonline.items.api.equipment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

public interface LivingEntityEquipment<T extends LivingEntity> extends Equipment<T> {

    boolean hasMainHandSlot(T equipmentHolder);

    boolean hasOffHandSlot(T equipmentHolder);

    boolean hasHeadSlot(T equipmentHolder);

    boolean hasChestSlot(T equipmentHolder);

    boolean hasLegsSlot(T equipmentHolder);

    boolean hasFeetSlot(T equipmentHolder);

    default boolean hasSlot(T equipmentHolder, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HAND:
                return hasMainHandSlot(equipmentHolder);
            case OFF_HAND:
                return hasOffHandSlot(equipmentHolder);
            case HEAD:
                return hasHeadSlot(equipmentHolder);
            case CHEST:
                return hasChestSlot(equipmentHolder);
            case LEGS:
                return hasLegsSlot(equipmentHolder);
            case FEET:
                return hasFeetSlot(equipmentHolder);
            default:
                return false;
        }
    }

    interface Slot<T extends LivingEntity> extends Equipment.Slot<T> {

    }
}
