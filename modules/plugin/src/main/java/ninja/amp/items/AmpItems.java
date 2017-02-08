/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
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
import ninja.amp.items.commands.items.GiveItemCommand;
import ninja.amp.items.commands.items.ItemInfoCommand;
import ninja.amp.items.commands.misc.TokenCommand;
import ninja.amp.items.commands.sockets.ExtractGemCommand;
import ninja.amp.items.commands.sockets.InfuseGemCommand;
import ninja.amp.items.equipment.EquipmentManager;
import ninja.amp.items.item.ItemManager;
import ninja.amp.items.nms.NMSHandler;
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
    private EquipmentManager equipmentManager;
    private PlayerListener playerListener;
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
        equipmentManager = new EquipmentManager(this);
        playerListener = new PlayerListener(this);
        itemListener = new ItemListener(this);
        menuListener = new MenuListener(this);

        // Create amp items command tree

        // Item commands. These are added to both /aitem and /aitem item
        Command info = new ItemInfoCommand(this);
        Command get = new GetItemCommand(this);
        Command give = new GiveItemCommand(this);
        CommandGroup item = new CommandGroup(this, "item")
                .addChildCommand(info)
                .addChildCommand(get)
                .addChildCommand(give);
        item.setPermission(new Permission("ampitems.item.all", PermissionDefault.OP));

        // Socket commands.
        Command extract = new ExtractGemCommand(this);
        Command infuse = new InfuseGemCommand(this);
        CommandGroup socket = new CommandGroup(this, "socket")
                .addChildCommand(extract)
                .addChildCommand(infuse);
        socket.setPermission(new Permission("ampitems.socket.all", PermissionDefault.OP));

        // Main /aitem command tree
        CommandGroup ampItems = new CommandGroup(this, "ampitems")
                .addChildCommand(new AboutCommand(this))
                .addChildCommand(new HelpCommand(this))
                .addChildCommand(new ReloadCommand(this))
                .addChildCommand(new TokenCommand(this))
                .addChildCommand(info)
                .addChildCommand(get)
                .addChildCommand(give)
                .addChildCommand(item)
                .addChildCommand(extract)
                .addChildCommand(infuse)
                .addChildCommand(socket);
        ampItems.setPermission(new Permission("ampitems.all", PermissionDefault.OP));

        // Add amp items command tree to command controller
        commandController.addCommand(ampItems);
        commandController.updatePageList();
    }

    @Override
    public void onDisable() {
        // The order managers are destroyed in is not important
        menuListener.closeOpenMenus();
        menuListener = null;
        itemListener = null;
        equipmentManager = null;
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

    @Override
    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public ItemListener getItemListener() {
        return itemListener;
    }

    public MenuListener getMenuListener() {
        return menuListener;
    }

}
