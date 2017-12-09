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
package com.herocraftonline.items.item.attributes.triggers;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.BaseTrigger;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggers.AttackEntity;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class AttackEntityTrigger extends BaseTrigger<AttackEntity> implements AttackEntity {

    public AttackEntityTrigger(Item item, String name, List<String> targets, boolean separate) {
        super(item, name, DefaultAttributes.ATTACK_ENTITY_TRIGGER, targets, separate);
    }

    public static class Factory extends BaseTriggerFactory<AttackEntity> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public AttackEntity loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", false);

            return new AttackEntityTrigger(item, name, targets, separate);
        }

        @Override
        public AttackEntity loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");

            return new AttackEntityTrigger(item, name, targets, separate);
        }
    }

}
