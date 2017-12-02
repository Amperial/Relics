/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.attributes.stats.StatAttribute;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * An attribute that adds generic minecraft stats to an item.
 *
 * @author Austin Payne
 */
public interface Minecraft extends StatAttribute<Minecraft> {

    /**
     * Gets the attribute's minecraft type.
     *
     * @return the type
     */
    Type getMinecraftType();

    /**
     * Gets the attribute's minecraft slot.
     *
     * @return the slot
     */
    Slot getSlot();

    /**
     * Sets the attribute's minecraft slot.
     *
     * @param slot the slot
     */
    void setSlot(Slot slot);

    /**
     * Gets the attribute's minecraft operation.
     *
     * @return the operation
     */
    Operation getOperation();

    /**
     * Sets the attribute's minecraft operation.
     *
     * @param operation the operation
     */
    void setOperation(Operation operation);

    /**
     * Checks if the attribute is stacking.
     *
     * @return {@code true} if the attribute is stacking
     */
    boolean isStacking();

    /**
     * Sets if the attribute is stacking.
     *
     * @param stacking if the attribute is stacking
     */
    void setStacking(boolean stacking);

    /**
     * Gets the attribute's minecraft id.
     *
     * @return the minecraft id
     */
    UUID getUUID();

    /**
     * Gets the attribute's amount.
     *
     * @return the amount
     */
    double getAmount();

    /**
     * Sets the attribute's amount.
     *
     * @param amount the amount
     */
    void setAmount(double amount);

    /**
     * Types of generic minecraft attribute modifiers.
     */
    enum Type {
        ARMOR("armor", UUID.fromString("E4A6AA09-6F0E-483F-B599-70A99CAB3A60"), "Armor"),
        ARMOR_TOUGHNESS("armorToughness", UUID.fromString("299080F9-436A-4230-BEBA-193D896293EA"), "Armor Toughness"),
        ATTACK_DAMAGE("attackDamage", UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Attack Damage"),
        ATTACK_SPEED("attackSpeed", UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Attack Speed"),
        LUCK("luck", UUID.fromString("940A0E67-89D3-4A4F-907F-ACE9524BE34E"), "Luck"),
        MAX_HEALTH("maxHealth", UUID.fromString("493E3AEC-0B14-48DF-985B-E6CAD96838BD"), "Max Health"),
        MOVEMENT_SPEED("movementSpeed", UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), "Movement Speed");

        private final String name;
        private final UUID uuid;
        private String displayName;

        Type(String name, UUID uuid, String displayName) {
            this.name = "generic." + name;
            this.uuid = uuid;
            this.displayName = displayName;
        }

        /**
         * Gets the type's nbt name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the type's nbt id.
         *
         * @return the id
         */
        public UUID getUUID() {
            return uuid;
        }

        /**
         * Gets the type's display name.
         *
         * @return the display name
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Sets the type's display name.
         *
         * @param displayName the display name
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    /**
     * Slots of generic minecraft attribute modifiers.
     */
    enum Slot {
        ANY("any", "When equipped:"),
        MAIN_HAND("mainhand", "When equipped in main hand:"),
        OFF_HAND("offhand", "When equipped in off hand:"),
        HEAD("head", "When equipped on head:"),
        CHEST("chest", "When equipped on chest:"),
        LEGS("legs", "When equipped on legs:"),
        FEET("feet", "When equipped on feet:");

        private final String name;
        private String displayName;

        Slot(String name, String displayName) {
            this.name = name;
            this.displayName = displayName;
        }

        /**
         * Gets the slot's nbt name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the slot's display name.
         *
         * @return the display name
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Sets the slot's display name.
         *
         * @param displayName the display name
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    /**
     * Operations of generic minecraft attribute modifiers.
     */
    enum Operation {
        ADD_NUMBER("") {
        },
        ADD_SCALAR("%") {
            @Override
            public String amount(double amount) {
                return super.amount(amount * 100);
            }
        },
        MULTIPLY_SCALAR("%") {
            @Override
            public String prefix(double amount, boolean stacks) {
                return stacks ? ChatColor.BLUE + "x" : ChatColor.GRAY.toString();
            }

            @Override
            public String amount(double amount) {
                return super.amount(amount * 100);
            }
        };

        private final String suffix;

        Operation(String suffix) {
            this.suffix = suffix;
        }

        /**
         * Gets the prefix string of an operation for a certain amount and stacking type.
         *
         * @param amount   the amount
         * @param stacking whether the attribute is stacking
         * @return the prefix string
         */
        public String prefix(double amount, boolean stacking) {
            return stacking ? ChatColor.BLUE + (amount > 0 ? "+" : "") : ChatColor.GRAY.toString();
        }

        /**
         * Gets the amount string of an operation for a certain amount.
         *
         * @param amount the amount
         * @return the amount string
         */
        public String amount(double amount) {
            return (amount == Math.floor(amount) ? Integer.toString((int) amount) : Double.toString(amount));
        }

        /**
         * Gets the suffix string of an operation.
         *
         * @return the suffix string
         */
        public String suffix() {
            return suffix;
        }
    }

}
