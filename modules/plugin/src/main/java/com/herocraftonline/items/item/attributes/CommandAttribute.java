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
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.Command;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.configuration.ConfigurationSection;

public class CommandAttribute extends BaseAttribute<Command> implements Command {

    private String command;
    private Sender sender;

    public CommandAttribute(String name, String command, Sender sender) {
        super(name, DefaultAttributes.COMMAND);

        this.command = command;
        this.sender = sender;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public Sender getSendAs() {
        return sender;
    }

    @Override
    public void setSendAs(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("command", getCommand());
        compound.setString("sender", getSendAs().name());
    }

    public static class Factory extends BaseAttributeFactory<Command> {
        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Command loadFromConfig(String name, ConfigurationSection config) {
            // Load command and sender
            String command = config.getString("command");
            Sender sender = Sender.valueOf(config.getString("sender", "SOURCE"));

            // Load command attribute
            return new CommandAttribute(name, command, sender);
        }

        @Override
        public Command loadFromNBT(String name, NBTTagCompound compound) {
            // Load command and sender
            String command = compound.getString("command");
            Sender sender = Sender.valueOf(compound.getString("sender"));

            // Load command attribute
            return new CommandAttribute(name, command, sender);
        }
    }

}
