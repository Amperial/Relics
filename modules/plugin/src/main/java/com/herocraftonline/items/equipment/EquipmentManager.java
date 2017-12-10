/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.equipment;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.equipment.Equipment;
import com.herocraftonline.items.api.equipment.EquipmentChangedEvent;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemType;
import com.herocraftonline.items.api.item.attribute.attributes.Durability;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.RelMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EquipmentManager implements com.herocraftonline.items.api.equipment.EquipmentManager, Listener {

    private final ItemPlugin plugin;
    private final Map<UUID, Equipment> equipment;
    private final Map<UUID, Long> equipReplace;

    public EquipmentManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.equipment = new HashMap<>();
        this.equipReplace = new HashMap<>();

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void loadEquipment(Player player) {
        if (!equipment.containsKey(player.getUniqueId())) {
            // Load player equipment
            equipment.put(player.getUniqueId(), new PlayerEquipment(plugin, player));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void unloadEquipment(PlayerQuitEvent event) {
        // Remove and save player equipment
        if (equipment.containsKey(event.getPlayer().getUniqueId())) {
            equipment.remove(event.getPlayer().getUniqueId()).save();
        }
    }

    @Override
    public Equipment getEquipment(Player player) {
        if (!equipment.containsKey(player.getUniqueId())) {
            loadEquipment(player);
        }
        return equipment.get(player.getUniqueId());
    }

    @Override
    public boolean isEquipped(Player player, Item item) {
        return getEquipment(player).isEquipped(item);
    }

    @Override
    public void equip(Player player, Item item, ItemStack itemStack) {
        Messenger messenger = plugin.getMessenger();

        ItemType type = item.getType();
        Equipment playerEquipment = getEquipment(player);
        Optional<Durability> durability = item.getAttribute(Durability.class);
        if (durability.isPresent() && !durability.get().isUsable()) {
            messenger.sendShortErrorMessage(player, RelMessage.ITEM_NEEDSREPAIR, item.getName());
            return;
        }
        if (playerEquipment.hasSlot(type) && item.canEquip(player)) {
            if (playerEquipment.isSlotOpen(type)) {
                playerEquipment.equip(item, itemStack);
                messenger.sendShortMessage(player, RelMessage.ITEM_EQUIPPED, item.getName());

                equipmentChanged(player);
            } else {
                UUID itemId = item.getId();
                if (equipReplace.containsKey(itemId)) {
                    long lastClicked = equipReplace.get(itemId);
                    if (System.currentTimeMillis() - lastClicked < 1000) {
                        playerEquipment.replaceEquip(item, itemStack);
                        messenger.sendShortMessage(player, RelMessage.ITEM_EQUIPPED, item.getName());
                        equipReplace.remove(itemId);

                        equipmentChanged(player);
                        return;
                    }
                }
                equipReplace.put(itemId, System.currentTimeMillis());
                messenger.sendShortErrorMessage(player, RelMessage.ITEM_ALREADYEQUIPPED);
            }
        } else {
            messenger.sendShortErrorMessage(player, RelMessage.ITEM_CANTEQUIP);
        }
    }

    @Override
    public void replaceEquip(Player player, Item item, ItemStack itemStack) {
        Messenger messenger = plugin.getMessenger();
        ItemType type = item.getType();
        Equipment playerEquipment = getEquipment(player);
        if (playerEquipment.hasSlot(type) && item.canEquip(player)) {
            if (playerEquipment.isSlotOpen(type)) {
                playerEquipment.equip(item, itemStack);
            } else {
                playerEquipment.replaceEquip(item, itemStack);
            }
            messenger.sendShortMessage(player, RelMessage.ITEM_EQUIPPED, item.getName());

            equipmentChanged(player);
        } else {
            messenger.sendShortErrorMessage(player, RelMessage.ITEM_CANTEQUIP);
        }
    }

    @Override
    public void unEquip(Player player, Item item, ItemStack itemStack) {
        Messenger messenger = plugin.getMessenger();

        Equipment playerEquipment = getEquipment(player);
        if (playerEquipment.isEquipped(item)) {
            playerEquipment.unEquip(item, itemStack);
            messenger.sendShortMessage(player, RelMessage.ITEM_UNEQUIPPED, item.getName());

            equipmentChanged(player);
        } else {
            messenger.sendShortErrorMessage(player, RelMessage.ITEM_NOTEQUIPPED);
        }
    }

    @Override
    public void unEquipAll(Player player) {
        getEquipment(player).unEquipAll();

        equipmentChanged(player);
    }

    private void equipmentChanged(Player player) {
        Bukkit.getServer().getPluginManager().callEvent(new EquipmentChangedEvent(player));
    }

}
