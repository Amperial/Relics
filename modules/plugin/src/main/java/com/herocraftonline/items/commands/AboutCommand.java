/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.commands;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.command.Command;
import com.herocraftonline.items.api.message.Messenger;
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
        setDescription("Lists some information about relics.");
        setCommandUsage("/relics");
        setPermission(new Permission("relics.about", PermissionDefault.TRUE));
        setPlayerOnly(false);

        header = Messenger.HIGHLIGHT_COLOR + "<-------<| " + Messenger.PRIMARY_COLOR + "About Relics " + Messenger.HIGHLIGHT_COLOR + "|>------->";
        info.add(Messenger.SECONDARY_COLOR + "Author: " + StringUtils.join(getPlugin().getDescription().getAuthors(), ", "));
        info.add(Messenger.SECONDARY_COLOR + "Version: " + getPlugin().getDescription().getVersion());
        info.add(Messenger.SECONDARY_COLOR + "Help: /relics help [page]");
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        sender.sendMessage(header);
        info.forEach(sender::sendMessage);
    }

    /**
     * Adds more info to the about command.
     *
     * @param message the info message to add
     */
    public void addInfo(String message) {
        info.add(message);
    }

}
