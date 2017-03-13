package com.herocraftonline.items.api.item.attribute.attributes.effects;

import com.herocraftonline.items.api.item.attribute.Attribute;
import org.bukkit.entity.Player;

public interface Effect<T extends Attribute<T>> extends Attribute<T> {

    boolean isGlobal();

    void play(Player player);

}
