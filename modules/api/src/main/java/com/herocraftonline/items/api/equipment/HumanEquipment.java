package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface HumanEquipment<T extends HumanEntity> extends LivingEntityEquipment<T>, InventoryEquipment<T> {

    @Override
    Slot<T> getSlot(String name);

    @Override
    default Slot<T> getSlot(T equipmentHolder, String name) {
        return getSlot(name);
    }

    @Override
    Slot<T> getMainHandSlot();

    @Override
    default Slot<T> getMainHandSlot(T equipmentHolder) {
        return getMainHandSlot();
    }

    @Override
    Slot<T> getOffHandSlot();

    @Override
    default Slot<T> getOffHandSlot(T equipmentHolder) {
        return getOffHandSlot();
    }

    @Override
    Slot<T> getHeadSlot();

    @Override
    default Slot<T> getHeadSlot(T equipmentHolder) {
        return getHeadSlot();
    }

    @Override
    Slot<T> getChestSlot();

    @Override
    default Slot<T> getChestSlot(T equipmentHolder) {
        return getChestSlot();
    }

    @Override
    Slot<T> getLegsSlot();

    @Override
    default Slot<T> getLegsSlot(T equipmentHolder) {
        return getLegsSlot();
    }

    @Override
    Slot<T> getFeetSlot();

    @Override
    default Slot<T> getFeetSlot(T equipmentHolder) {
        return getFeetSlot();
    }

    @Override
    default Slot<T> getSlot(EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HAND:
                return getMainHandSlot();
            case OFF_HAND:
                return getOffHandSlot();
            case HEAD:
                return getHeadSlot();
            case CHEST:
                return getChestSlot();
            case LEGS:
                return getLegsSlot();
            case FEET:
                return getFeetSlot();
            default:
                return null;
        }
    }

    @Override
    default Slot<T> getSlot(T equipmentHolder, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HAND:
                return getMainHandSlot(equipmentHolder);
            case OFF_HAND:
                return getOffHandSlot(equipmentHolder);
            case HEAD:
                return getHeadSlot(equipmentHolder);
            case CHEST:
                return getChestSlot(equipmentHolder);
            case LEGS:
                return getLegsSlot(equipmentHolder);
            case FEET:
                return getFeetSlot(equipmentHolder);
            default:
                return null;
        }
    }

    @Override
    Slot<T> getSlot(int inventorySlot);

    @Override
    default Slot<T> getSlot(T equipmentHolder, int inventorySlot) {
        return getSlot(inventorySlot);
    }

    @Override
    Collection<? extends Slot<T>> getSlots();

    @Override
    default Collection<? extends Slot<T>> getSlots(T equipmentHolder) {
        return getSlots();
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, ItemType itemType) {
        if (itemType == null) return Collections.emptyList();
        return getSlots(equipmentHolder).stream()
                .filter(slot -> slot.canHoldItem(itemType))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots().stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getSlotsForItem(T equipmentHolder, Item item) {
        if (item == null) return Collections.emptyList();
        return getSlots(equipmentHolder).stream()
                .filter(slot -> slot.canHoldItem(item))
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getEquipmentSlots() {
        return getSlots().stream()
                .filter(Slot::isEquipmentSlot)
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getEquipmentSlots(T equipementHolder) {
        return getSlots(equipementHolder).stream()
                .filter(Slot::isEquipmentSlot)
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getInventorySlots() {
        return getSlots().stream()
                .filter(Slot::isInventorySlot)
                .collect(Collectors.toList());
    }

    @Override
    default Collection<? extends Slot<T>> getInventorySlots(T equipmentHolder) {
        return getSlots(equipmentHolder).stream()
                .filter(Slot::isInventorySlot)
                .collect(Collectors.toList());
    }

    interface Slot<T extends HumanEntity> extends LivingEntityEquipment.Slot<T>, InventoryEquipment.Slot<T> { }
}
