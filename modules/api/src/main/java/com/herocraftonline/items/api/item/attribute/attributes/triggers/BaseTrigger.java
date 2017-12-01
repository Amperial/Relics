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
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.nbt.NBTTagString;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A base trigger attribute implementation to simplify the creation of trigger attributes.<br>
 * Helps manage keeping track of and triggering the trigger's target triggerable attributes.
 *
 * @author Austin Payne
 */
public abstract class BaseTrigger<T extends Trigger<T>> extends BaseAttribute<T> implements Trigger<T> {

    private final List<String> targets;
    private final boolean separate;

    public BaseTrigger(Item item, String name, AttributeType<T> type, List<String> targets, boolean separate) {
        super(item, name, type);

        this.targets = targets;
        this.separate = separate;
    }

    @Override
    public boolean canTrigger(TriggerSource source) {
        Stream<Triggerable> targets = getTargets().stream().map(target -> source.getItem().getAttributeDeep(Triggerable.class, target)).filter(Optional::isPresent).map(Optional::get);
        return handleSeparately() ? targets.anyMatch(triggerable -> triggerable.canTrigger(source)) : targets.allMatch(triggerable -> triggerable.canTrigger(source));
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Stream<Triggerable> targets = getTargets().stream().map(target -> source.getItem().getAttributeDeep(Triggerable.class, target)).filter(Optional::isPresent).map(Optional::get);
        return targets.map(triggerable -> triggerable.onTrigger(source)).reduce(handleSeparately() ? TriggerResult.SEPARATE : TriggerResult.COMBINED).orElse(TriggerResult.NOT_TRIGGERED);
    }

    @Override
    public List<String> getTargets() {
        return targets;
    }

    @Override
    public boolean handleSeparately() {
        return separate;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagList targets = NBTTagList.create();
        getTargets().forEach(target -> targets.addBase(NBTTagString.create(target)));
        compound.setBase("targets", targets);
        compound.setBoolean("separate", handleSeparately());
    }

    public abstract static class BaseTriggerFactory<T extends Trigger<T>> extends BaseAttributeFactory<T> {
        public BaseTriggerFactory(ItemPlugin plugin) {
            super(plugin);
        }

        protected List<String> loadTargetsFromConfig(ConfigurationSection config) {
            if (config.isString("target")) {
                List<String> target = new ArrayList<>();
                target.add(config.getString("target"));
                return target;
            } else {
                return config.getStringList("targets");
            }
        }

        protected List<String> loadTargetsFromNBT(NBTTagCompound compound) {
            List<String> targets = new ArrayList<>();
            NBTTagList targetList = compound.getList("targets", NBTTagString.create().getTypeId());
            for (int i = 0; i < targetList.size(); i++) {
                targets.add(targetList.getString(i));
            }
            return targets;
        }
    }

}
