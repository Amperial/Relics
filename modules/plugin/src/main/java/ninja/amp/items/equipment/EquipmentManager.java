/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
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
