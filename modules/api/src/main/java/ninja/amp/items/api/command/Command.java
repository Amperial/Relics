/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.command;

import ninja.amp.items.api.ItemPlugin;
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
     * @param plugin The item plugin instance
     * @param name   The name of the command
     */
    public Command(ItemPlugin plugin, String name) {
        super(plugin, name);
    }

    /**
     * Gets the command's command usage.
     *
     * @return The command's command usage
     */
    public String getCommandUsage() {
        return commandUsage;
    }

    /**
     * Sets the command's command usage.
     *
     * @param commandUsage The command usage
     */
    public void setCommandUsage(String commandUsage) {
        this.commandUsage = commandUsage;
    }

    /**
     * Gets the command's description.
     *
     * @return The command's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the command's description.
     *
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the command's visibility.
     *
     * @return The command's visibility
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Sets the command's visibility.
     *
     * @param visible The visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public abstract void execute(String command, CommandSender sender, List<String> args);

}
