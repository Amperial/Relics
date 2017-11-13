package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface LivingEntityEquipment<T extends LivingEntity> extends Equipment<T> {

    boolean hasMainHandSlot();

    default boolean hasMainHandSlot(T equipmentHolder) {
        return hasMainHandSlot();
    }

    boolean hasOffHandSlot();

    default boolean hasOffHandSlot(T equipmentHolder) {
        return hasOffHandSlot();
    }

    boolean hasHeadSlot();

    default boolean hasHeadSlot(T equipmentHolder) {
        return hasHeadSlot();
    }

    boolean hasChestSlot();

    default boolean hasChestSlot(T equipmentHolder) {
        return hasChestSlot();
    }

    boolean hasLegsSlot();

    default boolean hasLegsSlot(T equipmentHolder) {
        return hasLegsSlot();
    }

    boolean hasFeetSlot();

    default boolean hasFeetSlot(T equipmentHolder) {
        return hasFeetSlot();
    }

    default boolean hasSlot(EquipmentSlot equipmentSlot)
    {
        switch (equipmentSlot) {
            case HAND:
                return hasMainHandSlot();
            case OFF_HAND:
                return hasOffHandSlot();
            case HEAD:
                return hasHeadSlot();
            case CHEST:
                return hasChestSlot();
            case LEGS:
                return hasLegsSlot();
            case FEET:
                return hasFeetSlot();
            default:
                return false;
        }
    }

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

    @Override
    Slot<T> getSlot(String name);

    @Override
    default Slot<T> getSlot(T equipmentHolder, String name) {
        return getSlot(name);
    }

    Slot<T> getMainHandSlot();

    default Slot<T> getMainHandSlot(T equipmentHolder) {
        return getMainHandSlot();
    }

    Slot<T> getOffHandSlot();

    default Slot<T> getOffHandSlot(T equipmentHolder) {
        return getOffHandSlot();
    }

    Slot<T> getHeadSlot();

    default Slot<T> getHeadSlot(T equipmentHolder) {
        return getHeadSlot();
    }

    Slot<T> getChestSlot();

    default Slot<T> getChestSlot(T equipmentHolder) {
        return getChestSlot();
    }

    Slot<T> getLegsSlot();

    default Slot<T> getLegsSlot(T equipmentHolder) {
        return getLegsSlot();
    }

    Slot<T> getFeetSlot();

    default Slot<T> getFeetSlot(T equipmentHolder) {
        return getFeetSlot();
    }

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

    default Collection<? extends Slot<T>> getEquipmentSlots() {
        return getSlots().stream()
                .filter(Slot::isEquipmentSlot)
                .collect(Collectors.toList());
    }

    default Collection<? extends Slot<T>> getEquipmentSlots(T equipementHolder) {
        return getSlots(equipementHolder).stream()
                .filter(Slot::isEquipmentSlot)
                .collect(Collectors.toList());
    }

    interface Slot<T extends LivingEntity> extends Equipment.Slot<T> {

        boolean isEquipmentSlot();

        EquipmentSlot getEquipmentSlot();

        default boolean isMainHand() {
            return getEquipmentSlot() == EquipmentSlot.HAND;
        }

        default boolean isOffHand() {
            return getEquipmentSlot() == EquipmentSlot.OFF_HAND;
        }

        default boolean isHead() {
            return getEquipmentSlot() == EquipmentSlot.HEAD;
        }

        default boolean isChest() {
            return getEquipmentSlot() == EquipmentSlot.CHEST;
        }

        default boolean isLegs() {
            return getEquipmentSlot() == EquipmentSlot.LEGS;
        }

        default boolean isFeet() {
            return getEquipmentSlot() == EquipmentSlot.FEET;
        }
    }
}
