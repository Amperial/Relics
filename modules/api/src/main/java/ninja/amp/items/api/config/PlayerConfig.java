/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.api.config;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerConfig implements Config {

    private final UUID playerId;
    private final String fileName;

    public PlayerConfig(Player player) {
        this.playerId = player.getUniqueId();
        this.fileName = "players/" + ConfigManager.getNestedPath(playerId.toString()) + ".yml";
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PlayerConfig && playerId.equals(((PlayerConfig) obj).getPlayerId());
    }

    @Override
    public int hashCode() {
        return playerId.hashCode();
    }

}
