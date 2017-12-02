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
package com.herocraftonline.items.api.command;

import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * A command that can contain child commands and be executed.
 *
 * @author Austin Payne
 */
public abstract class Command extends CommandGroup {

    private String commandUsage;
    private String description;
    private boolean visible = true;

    /**
     * Creates a new command.
     *
     * @param plugin the item plugin instance
     * @param name   the name of the command
     */
    public Command(ItemPlugin plugin, String name) {
        super(plugin, name);
    }

    /**
     * Gets the command's command usage.
     *
     * @return the command's command usage
     */
    public String getCommandUsage() {
        return commandUsage;
    }

    /**
     * Sets the command's command usage.
     *
     * @param commandUsage the command usage
     */
    public void setCommandUsage(String commandUsage) {
        this.commandUsage = commandUsage;
    }

    /**
     * Gets the command's description.
     *
     * @return the command's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the command's description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the command's visibility.
     *
     * @return the command's visibility
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the command's visibility.
     *
     * @param visible the visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public abstract void execute(String command, CommandSender sender, List<String> args);

}
