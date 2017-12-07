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
package com.herocraftonline.items.item.attributes.triggers.effects;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.PlayerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.event.PlayerInteractSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggerables.effects.Effect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import com.herocraftonline.items.nms.NMSHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Optional;

public class ArmSwingEffectAttribute extends BaseAttribute<ArmSwingEffectAttribute> implements Effect<ArmSwingEffectAttribute> {

    private final boolean oppositeArm;

    public ArmSwingEffectAttribute(Item item, String name, boolean oppositeArm) {
        super(item, name, DefaultAttributes.ARM_SWING_EFFECT);

        this.oppositeArm = oppositeArm;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source instanceof PlayerSource;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setBoolean("opposite-arm", oppositeArm);
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<PlayerSource> playerSourceOptional = source.ofType(PlayerSource.class);
        if (playerSourceOptional.isPresent()) {
            PlayerSource playerSource = playerSourceOptional.get();
            boolean mainArm = !oppositeArm;
            if (playerSource instanceof PlayerInteractSource) {
                PlayerInteractSource playerInteractSource = (PlayerInteractSource) playerSource;
                if (playerInteractSource.getEvent().getHand() != EquipmentSlot.HAND) {
                    mainArm = !mainArm;
                }
            }
            NMSHandler.instance().playArmSwing(playerSource.getPlayer(), mainArm);
            return TriggerResult.TRIGGERED;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    public static class Factory extends BaseAttributeFactory<ArmSwingEffectAttribute> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public ArmSwingEffectAttribute loadFromConfig(Item item, String name, ConfigurationSection config) {
            boolean oppositeArm = config.getBoolean("opposite-arm", false);

            return new ArmSwingEffectAttribute(item, name, oppositeArm);
        }

        @Override
        public ArmSwingEffectAttribute loadFromNBT(Item item, String name, NBTTagCompound compound) {
            boolean oppositeArm = compound.getBoolean("opposite-arm");

            return new ArmSwingEffectAttribute(item, name, oppositeArm);
        }
    }

}
