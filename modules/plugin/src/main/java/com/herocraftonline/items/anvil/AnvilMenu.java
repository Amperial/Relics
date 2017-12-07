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
package com.herocraftonline.items.anvil;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.result.TriggerResult;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.source.TriggerSource;
import com.herocraftonline.items.api.item.attribute.attributes.trigger.triggers.Anvil;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import com.herocraftonline.items.api.util.InventoryUtil.Slot;
import com.herocraftonline.items.item.attributes.triggers.sources.AnvilSource;
import com.herocraftonline.items.menu.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A menu controlling the usage of anvils to modify relics items.
 *
 * @author Austin Payne
 */
public class AnvilMenu {

    private static final Dimensions OUTER = new Dimensions(9, 4);
    private static final Position ANVIL = new Position(1, 2);
    private static final Position ITEM = new Position(1, 1);
    private static final Position UPGRADE = new Position(2, 1);
    private static final ItemStack ANVIL_INSERT = MenuItem.setNameAndLore(new ItemStack(Material.ANVIL), "Place a Relics item on the anvil!", null);
    private static final ItemStack ANVIL_UPGRADE = MenuItem.setNameAndLore(new ItemStack(Material.ANVIL), "Choose an upgrade material!", null);
    private static final ItemStack ANVIL_CONFIRM = MenuItem.setNameAndLore(new ItemStack(Material.ANVIL), ChatColor.GREEN + "Click to confirm upgrade!", null);
    private static final ItemStack ANVIL_INVALID = MenuItem.setNameAndLore(new ItemStack(Material.ANVIL), ChatColor.DARK_RED + "Invalid upgrade material.", null);
    private static final ItemStack FILL = new ItemStack(Material.IRON_FENCE);
    private static final Predicate<ItemStack> EMPTY = (item) -> item == null || item.getType() == Material.AIR;

    private final ItemPlugin plugin;
    private final Inventory inventory;

    public AnvilMenu(ItemPlugin plugin, Inventory inventory) {
        this.plugin = plugin;
        this.inventory = inventory;

        setItem(ANVIL, ANVIL_INSERT);

        // Fill remaining positions
        OUTER.forEach(position -> {
            if (!(position.equals(ANVIL) || position.equals(ITEM))) {
                setItem(position, FILL);
            }
        });
    }

    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        InventoryAction action = event.getAction();
        switch (action) {
            // Won't affect crafting menu at all
            case CLONE_STACK:
            case DROP_ALL_CURSOR:
            case DROP_ONE_CURSOR:
            case NOTHING:
                return;
            // Not allowed inside crafting menu
            case COLLECT_TO_CURSOR:
            case DROP_ALL_SLOT:
            case DROP_ONE_SLOT:
            case HOTBAR_MOVE_AND_READD:
            case HOTBAR_SWAP:
            case UNKNOWN:
                event.setCancelled(true);
                return;
            // These actions are fine
            case MOVE_TO_OTHER_INVENTORY:
            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
            case PLACE_ALL:
            case PLACE_ONE:
            case PLACE_SOME:
            case SWAP_WITH_CURSOR:
                break;
        }

        ClickType click = event.getClick();
        switch (click) {
            // Won't affect crafting menu at all
            case CONTROL_DROP:
            case DROP:
            case WINDOW_BORDER_LEFT:
            case WINDOW_BORDER_RIGHT:
                return;
            // Not allowed inside crafting menu
            case DOUBLE_CLICK:
            case MIDDLE:
            case NUMBER_KEY:
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
            case UNKNOWN:
                event.setCancelled(true);
                return;
            // These clicks are fine
            case CREATIVE:
            case LEFT:
            case RIGHT:
                break;
        }

        if (!event.getClickedInventory().equals(inventory)) {
            return;
        }

        Position clicked = new Slot(event.getSlot()).getPosition(OUTER);
        ItemStack cursor = event.getCursor();

        // Check for player clicking item slot
        if (clicked.equals(ITEM)) {
            boolean hasItem = hasItem(ITEM);
            boolean cursorEmpty = EMPTY.test(cursor);
            if (!hasItem && !cursorEmpty) {
                // Inserting an item. Show upgrade slot
                setItem(UPGRADE, null);
            } else if (hasItem && cursorEmpty && hasItem(UPGRADE)) {
                // Don't allow player to remove the item while an item is in upgrade slot.
                event.setCancelled(true);
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> updateAnvil(player));
            return;
        }

