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
import org.bukkit.command.CommandSender;

public class CommandSenderSource extends SingleSource {

    private final CommandSender source;

    public CommandSenderSource(Item item, CommandSender source) {
        super(item, source);

        this.source = source;
    }

    @Override
    public CommandSender getSource() {
        return source;
    }

}
