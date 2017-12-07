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
package com.herocraftonline.items.item.attributes.triggers.transforms;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.transforms.BaseTransform;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import com.herocraftonline.items.item.attributes.triggers.sources.entity.PlayerSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetPlayerTransform extends BaseTransform<GetPlayerTransform> {

    private final UUID playerId;
    private final String playerName;

    public GetPlayerTransform(Item item, String name, List<String> targets, boolean separate, UUID playerId, String playerName) {
        super(item, name, DefaultAttributes.GET_PLAYER_TRANSFORM, targets, separate);

        this.playerId = playerId;
        this.playerName = playerName;
    }

    private Optional<Player> getPlayer() {
        Player player = playerId == null ? null : Bukkit.getPlayer(playerId);
        if (player == null) {
            player = playerName == null ? null : Bukkit.getPlayerExact(playerName);
        }
        return Optional.ofNullable(player);
    }

    @Override
    public Optional<TriggerSource> transform(TriggerSource source) {
        return getPlayer().map(player -> new PlayerSource(source.getItem(), player));
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        if (playerId != null) {
            compound.setString("player-id", playerId.toString());
        }
        if (playerName != null) {
            compound.setString("player-name", playerName);
        }
    }

    public static class Factory extends BaseTriggerFactory<GetPlayerTransform> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public GetPlayerTransform loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", true);
            UUID playerId = config.isString("player-id") ? UUID.fromString(config.getString("player-id")) : null;
            String playerName = config.getString("player-name");

            return new GetPlayerTransform(item, name, targets, separate, playerId, playerName);
        }

        @Override
        public GetPlayerTransform loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            UUID playerId = compound.hasKey("player-id") ? UUID.fromString(compound.getString("player-id")) : null;
            String playerName = compound.hasKey("player-name") ? compound.getString("player-name") : null;

            return new GetPlayerTransform(item, name, targets, separate, playerId, playerName);
        }
    }

}
