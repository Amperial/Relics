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
package com.herocraftonline.items.api.item.trigger.sources;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.trigger.TriggerSource;

public class SingleSource extends TriggerSource {

    private final Object source;

    public SingleSource(Item item, Object source) {
        super(item);

        this.source = source;
    }

    public Object getSource() {
        return source;
    }

}
