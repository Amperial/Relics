package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.effects.PotionEffect;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectAttribute extends BaseAttribute<PotionEffect> implements PotionEffect {

    private org.bukkit.potion.PotionEffect effect;

    public PotionEffectAttribute(String name, org.bukkit.potion.PotionEffect effect) {
        super(name, DefaultAttribute.POTION_EFFECT);

        this.effect = effect;
    }

    @Override
    public org.bukkit.potion.PotionEffect getEffect() {
        return effect;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public void play(Player player) {
        getEffect().apply(player);
    }

    @Override
    public boolean canEquip(Player player) {
        return true;
    }

    @Override
    public boolean onEquip(Player player) {
        play(player);
        return false;
    }

    @Override
    public boolean onUnEquip(Player player) {
        player.removePotionEffect(getEffect().getType());
        return false;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        org.bukkit.potion.PotionEffect effect = getEffect();
        compound.setString("potion-type", effect.getType().getName());
        compound.setInt("duration", effect.getDuration());
        compound.setInt("amplifier", effect.getAmplifier());
        compound.setBoolean("ambient", effect.isAmbient());
        compound.setBoolean("particles", effect.hasParticles());
        if (effect.getColor() != null) {
            compound.setInt("color", effect.getColor().asRGB());
        }
    }

    public static class Factory extends BaseAttributeFactory<PotionEffect> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public PotionEffect loadFromConfig(String name, ConfigurationSection config) {
            // Load potion effect
            PotionEffectType type = PotionEffectType.getByName(config.getString("potion-type"));
            int duration = config.getInt("duration", Integer.MAX_VALUE);
            int amplifier = config.getInt("amplifier", 1);
            boolean ambient = config.getBoolean("ambient", true);
            boolean particles = config.getBoolean("particles", true);
            Color color = config.getColor("color", null);
            org.bukkit.potion.PotionEffect effect =
                    new org.bukkit.potion.PotionEffect(type, duration, amplifier, ambient, particles, color);

            // Load potion effect attribute
            return new PotionEffectAttribute(name, effect);
        }

        @Override
        public PotionEffect loadFromNBT(String name, NBTTagCompound compound) {
            // Load potion effect
            PotionEffectType type = PotionEffectType.getByName(compound.getString("potion-type"));
            int duration = compound.getInt("duration");
            int amplifier = compound.getInt("amplifier");
            boolean ambient = compound.getBoolean("ambient");
            boolean particles = compound.getBoolean("particles");
            Color color = compound.hasKey("color") ? Color.fromRGB(compound.getInt("color")) : null;
            org.bukkit.potion.PotionEffect effect =
                    new org.bukkit.potion.PotionEffect(type, duration, amplifier, ambient, particles, color);

            // Load potion effect attribute
            return new PotionEffectAttribute(name, effect);
        }
    }

}
