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
package com.herocraftonline.items.api.item.attribute.attributes.stats;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;

/**
 * A base stat attribute implementation to simplify the creation of stat attributes.<br>
 * Handles the attribute's stat type.
 *
 * @param <T> the type of stat
 * @author Austin Payne
 */
public abstract class BaseStatAttribute<T extends StatAttribute<T>> extends BaseAttribute<T> implements StatAttribute<T> {

    private StatType<T> statType;

    public BaseStatAttribute(Item item, String name, AttributeType<T> attributeType, StatType<T> statType) {
        super(item, name, attributeType);

        this.statType = statType;
    }

    @Override
    public StatType<T> getStatType() {
        return statType;
    }

}
