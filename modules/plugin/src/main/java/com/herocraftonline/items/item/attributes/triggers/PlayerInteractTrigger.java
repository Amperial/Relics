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
package com.herocraftonline.items.item.attributes.triggers;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.PlayerInteract;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.event.PlayerInteractSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerInteractTrigger extends BaseTrigger<PlayerInteract> implements PlayerInteract {

    // TODO: Triggers should not have conditions, move to InteractActionCondition
    private final Set<Action> actions;

    public PlayerInteractTrigger(Item item, String name, List<String> targets, boolean separate, Set<Action> actions) {
        super(item, name, DefaultAttributes.PLAYER_INTERACT, targets, separate);

        this.actions = actions;
    }

    @Override
    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        Optional<PlayerInteractSource> playerInteractSource = source.ofType(PlayerInteractSource.class);
        return playerInteractSource.isPresent()
                && getActions().contains(playerInteractSource.get().getEvent().getAction())
                && super.canTrigger(source);
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<PlayerInteractSource> playerInteractSource = source.ofType(PlayerInteractSource.class);
        return playerInteractSource.isPresent() && getActions().contains(playerInteractSource.get().getEvent().getAction())
                ? super.onTrigger(source)
                : TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setBoolean("separate", handleSeparately());
        NBTTagList actions = NBTTagList.create();
        getActions().forEach(action -> actions.addBase(NBTTagString.create(action.name())));
        compound.setBase("actions", actions);
    }

    public static class Factory extends BaseTriggerFactory<PlayerInteract> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public PlayerInteract loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);
            Set<Action> actions = EnumSet.noneOf(Action.class);
            if (config.isString("action")) {
                actions.add(Action.valueOf(config.getString("action")));
            } else {
                actions.addAll(config.getStringList("actions").stream().map(Action::valueOf).collect(Collectors.toList()));
            }

            // Load player interact trigger
            return new PlayerInteractTrigger(item, name, targets, separate, actions);
        }

        @Override
        public PlayerInteract loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            Set<Action> actions = EnumSet.noneOf(Action.class);
            NBTTagList actionList = compound.getList("actions", NBTTagString.create().getTypeId());
            for (int i = 0; i < actionList.size(); i++) {
                actions.add(Action.valueOf(actionList.getString(i)));
            }

            // Load player interact trigger
            return new PlayerInteractTrigger(item, name, targets, separate, actions);
        }
    }

}
