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
package com.herocraftonline.items.api;

import com.herocraftonline.items.api.command.CommandController;
import com.herocraftonline.items.api.equipment.EquipmentManager;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.model.ModelManager;
import com.herocraftonline.items.api.message.Messenger;
import com.herocraftonline.items.api.storage.config.ConfigManager;
import de.slikey.effectlib.EffectManager;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

/**
 * The main class of the Relics plugin api.
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
     * Gets the item plugin's model manager.
     *
     * @return the item plugin's model manager
     */
    ModelManager getModelManager();

    /**
     * Gets the item plugin's equipment manager.
     *
     * @return the item plugin's equipment manager
     */
    EquipmentManager getEquipmentManager();

    /**
     * Gets the item plugin's effect lib integration.
     *
     * @return the item plugin's effect manager
     */
    Optional<EffectManager> getEffectLib();

    /**
     * Saves a plugin resource if it exists and isn't already saved,<br>
     * avoiding related exceptions and console output.
     *
     * @param resourcePath the path to the plugin resource
     */
    void saveResource(String resourcePath);

}
