/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.equipment;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.equipment.Equipment;
import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.ItemType;
import ninja.amp.items.api.message.AIMessage;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EquipmentManager implements ninja.amp.items.api.equipment.EquipmentManager, Listener {

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
        // Load player equipment
        equipment.put(player.getUniqueId(), new PlayerEquipment(plugin, player));
    }

    @EventHandler
    public void unloadEquipment(PlayerQuitEvent event) {
        // Remove and save player equipment
        equipment.remove(event.getPlayer().getUniqueId()).save();
    }

    @Override
    public Equipment getEquipment(Player player) {
        return equipment.get(player.getUniqueId());
    }

    @Override
    public boolean isEquipped(Player player, Item item) {
        return getEquipment(player).isEquipped(item);
    }

    @Override
    public boolean canEquip(Player player, Item item) {
        return getEquipment(player).hasSlot(item.getType()) && item.canEquip(player);
    }

    @Override
    public void equip(Player player, Item item) {
        Messenger messenger = plugin.getMessenger();

        ItemType type = item.getType();
        Equipment playerEquipment = getEquipment(player);
        if (playerEquipment.hasSlot(type) && item.canEquip(player)) {
            if (playerEquipment.isSlotOpen(type)) {
                playerEquipment.equip(item);
                messenger.sendShortMessage(player, AIMessage.ITEM_EQUIPPED, item.getName());
            } else {
                UUID itemId = item.getId();
                if (equipReplace.containsKey(itemId)) {
                    long lastClicked = equipReplace.get(itemId);
                    if (System.currentTimeMillis() - lastClicked < 5000) {
                        playerEquipment.replaceEquip(item);
                        messenger.sendShortMessage(player, AIMessage.ITEM_EQUIPPED, item.getName());
                        equipReplace.remove(itemId);
                        return;
                    }
                }
                equipReplace.put(itemId, System.currentTimeMillis());
                messenger.sendShortErrorMessage(player, AIMessage.ITEM_ALREADYEQUIPPED);
            }
        } else {
            messenger.sendShortErrorMessage(player, AIMessage.ITEM_CANTEQUIP);
        }
    }

    @Override
    public void replaceEquip(Player player, Item item) {
        Messenger messenger = plugin.getMessenger();
        ItemType type = item.getType();
        Equipment playerEquipment = getEquipment(player);
        if (playerEquipment.hasSlot(type) && item.canEquip(player)) {
            if (playerEquipment.isSlotOpen(type)) {
                playerEquipment.equip(item);
            } else {
                playerEquipment.replaceEquip(item);
            }
            messenger.sendShortMessage(player, AIMessage.ITEM_EQUIPPED, item.getName());
        } else {
            messenger.sendShortErrorMessage(player, AIMessage.ITEM_CANTEQUIP);
        }
    }

    @Override
    public void unEquip(Player player, Item item) {
        Messenger messenger = plugin.getMessenger();

        Equipment playerEquipment = getEquipment(player);
        if (playerEquipment.isEquipped(item)) {
            playerEquipment.unEquip(item);
            messenger.sendShortMessage(player, AIMessage.ITEM_UNEQUIPPED, item.getName());
        } else {
            messenger.sendShortErrorMessage(player, AIMessage.ITEM_NOTEQUIPPED);
        }
    }

    @Override
    public void unEquipAll(Player player) {
        getEquipment(player).unEquipAll();
    }

}
