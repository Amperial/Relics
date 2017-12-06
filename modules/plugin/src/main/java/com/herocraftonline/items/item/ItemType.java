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
package com.herocraftonline.items.item;

import java.util.Objects;

public final class ItemType implements com.herocraftonline.items.api.item.ItemType {

    private final String name;
    private final ItemType parent;
    private final boolean isTransient;

    public ItemType(String name, ItemType parent, boolean isTransient) {
        this.name = name;
        this.parent = parent;
        this.isTransient = isTransient;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public ItemType getParent() {
        return parent;
    }

    @Override
    public boolean isType(com.herocraftonline.items.api.item.ItemType other) {
        return equals(other) || (hasParent() && getParent().isType(other));
    }

    @Override
    public boolean isTransient() {
        return isTransient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemType)) return false;
        ItemType itemType = (ItemType) o;
        return Objects.equals(getName().toLowerCase(), itemType.getName().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName().toLowerCase());
    }

}
