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
package com.herocraftonline.items.api.menu;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.menu.items.MenuItem;
import com.herocraftonline.items.api.menu.items.StaticMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * A dynamic and interactive menu made up of an inventory and item stacks.
 *
 * @author Austin Payne
 */
public class ItemMenu {

    private ItemPlugin plugin;
    private String name;
    private Size size;
    private MenuItem[] items;
    private ItemMenu parent;

    /**
     * The menu item that appears in empty slots if {@link ItemMenu#fillEmptySlots()} is called.
     */
    @SuppressWarnings("deprecation")
    private static final MenuItem EMPTY_SLOT_ITEM = new StaticMenuItem(" ", new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getDyeData()));

    /**
     * Creates an item menu.
     *
     * @param name   the name of the inventory
     * @param size   the item menu of the inventory
     * @param plugin the item plugin instance
     * @param parent the item menu's parent
     */
    public ItemMenu(String name, Size size, ItemPlugin plugin, ItemMenu parent) {
        this.plugin = plugin;
        this.name = name;
        this.size = size;
        this.items = new MenuItem[size.toInt()];
        this.parent = parent;
    }

    /**
     * Creates an item menu with no parent.
     *
     * @param name   the name of the inventory
     * @param size   the size of the inventory
     * @param plugin the item plugin instance
     */
    public ItemMenu(String name, Size size, ItemPlugin plugin) {
        this(name, size, plugin, null);
    }

    /**
     * Gets the name of the item menu.
     *
     * @return the item menu's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the size of the item menu.
     *
     * @return the item menu's size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Checks if the item menu has a parent.
     *
     * @return {@code true} if the item menu has a parent
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Gets the parent of the item menu.
     *
     * @return the item menu's parent
     */
    public ItemMenu getParent() {
        return parent;
    }

    /**
     * Sets the parent of the item menu.
     *
     * @param parent the item menu to set as parent
     */
    public void setParent(ItemMenu parent) {
        this.parent = parent;
    }

    /**
     * Sets the menu item of a slot.
     *
     * @param position the slot position
     * @param menuItem the menu item
     * @return the item menu
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {
        items[position] = menuItem;
        return this;
    }

    /**
     * Fills all empty slots in the item menu with a certain menu item.
     *
     * @param menuItem the menu item to fill empty slots with
     * @return the item menu
     */
    public ItemMenu fillEmptySlots(MenuItem menuItem) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = menuItem;
            }
        }
        return this;
    }

    /**
     * Fills all empty slots in the item menu with the default empty slot item.
     *
     * @return the item menu
     */
    public ItemMenu fillEmptySlots() {
        return fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    /**
     * Opens the item menu for a player.
     *
     * @param player the player
     */
    public void open(Player player) {
        MenuHolder holder = MenuHolder.createInventory(this, size.toInt(), name);
        apply(holder.getInventory(), player);
        player.openInventory(holder.getInventory());
    }

    /**
     * Updates the item menu for a player.
     *
     * @param player the player to update the item menu for
     */
    @SuppressWarnings("deprecation")
    public void update(Player player) {
        if (player.getOpenInventory() != null) {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof MenuHolder && ((MenuHolder) inventory.getHolder()).getMenu().equals(this)) {
                apply(inventory, player);
                player.updateInventory();
            }
        }
    }

    /**
     * Applies the item menu for a player to an inventory.<br>
     * This overrides the existing contents of the inventory.
     *
     * @param inventory the inventory to apply the item menu to
     * @param player    the player
     */
    public void apply(Inventory inventory, Player player) {
        Owner owner = new Owner(player);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalIcon(owner));
            } else {
                inventory.setItem(i, null);
            }
        }
    }

    /**
     * Handles inventory click events for the item menu.
     */
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT) {
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size.toInt() && items[slot] != null) {
                Player player = (Player) event.getWhoClicked();
                final UUID playerId = player.getUniqueId();

                ItemClickEvent itemClickEvent = new ItemClickEvent(player);
                items[slot].onItemClick(itemClickEvent);
                if (itemClickEvent.willUpdate()) {
                    update(player);
                } else {
                    player.updateInventory();
                    if (itemClickEvent.willClose() || itemClickEvent.willGoBack()) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            Player p = Bukkit.getPlayer(playerId);
                            if (p != null) {
                                p.closeInventory();
                            }
                        }, 1);
                    }
                    if (itemClickEvent.willGoBack() && hasParent()) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            Player p = Bukkit.getPlayer(playerId);
                            if (p != null) {
                                parent.open(p);
                            }
                        }, 3);
                    }
                }
            }
        }
    }

    /**
     * Possible inventory sizes of an item menu.
     */
    public enum Size {
        ONE_LINE(9),
        TWO_LINE(18),
        THREE_LINE(27),
        FOUR_LINE(36),
        FIVE_LINE(45),
        SIX_LINE(54);

        private final int size;

        Size(int size) {
            this.size = size;
        }

        /**
         * Gets the size's amount of slots.
         *
         * @return the amount of slots
         */
        public int toInt() {
            return size;
        }

        /**
         * Gets the required size for an amount of slots.
         *
         * @param slots the amount of slots
         * @return the required size
         */
        public static Size fit(int slots) {
            if (slots < 10) {
                return ONE_LINE;
            } else if (slots < 19) {
                return TWO_LINE;
            } else if (slots < 28) {
                return THREE_LINE;
            } else if (slots < 37) {
                return FOUR_LINE;
            } else if (slots < 46) {
                return FIVE_LINE;
            } else {
                return SIX_LINE;
            }
        }

    }

}
