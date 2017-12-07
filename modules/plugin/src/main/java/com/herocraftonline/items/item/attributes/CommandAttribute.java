/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.attributes.Command;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.CommandSenderSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.item.DefaultAttributes;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

public class CommandAttribute extends BaseAttribute<Command> implements Command {

    private Value<String> command;
    private Sender sender;

    public CommandAttribute(Item item, String name, Value<String> command, Sender sender) {
        super(item, name, DefaultAttributes.COMMAND);

        this.command = command;
        this.sender = sender;
    }

    @Override
    public String getCommand() {
        return command.getValue();
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
    public boolean canTrigger(TriggerSource source) {
        return source instanceof CommandSenderSource;
    }

    @Override
    public TriggerResult onTrigger(TriggerSource source) {
        Optional<CommandSenderSource> senderSource = source.ofType(CommandSenderSource.class);
        if (senderSource.isPresent()) {
            CommandSender commandSender = senderSource.get().getSender();
            execute(commandSender);
            return TriggerResult.TRIGGERED;
        }
        return TriggerResult.NOT_TRIGGERED;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        command.saveToNBT(compound);
        compound.setString("sender", getSendAs().name());
    }

    public static class Factory extends BaseAttributeFactory<Command> {
        private static final StoredValue<String> COMMAND = new StoredValue<>("command", StoredValue.STRING, "");

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Command loadFromConfig(Item item, String name, ConfigurationSection config) {
            // Load command and sender
            Value<String> command = COMMAND.loadFromConfig(item, config);
            Sender sender = Sender.valueOf(config.getString("sender", "SOURCE"));

            // Load command attribute
            return new CommandAttribute(item, name, command, sender);
        }

        @Override
        public Command loadFromNBT(Item item, String name, NBTTagCompound compound) {
            // Load command and sender
            Value<String> command = COMMAND.loadFromNBT(item, compound);
            Sender sender = Sender.valueOf(compound.getString("sender"));

            // Load command attribute
            return new CommandAttribute(item, name, command, sender);
        }
    }

}
