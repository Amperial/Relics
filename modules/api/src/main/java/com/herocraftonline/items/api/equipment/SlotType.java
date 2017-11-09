package com.herocraftonline.items.api.equipment;

import com.herocraftonline.items.api.item.ItemType;

public final class SlotType {

    public static final SlotType MAIN_HAND = new SlotType("Main Hand", 0, ItemType.MAIN_HAND);
    public static final SlotType OFF_HAND = new SlotType("Off Hand", 40, ItemType.OFF_HAND);
    public static final SlotType HELMET = new SlotType("Helmet", 39, ItemType.HELMET);
    public static final SlotType CHESTPLATE = new SlotType("Chestplate", 38, ItemType.CHESTPLATE);
    public static final SlotType LEGGINGS = new SlotType("Leggings", 37, ItemType.LEGGINGS);
    public static final SlotType BOOTS = new SlotType("Boots", 36, ItemType.BOOTS);

    private final String name;
    private final int index;
    private final ItemType itemType;

    public SlotType(String name, int index, ItemType itemType) {
        this.name = name;
        this.index = index;
        this.itemType = itemType;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return index;
    }

    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlotType)) return false;
        return index == ((SlotType) o).index;
    }

    @Override
    public int hashCode() {
        return index;
    }
}
