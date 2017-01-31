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
package ninja.amp.items;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.command.CommandController;
import ninja.amp.items.api.command.CommandGroup;
import ninja.amp.items.api.config.ConfigManager;
import ninja.amp.items.api.menu.MenuListener;
import ninja.amp.items.api.message.Messenger;
import ninja.amp.items.commands.AboutCommand;
import ninja.amp.items.commands.HelpCommand;
import ninja.amp.items.commands.ReloadCommand;
import ninja.amp.items.commands.items.GetItemCommand;
import ninja.amp.items.commands.items.ItemInfoCommand;
import ninja.amp.items.commands.sockets.ExtractGemCommand;
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the AmpItems plugin.
 *
 * @author Austin Payne
 */
public class AmpItems extends JavaPlugin implements ItemPlugin {

    private ConfigManager configManager;
    private Messenger messenger;
    private CommandController commandController;
    private ItemManager itemManager;
    private ItemListener itemListener;
    private MenuListener menuListener;

    @Override
    public void onEnable() {
        // Attempt to load NMSHandler
        NMSHandler.getInterface();

        // The order managers are created in is important
        configManager = new ConfigManager(this);
        messenger = new Messenger(this);
        commandController = new CommandController(this);
        itemManager = new ItemManager(this);
        itemListener = new ItemListener(this);
        menuListener = new MenuListener(this);

        // Initialize item factories
        itemManager.setDefaultFactories();

        // Create amp items command tree

        // Item commands. These are added to both /aitem and /aitem item
        Command info = new ItemInfoCommand(this);
        Command get = new GetItemCommand(this);
        CommandGroup item = new CommandGroup(this, "item")
                .addChildCommand(info)
                .addChildCommand(get);
        item.setPermission(new Permission("ampitems.item.all", PermissionDefault.OP));

        // Socket commands.
        Command extract = new ExtractGemCommand(this);
        CommandGroup socket = new CommandGroup(this, "socket")
                .addChildCommand(extract);
        socket.setPermission(new Permission("ampitems.socket.all", PermissionDefault.OP));

        // Main /aitem command tree
        CommandGroup ampItems = new CommandGroup(this, "ampitems")
                .addChildCommand(new AboutCommand(this))
                .addChildCommand(new HelpCommand(this))
                .addChildCommand(new ReloadCommand(this))
                .addChildCommand(info)
                .addChildCommand(get)
                .addChildCommand(socket)
                .addChildCommand(item);
        ampItems.setPermission(new Permission("ampitems.all", PermissionDefault.OP));

        // Add amp items command tree to command controller
        commandController.addCommand(ampItems);
        commandController.updatePageList();

        // Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(itemListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(menuListener, this);
    }

    @Override
    public void onDisable() {
        // The order managers are destroyed in is not important
        menuListener = null;
        itemListener = null;
        itemManager = null;
        commandController.unregisterCommands();
        commandController = null;
        messenger = null;
        configManager = null;
    }

    @Override
    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public Messenger getMessenger() {
        return messenger;
    }

    @Override
    public CommandController getCommandController() {
        return commandController;
    }

    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }

    public ItemListener getItemListener() {
        return itemListener;
    }

    public MenuListener getMenuListener() {
        return menuListener;
    }

}
