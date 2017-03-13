package com.herocraftonline.items.api.item.attribute.attributes.effects;

import com.herocraftonline.items.api.item.Equippable;

public interface PotionEffect extends Effect<PotionEffect>, Equippable {

    org.bukkit.potion.PotionEffect getEffect();

}
