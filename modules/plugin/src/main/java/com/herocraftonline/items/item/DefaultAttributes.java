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
package com.herocraftonline.items.item;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.*;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Blueprint;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Reagent;
import com.herocraftonline.items.api.item.attribute.attributes.effects.HealEffect;
import com.herocraftonline.items.api.item.attribute.attributes.effects.PotionEffect;
import com.herocraftonline.items.api.item.attribute.attributes.effects.SoundEffect;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.gems.Socket;
import com.herocraftonline.items.api.item.attribute.attributes.projectiles.Velocity;
import com.herocraftonline.items.api.item.attribute.attributes.requirements.LevelRequirement;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.PlayerInteract;
import com.herocraftonline.items.api.item.attribute.attributes.triggers.conditions.Cooldown;
import com.herocraftonline.items.item.attributes.*;
import com.herocraftonline.items.item.attributes.triggers.conditions.CooldownCondition;
import com.herocraftonline.items.item.attributes.triggers.PlayerInteractTrigger;
import com.herocraftonline.items.item.attributes.triggers.conditions.PermissionCondition;
import com.herocraftonline.items.item.attributes.triggers.effects.HealEffectAttribute;
import com.herocraftonline.items.item.attributes.triggers.effects.PotionEffectAttribute;
import com.herocraftonline.items.item.attributes.triggers.effects.SoundEffectAttribute;
import com.herocraftonline.items.item.attributes.triggers.transforms.TargetBlockTransform;

import java.util.ArrayList;
import java.util.Collection;

public final class DefaultAttributes {

    public static final AttributeType<Blueprint> BLUEPRINT = new BaseAttributeType<>("blueprint", 5, BlueprintAttribute.Factory::new);
    public static final AttributeType<Command> COMMAND = new BaseAttributeType<>("command", Integer.MAX_VALUE, CommandAttribute.Factory::new);
    public static final AttributeType<Cooldown> COOLDOWN = new BaseAttributeType<>("cooldown-condition", Integer.MAX_VALUE, CooldownCondition.Factory::new);
    public static final AttributeType<Minecraft> DAMAGE = new BaseAttributeType<>("damage", Integer.MAX_VALUE, DamageAttribute.Factory::new);
    public static final AttributeType<Durability> DURABILITY = new BaseAttributeType<>("durability", 8, DurabilityAttribute.Factory::new);
    public static final AttributeType<Gem> GEM = new BaseAttributeType<>("gem", 7, GemAttribute.Factory::new);
    public static final AttributeType<Group> GROUP = new BaseAttributeType<>("group", 0, GroupAttribute.Factory::new);
    public static final AttributeType<HealEffect> HEAL_EFFECT = new BaseAttributeType<>("heal-effect", Integer.MAX_VALUE, HealEffectAttribute.Factory::new);
    public static final AttributeType<Identifiable> IDENTIFIABLE = new BaseAttributeType<>("identifiable", Integer.MAX_VALUE, IdentifiableAttribute.Factory::new);
    public static final AttributeType<LaunchEntityAttribute> LAUNCH_ENTITY = new BaseAttributeType<>("launch-entity", Integer.MAX_VALUE, LaunchEntityAttribute.Factory::new);
    public static final AttributeType<Level> LEVEL = new BaseAttributeType<>("level", 2, LevelAttribute.Factory::new);
    public static final AttributeType<LevelRequirement> LEVEL_REQUIREMENT = new BaseAttributeType<>("level-requirement", Integer.MIN_VALUE, LevelRequirementAttribute.Factory::new);
    public static final AttributeType<Minecraft> MINECRAFT = new BaseAttributeType<>("minecraft", Integer.MAX_VALUE, MinecraftAttribute.Factory::new);
    public static final AttributeType<Model> MODEL = new BaseAttributeType<>("model", Integer.MAX_VALUE, ModelAttribute.Factory::new);
    public static final AttributeType<PermissionCondition> PERMISSION_CONDITION = new BaseAttributeType<>("permission-condition", Integer.MAX_VALUE, PermissionCondition.Factory::new);
    public static final AttributeType<PlayerInteract> PLAYER_INTERACT = new BaseAttributeType<>("player-interact", Integer.MAX_VALUE, PlayerInteractTrigger.Factory::new);
    public static final AttributeType<PotionEffect> POTION_EFFECT = new BaseAttributeType<>("potion-effect", Integer.MAX_VALUE, PotionEffectAttribute.Factory::new);
    public static final AttributeType<Rarity> RARITY = new BaseAttributeType<>("rarity", 1, RarityAttribute.Factory::new);
    public static final AttributeType<Reagent> REAGENT = new BaseAttributeType<>("reagent", 3, ReagentAttribute.Factory::new);
    public static final AttributeType<SmiteAttribute> SMITE = new BaseAttributeType<>("smite", Integer.MAX_VALUE, SmiteAttribute.Factory::new);
    public static final AttributeType<Socket> SOCKET = new BaseAttributeType<>("socket", 6, SocketAttribute.Factory::new);
    public static final AttributeType<Soulbound> SOULBOUND = new BaseAttributeType<>("soulbound", 9, SoulboundAttribute.Factory::new);
    public static final AttributeType<SoundEffect> SOUND_EFFECT = new BaseAttributeType<>("sound-effect", Integer.MAX_VALUE, SoundEffectAttribute.Factory::new);
    public static final AttributeType<TargetBlockTransform> TARGET_BLOCK_TRANSFORM = new BaseAttributeType<>("target-block-transform", Integer.MAX_VALUE, TargetBlockTransform.Factory::new);
    public static final AttributeType<Text> TEXT = new BaseAttributeType<>("text", 4, TextAttribute.Factory::new);
    public static final AttributeType<Velocity> VELOCITY = new BaseAttributeType<>("velocity", Integer.MAX_VALUE, VelocityAttribute.Factory::new);

    private static final Collection<AttributeType> types = new ArrayList<>();

    static {
        types.add(BLUEPRINT);
        types.add(COMMAND);
        types.add(COOLDOWN);
        types.add(DAMAGE);
        types.add(DURABILITY);
        types.add(GEM);
        types.add(GROUP);
        types.add(HEAL_EFFECT);
        types.add(IDENTIFIABLE);
        types.add(LAUNCH_ENTITY);
        types.add(LEVEL);
        types.add(LEVEL_REQUIREMENT);
        types.add(MINECRAFT);
        types.add(MODEL);
        types.add(PERMISSION_CONDITION);
        types.add(PLAYER_INTERACT);
        types.add(POTION_EFFECT);
        types.add(RARITY);
        types.add(REAGENT);
        types.add(SMITE);
        types.add(SOCKET);
        types.add(SOULBOUND);
        types.add(SOUND_EFFECT);
        types.add(TARGET_BLOCK_TRANSFORM);
        types.add(TEXT);
        types.add(VELOCITY);
    }

    private DefaultAttributes() {
    }

    public static Collection<AttributeType> getTypes() {
        return types;
    }

    public static void loadFactories(ItemPlugin plugin) {
        types.stream().filter(type -> type instanceof BaseAttributeType).forEach(type -> ((BaseAttributeType) type).loadFactory(plugin));
    }

}
