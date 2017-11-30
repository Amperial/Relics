/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.triggers;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.trigger.TriggerResult;
import com.herocraftonline.items.api.item.trigger.Triggerable;
import com.herocraftonline.items.api.item.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

/**
 * A base trigger attribute implementation to simplify the creation of trigger attributes.<br>
 * Helps manage keeping track of and triggering the trigger's target triggerable attributes.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public abstract class BaseTrigger<T extends Trigger<T>> extends BaseAttribute<T> implements Trigger<T> {

    private final Set<String> targets;

    public BaseTrigger(String name, AttributeType<T> type, Set<String> targets) {
        super(name, type);

        this.targets = targets;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        return source.getItem().getAttributesDeep(attribute -> getTargets().contains(attribute.getName())).stream()
                .filter(attribute -> attribute instanceof Triggerable)
                .allMatch(attribute -> ((Triggerable) attribute).canTrigger(source));
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        return source.getItem().getAttributesDeep(attribute -> getTargets().contains(attribute.getName())).stream()
                .filter(attribute -> attribute instanceof Triggerable)
                .map(attribute -> ((Triggerable) attribute).onTrigger(source))
                .reduce(TriggerResult.COMBINE).orElse(TriggerResult.NOT_TRIGGERED);
    }

    @Override
    public Set<String> getTargets() {
        return targets;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagList targets = NBTTagList.create();
        getTargets().forEach(target -> targets.addBase(NBTTagString.create(target)));
        compound.setBase("targets", targets);
    }

    public abstract static class BaseTriggerFactory<T extends Trigger<T>> extends BaseAttributeFactory<T> {
        public BaseTriggerFactory(ItemPlugin plugin) {
            super(plugin);
        }

        protected Set<String> loadTargetsFromConfig(ConfigurationSection config) {
            return new HashSet<>(config.getStringList("targets"));
        }

        protected Set<String> loadTargetsFromNBT(NBTTagCompound compound) {
            Set<String> targets = new HashSet<>();
            NBTTagList targetList = compound.getList("targets", NBTTagString.create().getTypeId());
            for (int i = 0; i < targetList.size(); i++) {
                targets.add(targetList.getString(i));
            }
            return targets;
        }
    }

}
