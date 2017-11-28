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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.trigger.Triggerable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * An attribute that executes a command when triggered.
 *
 * @author Austin Payne
 */
public interface Command extends Attribute<Command>, Triggerable {

    /**
     * Gets the command to be executed by the attribute.
     *
     * @return the command string
     */
    String getCommand();

    /**
     * Sets the command to be executed by the attribute.
     *
     * @param command the command string
     */
    void setCommand(String command);

    /**
     * Gets the sender to execute the command.
     *
     * @return the command sender
     */
    Sender getSendAs();

    /**
     * Sets the sender to execute the command.
     *
     * @param sender the command sender
     */
    void setSendAs(Sender sender);

    /**
     * Represents types of senders to execute a command as.
     */
    enum Sender {
        SOURCE,
        SOURCE_OP,
        CONSOLE
    }

    /**
     * Executes the command.
     *
     * @param source the source executing the command attribute
     */
    default void execute(CommandSender source) {
        execute(source, getSendAs());
    }

    /**
     * Executes the command.
     *
     * @param source the source executing the command attribute
     * @param sender the sender the command will be run as
     */
    default void execute(CommandSender source, Sender sender) {
        if (sender == Sender.SOURCE_OP && !source.isOp()) {
            source.setOp(true);
            execute(source, Sender.SOURCE);
            source.setOp(false);
        } else if (sender == Sender.CONSOLE) {
            execute(Bukkit.getConsoleSender(), Sender.SOURCE);
        } else {
            Bukkit.dispatchCommand(source, getCommand());
        }
    }

}
