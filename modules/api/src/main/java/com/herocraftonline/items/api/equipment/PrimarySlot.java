package com.herocraftonline.items.api.equipment;

public enum PrimarySlot {
    MAIN_HAND("Main Hand"),
    OFF_HAND("Off Hand"),
    HEAD("Head"),
    CHEST("Chest"),
    LEGS("Legs"),
    FEET("Legs");

    private String name;

    PrimarySlot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
