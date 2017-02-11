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
package ninja.amp.items.api;

import ninja.amp.items.api.command.CommandController;
import ninja.amp.items.api.config.ConfigManager;
import ninja.amp.items.api.equipment.EquipmentManager;
import ninja.amp.items.api.item.ItemManager;
import ninja.amp.items.api.message.Messenger;
import org.bukkit.plugin.Plugin;

/**
 * The main class of the AmpItems plugin api.
 *
 * @author Austin Payne
 */
public interface ItemPlugin extends Plugin {

    /**
     * Gets the item plugin's config manager.
     *
     * @return the item plugin's config manager
     */
    ConfigManager getConfigManager();

    /**
     * Gets the item plugin's messenger.
     *
     * @return the item plugin's messenger
     */
    Messenger getMessenger();

    /**
     * Gets the item plugin's command controller.
     *
     * @return the item plugin's command controller
     */
    CommandController getCommandController();

    /**
     * Gets the item plugin's item manager.
     *
     * @return the item plugin's item manager
     */
    ItemManager getItemManager();

    /**
     * Gets the item plugin's equipment manager.
     *
     * @return the item plugin's equipment manager
     */
    EquipmentManager getEquipmentManager();

}
