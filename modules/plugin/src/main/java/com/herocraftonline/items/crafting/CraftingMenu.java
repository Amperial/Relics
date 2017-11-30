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
package com.herocraftonline.items.crafting;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.crafting.Recipe;
import com.herocraftonline.items.api.util.InventoryUtil.Dimensions;
import com.herocraftonline.items.api.util.InventoryUtil.Position;
import com.herocraftonline.items.api.util.InventoryUtil.Slot;
import org.bukkit.Bukkit;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A menu controlling the crafting of relics recipes.
 *
 * @author Austin Payne
 */
public class CraftingMenu {

    private static final Dimensions OUTER = new Dimensions(9, 5);
    private static final Dimensions SMALL = new Dimensions(3, 3);
    private static final Dimensions LARGE = new Dimensions(5, 5);
    private static final Position SMALL_OFFSET = new Position(3, 1);
    private static final Position LARGE_OFFSET = new Position(2, 0);
    private static final Position BLUEPRINT = new Position(1, 2);
    private static final Position OUTPUT = new Position(7, 2);
    private static final ItemStack FILL = new ItemStack(Material.IRON_FENCE);

    private final ItemPlugin plugin;
    private final Recipe recipe;
    private final Inventory inventory;
    private final Dimensions craftingDimensions;
    private final Position craftingOffset;
    private final Set<Position> craftingArea;

    public CraftingMenu(ItemPlugin plugin, Recipe recipe, Inventory inventory, ItemStack blueprint) {
        this.plugin = plugin;
        this.recipe = recipe;
        this.inventory = inventory;

        // Get required crafting dimensions and offset
        Dimensions required = recipe.getDimensions();
        if (SMALL.contains(required)) {
            craftingDimensions = SMALL;
            craftingOffset = SMALL_OFFSET;
        } else {
            craftingDimensions = LARGE;
            craftingOffset = LARGE_OFFSET;
        }

        // Get positions in crafting area
        craftingArea = craftingDimensions.getPositions(craftingOffset);

        // Set blueprint item for player to reference when crafting
        setItem(BLUEPRINT, blueprint == null ? FILL : blueprint);

        // Fill remaining positions
        OUTER.forEach(position -> {
            if (!(position.equals(BLUEPRINT) || craftingArea.contains(position) || position.equals(OUTPUT))) {
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

        Position clicked = new Slot(event.getSlot()).getPosition(OUTER);
        ItemStack cursor = event.getCursor();
        if (craftingArea.contains(clicked)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> updateOutput(player));
        } else if (event.getClickedInventory().equals(inventory)) {
            if (clicked.equals(OUTPUT) && (cursor == null || cursor.getType() == Material.AIR)) {
                Optional<ItemStack> output = getItem(OUTPUT);
                if (output.isPresent()) {
                    craftingArea.forEach(position -> setItem(position, null));
                    setItem(OUTPUT, null);
                    player.setItemOnCursor(output.get());
                    player.updateInventory();
                }
            }
            event.setCancelled(true);
        }
    }

    private void updateOutput(Player player) {
        // Get current crafting inputs
        Set<Position> filled = craftingArea.stream().filter(this::hasItem).collect(Collectors.toSet());
        Position offset = filled.stream().reduce(Position.MIN).orElse(Position.ZERO);
        Map<Position, ItemStack> input = filled.stream().collect(Collectors.toMap(position -> position.subtract(offset), position -> getItem(position).orElse(null)));

        // Update crafting output
        setItem(OUTPUT, recipe.test(input) ? recipe.getResult().getItem() : null);
        player.updateInventory();
    }

    private boolean hasItem(Position position) {
        return getItem(position).isPresent();
    }

    private Optional<ItemStack> getItem(Position position) {
        ItemStack item = inventory.getItem(position.getSlot(OUTER).getIndex());
        return (item == null || item.getType() == Material.AIR) ? Optional.empty() : Optional.of(item);
    }

    private void setItem(Position position, ItemStack itemStack) {
        inventory.setItem(position.getSlot(OUTER).getIndex(), itemStack);
    }

    public void onClose(InventoryCloseEvent event) {
        // Get items currently in the crafting area
        List<ItemStack> drops = craftingArea.stream().map(this::getItem)
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        // Return items to player inventory
        HumanEntity entity = event.getPlayer();
        Collection<ItemStack> notReturned = entity.getInventory().addItem(drops.toArray(new ItemStack[drops.size()])).values();
        if (!notReturned.isEmpty()) {
            // Drop not returned items at player's location
            World world = entity.getWorld();
            notReturned.forEach(itemStack -> world.dropItem(entity.getLocation(), itemStack));
        }

    }

    public static void open(ItemPlugin plugin, Player player, Recipe recipe, ItemStack blueprint) {
        CraftingHolder holder = new CraftingHolder(player);
        Inventory inventory = Bukkit.createInventory(holder, OUTER.size(), "Blueprint Assembly");
        holder.menu = new CraftingMenu(plugin, recipe, inventory, blueprint);

        player.openInventory(inventory);
    }

    public static class CraftingHolder implements InventoryHolder {
        private Player player;
        private CraftingMenu menu;

        public CraftingHolder(Player player) {
            this.player = player;
        }

        public CraftingMenu getMenu() {
            return menu;
        }

        @Override
        public Inventory getInventory() {
            return getMenu().inventory;
        }
    }

}
