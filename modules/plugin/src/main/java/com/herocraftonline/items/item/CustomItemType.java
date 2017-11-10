package com.herocraftonline.items.item;

import java.util.Objects;

public final class CustomItemType implements com.herocraftonline.items.api.item.ItemType {

    private final String name;
    private final CustomItemType parent;
    private final boolean isAbstract;
    private final boolean isTransient;

    public CustomItemType(String name, CustomItemType parent, boolean isAbstract, boolean isTransient) {
        this.name = name;
        this.parent = parent;
        this.isAbstract = isAbstract;
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
        if (other instanceof CustomItemType) {
            CustomItemType current = (CustomItemType) other;
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
    public boolean isAbstract() {
        return isAbstract;
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
        if (!(o instanceof CustomItemType)) return false;
        return name.equalsIgnoreCase(((CustomItemType) o).name);
    }
}
