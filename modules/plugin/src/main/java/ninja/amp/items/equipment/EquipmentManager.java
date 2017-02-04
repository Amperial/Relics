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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EquipmentManager implements ninja.amp.items.api.equipment.EquipmentManager, Listener {

    private final ItemPlugin plugin;
    private final Map<UUID, Equipment> equipment;

    public EquipmentManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.equipment = new HashMap<>();

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Equipment getEquipment(Player player) {
        return equipment.get(player.getUniqueId());
    }

    @Override
    public boolean canEquip(Player player, Item item) {
        Equipment playerEquipment = getEquipment(player);
        return playerEquipment.hasSlot(item.getType());
    }

    @Override
    public void equip(Player player, Item item) {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Load player equipment
        Player player = event.getPlayer();
        equipment.put(player.getUniqueId(), new PlayerEquipment(plugin, player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove and save player equipment
        equipment.remove(event.getPlayer().getUniqueId()).save();
    }

}
