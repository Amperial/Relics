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
package com.herocraftonline.items;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.command.Command;
import com.herocraftonline.items.api.command.CommandController;
import com.herocraftonline.items.api.command.CommandGroup;
import com.herocraftonline.items.api.menu.MenuListener;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.storage.config.ConfigManager;
import com.herocraftonline.items.commands.AboutCommand;
import com.herocraftonline.items.commands.HelpCommand;
import com.herocraftonline.items.commands.ReloadCommand;
import com.herocraftonline.items.commands.items.GetItemCommand;
import com.herocraftonline.items.commands.items.GiveItemCommand;
import com.herocraftonline.items.commands.items.ItemInfoCommand;
import com.herocraftonline.items.commands.misc.TokenCommand;
import com.herocraftonline.items.commands.sockets.ExtractGemCommand;
import com.herocraftonline.items.commands.sockets.InfuseGemCommand;
import com.herocraftonline.items.equipment.EquipmentManager;
import com.herocraftonline.items.item.ItemManager;
import com.herocraftonline.items.nms.NMSHandler;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the Relics plugin.
 *
 * @author Austin Payne
 */
public class Relics extends JavaPlugin implements ItemPlugin {

    /**
     * Managers of the Relics plugin.
     */

    private ConfigManager configManager;
    private Messenger messenger;
    private CommandController commandController;
    private ItemManager itemManager;
    private EquipmentManager equipmentManager;

    /**
     * Listeners of the Relics plugin.
     */

    private PlayerListener playerListener;
    private ItemListener itemListener;
    private MenuListener menuListener;

    @Override
    public void onEnable() {
        // Attempt to load NMSHandler
        NMSHandler.getInterface();

        // Create managers (order is important)
        configManager = new ConfigManager(this);
        messenger = new Messenger(this);
        commandController = new CommandController(this);
        itemManager = new ItemManager(this);
        equipmentManager = new EquipmentManager(this);

        // Create listeners
        playerListener = new PlayerListener(this);
        itemListener = new ItemListener(this);
        menuListener = new MenuListener(this);

        // Create relics command tree

        // Item commands. These are added to both /relic and /relic item
        Command info = new ItemInfoCommand(this);
        Command get = new GetItemCommand(this);
        Command give = new GiveItemCommand(this);
        CommandGroup item = new CommandGroup(this, "item")
                .addChildCommand(info)
                .addChildCommand(get)
                .addChildCommand(give);
        item.setPermission(new Permission("relics.item.all", PermissionDefault.OP));

        // Socket commands.
        Command extract = new ExtractGemCommand(this);
        Command infuse = new InfuseGemCommand(this);
        CommandGroup socket = new CommandGroup(this, "socket")
                .addChildCommand(extract)
                .addChildCommand(infuse);
        socket.setPermission(new Permission("relics.socket.all", PermissionDefault.OP));

        // Main /relics command tree
        CommandGroup relic = new CommandGroup(this, "relics")
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
        relic.setPermission(new Permission("relics.all", PermissionDefault.OP));

        // Add relics command tree to command controller
        commandController.addCommand(relic);
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

    /**
     * Gets the item plugin's player listener.
     *
     * @return the item plugin's player listener
     */
    public PlayerListener getPlayerListener() {
        return playerListener;
    }

    /**
     * Gets the item plugin's item listener.
     *
     * @return the item plugin's item listener
     */
    public ItemListener getItemListener() {
        return itemListener;
    }

    /**
     * Gets the item plugin's menu listener.
     *
     * @return the item plugin's menu listener
     */
    public MenuListener getMenuListener() {
        return menuListener;
    }

}
