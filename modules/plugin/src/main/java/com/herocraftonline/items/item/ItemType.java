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
    public com.herocraftonline.items.api.item.ItemType getParent() {
        return parent;
    }

    @Override
    public boolean isType(com.herocraftonline.items.api.item.ItemType other) {
        if (other instanceof ItemType) {
            ItemType current = (ItemType) other;
            do {
                if (current.equals(other)) {
                    return true;
                }
            }
            while ((current = current.parent) != null);
        }

        return false;
    }

    @Override
    public boolean isTransient() {
        return isTransient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemType)) return false;
        return name.equalsIgnoreCase(((ItemType) o).name);
    }
}
