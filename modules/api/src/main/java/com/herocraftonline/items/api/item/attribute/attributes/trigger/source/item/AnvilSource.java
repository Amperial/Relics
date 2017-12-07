/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.trigger.source.item;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.entity.PlayerSource;

/**
 * Provides the player, item being upgraded, and upgrade item from an anvil upgrade.
 *
 * @author Austin Payne
 */
public interface AnvilSource extends ItemSource, PlayerSource {

    Item getUpgradeItem();

    default Item getSourceItem() {
        return getUpgradeItem();
    }

}
