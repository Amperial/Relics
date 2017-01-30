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
package ninja.amp.items.commands;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.message.Messenger;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * A command that lists some information about the plugin.
 *
 * @author Austin Payne
 */
public class AboutCommand extends Command {

    private final String header;
    private final List<String> info = new ArrayList<>();

    public AboutCommand(ItemPlugin plugin) {
        super(plugin, "");
        setDescription("Lists some information about amp items.");
        setCommandUsage("/aitem");
        setPermission(new Permission("ampitems.about", PermissionDefault.TRUE));
        setPlayerOnly(false);

        header = Messenger.HIGHLIGHT_COLOR + "<-------<| " + Messenger.PRIMARY_COLOR + "About AmpItems " + Messenger.HIGHLIGHT_COLOR + "|>------->";
        info.add(Messenger.SECONDARY_COLOR + "Author: " + StringUtils.join(getPlugin().getDescription().getAuthors(), ", "));
        info.add(Messenger.SECONDARY_COLOR + "Version: " + getPlugin().getDescription().getVersion());
        info.add(Messenger.SECONDARY_COLOR + "Help: /aitem help [page]");
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        sender.sendMessage(header);
        info.forEach(sender::sendMessage);
    }

    /**
     * Adds more info to the about command.
     *
     * @param message The info message to add
     */
    public void addInfo(String message) {
        info.add(message);
    }

}
