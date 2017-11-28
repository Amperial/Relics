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
package com.herocraftonline.items.menu;

import org.bukkit.entity.Player;

/**
 * An event called when an item in the item menu is clicked.
 *
 * @author Austin Payne
 */
public class ItemClickEvent {

    private Player player;
    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;

    public ItemClickEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets the player who clicked.
     *
     * @return the player who clicked
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the {@link ItemMenu} will go back to the parent menu.
     *
     * @return {@code true} if the item menu will go back to the parent menu
     */
    public boolean willGoBack() {
        return goBack;
    }

    /**
     * Sets if the {@link ItemMenu} will go back to the parent menu.
     *
     * @param goBack if the item menu will go back to the parent menu
     */
    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
            update = false;
        }
    }

    /**
     * Checks if the item menu will close.
     *
     * @return {@code true} if the item menu will close
     */
    public boolean willClose() {
        return close;
    }

    /**
     * Sets if the item menu will close.
     *
     * @param close if the item menu will close
     */
    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
            update = false;
        }
    }

    /**
     * Checks if the item menu will update.
     *
     * @return {@code true} if the item menu will update
     */
    public boolean willUpdate() {
        return update;
    }

    /**
     * Sets if the item menu will update.
     *
     * @param update if the item menu will update
     */
    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            goBack = false;
            close = false;
        }
    }

}
