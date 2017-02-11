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
package ninja.amp.items.api.menu.items;

import ninja.amp.items.api.menu.ItemClickEvent;
import ninja.amp.items.api.menu.Owner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * An item inside an {@link ninja.amp.items.api.menu.ItemMenu}.
 *
 * @author Austin Payne
 */
public class MenuItem {

    private final String displayName;
    private final ItemStack icon;
    private final List<String> lore;

    public MenuItem(String displayName, ItemStack icon, String... lore) {
        this.displayName = displayName;
        if (icon == null) {
            this.icon = new ItemStack(Material.STONE);
        } else {
            this.icon = icon.clone();
        }
        this.lore = Arrays.asList(lore);
    }

    /**
     * Gets the display name of the menu item.
     *
     * @return the menu item's display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the icon of the menu item.
     *
     * @return the menu item's icon
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Gets the lore of the menu item.
     *
     * @return the menu item's lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the item stack for a certain player.
     *
     * @param player the player
     * @return the final icon
     */
    public ItemStack getFinalIcon(Player player) {
        return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
    }

    /**
     * Gets the item stack for a certain owner.
     *
     * @param owner the owner of a character
     * @return the final icon
     */
    public ItemStack getFinalIcon(Owner owner) {
        return getFinalIcon(owner.getPlayer());
    }

    public void onItemClick(ItemClickEvent event) {
        // Do nothing by default
    }

    /**
     * Sets the display name and lore of an ItemStack.
     *
     * @param itemStack   the item stack
     * @param displayName the display name
     * @param lore        the lore
     * @return the item stack
     */
    public static ItemStack setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
