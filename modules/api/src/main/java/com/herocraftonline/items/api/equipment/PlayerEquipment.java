package com.herocraftonline.items.api.equipment;

import org.bukkit.entity.Player;

public interface PlayerEquipment extends LivingEntityEquipment<Player>, InventoryEquipment<Player> {


    interface Slot extends LivingEntityEquipment.Slot<Player>, InventoryEquipment.Slot<Player> {

    }
}
