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
import com.herocraftonline.items.crafting.CraftingListener;
import com.herocraftonline.items.equipment.EquipmentManager;
import com.herocraftonline.items.item.ItemManager;
import com.herocraftonline.items.item.models.ModelManager;
import com.herocraftonline.items.nms.NMSHandler;
import com.herocraftonline.items.util.EncryptUtil;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;

/**
 * The main class of the Relics plugin.
 *
 * @author Austin Payne
 */
public class Relics extends JavaPlugin implements ItemPlugin {

    private static ItemPlugin instance;

    public static ItemPlugin instance() {
        return instance;
    }

    /**
     * Managers of the Relics plugin.
     */

    private ConfigManager configManager;
    private Messenger messenger;
    private CommandController commandController;
    private ItemManager itemManager;
    private ModelManager modelManager;
    private EquipmentManager equipmentManager;
    private Optional<EffectManager> effectManager;

    /**
     * Listeners of the Relics plugin.
     */

    private PlayerListener playerListener;
    private ItemListener itemListener;
    private AttributeListener attributeListener;
    private MenuListener menuListener;
    private CraftingListener craftingListener;

    @Override
    public void onEnable() {
        instance = this;

        loadIntegrations();
        loadConfigurations();
        loadManagers();
        loadListeners();
        loadCommands();
    }

    private void loadIntegrations() {
        // Attempt to load effect lib
        Plugin effectLib = getServer().getPluginManager().getPlugin("EffectLib");
        if (effectLib instanceof EffectLib) {
            effectManager = Optional.of(new EffectManager(this));
        } else {
            effectManager = Optional.empty();
        }

        // Attempt to load NMSHandler
        NMSHandler.instance();
    }

    private void loadConfigurations() {
        // Load config manager
        configManager = new ConfigManager(this);

        // Load encrypt util key
        String key = getConfig().getString("encrypt-key");
        if (key == null) {
            getConfig().set("encrypt-key", EncryptUtil.getKey());
            saveConfig();
        } else {
            EncryptUtil.setKey(key);
        }
    }

    private void loadManagers() {
        messenger = new Messenger(this);
        commandController = new CommandController(this);
        itemManager = new ItemManager(this);
        modelManager = new ModelManager(this);
        equipmentManager = new EquipmentManager();
    }

    private void loadListeners() {
        playerListener = new PlayerListener(this);
        itemListener = new ItemListener(this);
        attributeListener = new AttributeListener(this);
        menuListener = new MenuListener(this);
        craftingListener = new CraftingListener(this);
    }

    private void loadCommands() {
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
        craftingListener.closeOpenMenus();
        craftingListener = null;
        menuListener.closeOpenMenus();
        menuListener = null;
        attributeListener = null;
        itemListener = null;
        equipmentManager = null;
        itemManager = null;
        modelManager = null;
        commandController.unregisterCommands();
        commandController = null;
        messenger = null;
        configManager = null;
        if (effectManager.isPresent()) {
            effectManager.get().dispose();
        }
        effectManager = null;
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
    public com.herocraftonline.items.api.item.model.ModelManager getModelManager() {
        return modelManager;
    }

    @Override
    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    @Override
    public Optional<EffectManager> getEffectManager() {
        return effectManager;
    }

    @Override
    public void saveResource(String resourcePath) {
        if (!(new File(getDataFolder(), resourcePath).exists())) {
            try {
                saveResource(resourcePath, false);
            } catch (IllegalArgumentException e) {
                // Ignore missing resources
            }
        }
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
     * Gets the item plugin's attribute listener.
     *
     * @return the item plugin's attribute listener
     */
    public AttributeListener getAttributeListener() {
        return attributeListener;
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