        // Check for player clicking upgrade slot
        if (clicked.equals(UPGRADE) && hasItem(ITEM)) {
            // Item is inserted into the item slot and the upgrade slot is visible.
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> updateAnvil(player));
            return;
        }

        // Check for player clicking anvil slot
        if (clicked.equals(ANVIL)) {
            // Attempt to upgrade item
            Optional<ItemStack> item = getItem(ITEM);
            Optional<ItemStack> upgrade = getItem(UPGRADE);
            if (item.isPresent() && upgrade.isPresent() ) {
                Optional<ItemStack> upgraded = upgrade(player, item.get(), upgrade.get());
                if (upgraded.isPresent()) {
                    // Item was upgraded
                    setItem(ITEM, upgraded.get());
                    setItem(UPGRADE, null);
                }
            }
        }

        event.setCancelled(true);
    }

    private void updateAnvil(Player player) {
        ItemManager itemManager = plugin.getItemManager();
        Optional<Item> relicItem = getItem(ITEM).flatMap(itemManager::getItem);
        Optional<ItemStack> upgrade = getItem(UPGRADE);
        Optional<Item> upgradeItem = upgrade.flatMap(itemManager::getItem);
        if (relicItem.isPresent()) {
            if (upgrade.isPresent()) {
                if (upgradeItem.isPresent() && canUpgrade(player, relicItem.get(), upgradeItem.get())) {
                    setItem(ANVIL, ANVIL_CONFIRM);
                } else {
                    setItem(ANVIL, ANVIL_INVALID);
                }
            } else {
                setItem(ANVIL, ANVIL_UPGRADE);
            }
        } else {
            setItem(ANVIL, ANVIL_INSERT);
            setItem(UPGRADE, FILL);
        }
        player.updateInventory();
    }

    private boolean canUpgrade(Player player, Item item, Item upgrade) {
        TriggerSource anvilSource = new AnvilSource(player, upgrade, item);
        return upgrade.getAttribute(Anvil.class).map(u -> u.canTrigger(anvilSource)).orElse(false);
    }

    private Optional<ItemStack> upgrade(Player player, ItemStack itemStack, ItemStack upgradeStack) {
        ItemManager itemManager = plugin.getItemManager();
        Optional<Item> item = itemManager.getItem(itemStack);
        Optional<Item> upgrade = itemManager.getItem(upgradeStack);
        if (item.isPresent() && upgrade.isPresent()) {
            Item relicItem = item.get();
            Item upgradeItem = upgrade.get();
            TriggerSource anvilSource = new AnvilSource(player, upgradeItem, relicItem);
            Optional<Anvil> attribute = upgradeItem.getAttribute(Anvil.class).filter(u -> u.canTrigger(anvilSource));
            if (attribute.isPresent()) {
                TriggerResult result = attribute.get().onTrigger(anvilSource);
                switch (result) {
                    case TRIGGERED:
                        return Optional.of(itemStack);
                    case UPDATE_ITEM:
                        return Optional.of(relicItem.updateItem(itemStack).orElse(itemStack));
                    case CONSUME_ITEM:
                        return Optional.of(new ItemStack(Material.AIR));
                    default:
                }
            }
        }
        return Optional.empty();
    }

    private boolean hasItem(Position position) {
        return getItem(position).isPresent();
    }

    private Optional<ItemStack> getItem(Position position) {
        ItemStack item = inventory.getItem(position.getSlot(OUTER).getIndex());
        return EMPTY.test(item) ? Optional.empty() : Optional.of(item);
    }

    private void setItem(Position position, ItemStack itemStack) {
        inventory.setItem(position.getSlot(OUTER).getIndex(), itemStack);
    }

    public void onClose(InventoryCloseEvent event) {
        // Get items currently in anvil menu
        List<ItemStack> drops = new ArrayList<>();
        getItem(ITEM).ifPresent(item -> {
            drops.add(item);
            getItem(UPGRADE).ifPresent(drops::add);
        });

        // Return items to player inventory
        HumanEntity entity = event.getPlayer();
        Collection<ItemStack> notReturned = entity.getInventory().addItem(drops.toArray(new ItemStack[drops.size()])).values();
        if (!notReturned.isEmpty()) {
            // Drop not returned items at player's location
            World world = entity.getWorld();
            notReturned.forEach(itemStack -> world.dropItem(entity.getLocation(), itemStack));
        }
    }

    public static void open(ItemPlugin plugin, Player player) {
        AnvilHolder holder = new AnvilHolder(player);
        Inventory inventory = Bukkit.createInventory(holder, OUTER.size(), "Relics Anvil");
        holder.menu = new AnvilMenu(plugin, inventory);

        player.openInventory(inventory);
    }

    public static class AnvilHolder implements InventoryHolder {
        private Player player;
        private AnvilMenu menu;

        public AnvilHolder(Player player) {
            this.player = player;
        }

        public AnvilMenu getMenu() {
            return menu;
        }

        @Override
        public Inventory getInventory() {
            return getMenu().inventory;
        }
    }

}
