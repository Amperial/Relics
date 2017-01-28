/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.command;

import ninja.amp.items.AmpItems;
import ninja.amp.items.message.Messenger;
import ninja.amp.items.message.PageList;

import java.util.ArrayList;
import java.util.List;

/**
 * A PageList that lists all of the commands and their description.
 *
 * @author Austin Payne
 */
public class CommandPageList extends PageList {

    private final List<String> pageNumbersList = new ArrayList<>();

    public CommandPageList(AmpItems plugin) {
        super("Commands", 8);

        for (CommandGroup command : plugin.getCommandController().getCommands()) {
            command.getChildren(true).stream()
                    .filter(child -> ((Command) child).getVisible())
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
     * @return The list of page numbers
     */
    public List<String> getPageNumbersList() {
        return pageNumbersList;
    }

}
