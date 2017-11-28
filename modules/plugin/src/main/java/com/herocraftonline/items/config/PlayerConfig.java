/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.config;

import com.herocraftonline.items.api.storage.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Player configuration files used in the relics plugin.
 *
 * @author Austin Payne
 */
public class PlayerConfig implements Config {

    private final UUID playerId;
    private final String fileName;

    public PlayerConfig(Player player) {
        this.playerId = player.getUniqueId();
        this.fileName = "players/" + ConfigManager.getNestedPath(player.getUniqueId().toString()) + ".yml";
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
        return obj != null && obj instanceof PlayerConfig && fileName.equals(((PlayerConfig) obj).getFileName());
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

}
