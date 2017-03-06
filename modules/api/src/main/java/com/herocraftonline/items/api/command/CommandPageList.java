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
package com.herocraftonline.items.api.command;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.message.PageList;

import java.util.ArrayList;
import java.util.List;

/**
 * A PageList that lists all of the commands and their description.
 *
 * @author Austin Payne
 */
public class CommandPageList extends PageList {

    private final List<String> pageNumbersList = new ArrayList<>();

    public CommandPageList(ItemPlugin plugin) {
        super("Commands", 8);

        for (CommandGroup command : plugin.getCommandController().getCommands()) {
            command.getChildren(true).stream()
                    .filter(child -> ((Command) child).isVisible())
                    .forEach(child -> add(Messenger.PRIMARY_COLOR + ((Command) child).getCommandUsage(), Messenger.SECONDARY_COLOR + "-" + ((Command) child).getDescription()));
        }

        int pageAmount = getTotalPages();
        for (int i = 1; i <= pageAmount; i++) {
            pageNumbersList.add(String.valueOf(i));
        }
    }

    /**
     * Gets the page numbers list of the command page list.
     *
     * @return the list of page numbers
     */
    public List<String> getPageNumbersList() {
        return pageNumbersList;
    }

}
