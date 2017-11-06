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
package com.herocraftonline.items.api.item;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a type of custom item.
 *
 * @author Austin Payne
 */
public class ItemType {

    /**
     * Default item types that may be needed.
     */

    public static final ItemType UNKNOWN = new ItemType("unknown", "UNKNOWN");
    public static final ItemType MAIN_HAND = new ItemType("main-hand");
        public static final ItemType TOOL = new ItemType("tool", MAIN_HAND);
            public static final ItemType TOOL_AXE = new ItemType("tool-axe", TOOL);
            public static final ItemType TOOL_HOE = new ItemType("tool-hoe", TOOL);
            public static final ItemType TOOL_PICKAXE = new ItemType("tool-pickaxe", TOOL);
            public static final ItemType TOOL_SHEARS = new ItemType("tool-shears", TOOL);
            public static final ItemType TOOL_SHOVEL = new ItemType("tool-shovel", TOOL);
        public static final ItemType WEAPON = new ItemType("weapon", MAIN_HAND);
            public static final ItemType WEAPON_AXE = new ItemType("weapon-axe", WEAPON);
            public static final ItemType WEAPON_BOW = new ItemType("weapon-bow", WEAPON);
            public static final ItemType WEAPON_HOE = new ItemType("weapon-hoe", WEAPON);
            public static final ItemType WEAPON_PICKAXE = new ItemType("weapon-pickaxe", WEAPON);
            public static final ItemType WEAPON_SHEARS = new ItemType("weapon-shears", WEAPON);
            public static final ItemType WEAPON_SHOVEL = new ItemType("weapon-shovel", WEAPON);
            public static final ItemType WEAPON_SWORD = new ItemType("weapon-sword", WEAPON);
    public static final ItemType OFF_HAND = new ItemType("off-hand");
        public static final ItemType SHIELD = new ItemType("shield", OFF_HAND);
        public static final ItemType ARROW = new ItemType("arrow", OFF_HAND);
    public static final ItemType ARMOR = new ItemType("armor");
        public static final ItemType HELMET = new ItemType("helmet", ARMOR);
        public static final ItemType CHESTPLATE = new ItemType("chestplate", ARMOR);
        public static final ItemType LEGGINGS = new ItemType("leggings", ARMOR);
        public static final ItemType BOOTS = new ItemType("boots", ARMOR);

    private final String name;
    public final String displayName;
    public final ItemType parent;

    public ItemType(String name, String displayName, ItemType parent) {
        this.name = name;
        this.displayName = displayName;
        this.parent = parent;
    }

    public ItemType(String name, ItemType parent) {
        this(name, name, parent);
    }

    public ItemType(String name, String displayName) {
        this(name, displayName, null);
    }

    public ItemType(String name) {
        this(name, name, null);
    }

    /**
     * Gets the name of the item type.
     *
     * @return the item type's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the display name of the item type.
     *
     * @return the item type's display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the this item type has a parent
     *
     * @return {@code true} if a perent exists, {@code false} otherwise
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Gets the parent item type of this item type.
     *
     * @return the parent item type
     */
    public ItemType getParent() {
        return parent;
    }

    /**
     * Checks if this item type is, or is a child of the given item type
     *
     * @param itemType the item type to check against
     * @return {@code true} this item type is or is a child of the given item type, {@code false} otherwise
     */
    public boolean isType(ItemType itemType) {
        if (itemType == null) return false;
        ItemType current = this;
        do {
            if (current.equals(itemType)) {
                return true;
            }
        }
        while ((current = parent) != null);

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemType)) return false;
        return equals((ItemType) o);
    }

    public boolean equals(ItemType other) {
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

}
