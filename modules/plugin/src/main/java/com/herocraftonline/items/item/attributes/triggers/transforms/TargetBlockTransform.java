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
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.LivingEntitySource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.transforms.BaseTransform;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import com.herocraftonline.items.item.attributes.triggers.sources.LocationSource;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TargetBlockTransform extends BaseTransform<TargetBlockTransform> {

    private static final Set<Material> AIR = Collections.singleton(Material.AIR);

    private final int range;

    public TargetBlockTransform(Item item, String name, List<String> targets, boolean separate, int range) {
        super(item, name, DefaultAttributes.TARGET_BLOCK_TRANSFORM, targets, separate);

        this.range = range;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("range", range);
    }

    @Override
    public Optional<TriggerSource> transform(TriggerSource source) {
        return source.ofType(LivingEntitySource.class)
                .map(entitySource -> entitySource.getEntity().getTargetBlock(AIR, range))
                .map(block -> new LocationSource(source.getItem(), block.getLocation()));
    }

    public static class Factory extends BaseTriggerFactory<TargetBlockTransform> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public TargetBlockTransform loadFromConfig(Item item, String name, ConfigurationSection config) {
            List<String> targets = loadTargetsFromConfig(config);
            boolean separate = config.getBoolean("separate", true);
            int range = Math.abs(config.getInt("range", 64));

            return new TargetBlockTransform(item, name, targets, separate, range);
        }

        @Override
        public TargetBlockTransform loadFromNBT(Item item, String name, NBTTagCompound compound) {
            List<String> targets = loadTargetsFromNBT(compound);
            boolean separate = compound.getBoolean("separate");
            int range = compound.getInt("range");

            return new TargetBlockTransform(item, name, targets, separate, range);
        }
    }

}
