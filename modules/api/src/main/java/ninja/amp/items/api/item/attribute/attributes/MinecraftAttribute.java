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
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.item.attribute.ItemAttribute;

import java.util.UUID;

public interface MinecraftAttribute extends ItemAttribute {

    Type getMinecraftType();

    double getAmount();

    void setAmount(double amount);

    Operation getOperation();

    void setOperation(Operation operation);

    UUID getUUID();

    Slot getSlot();

    void setSlot(Slot slot);

    boolean getStacks();

    void setStacks(boolean stacks);

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

        public String getName() {
            return name;
        }

        public UUID getUUID() {
            return uuid;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

    }

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

        public String getName() {
            return name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

    }

    enum Operation {
        ADD_NUMBER() {
            @Override
            public String format(double amount) {
                return format(formatAmount(amount));
            }

            @Override
            public String format(String amount) {
                return amount;
            }
        },
        ADD_SCALAR() {
            @Override
            public String format(double amount) {
                return format(formatAmount(amount * 100));
            }

            @Override
            public String format(String amount) {
                return amount + "%";
            }
        },
        MULTIPLY_SCALAR() {
            @Override
            public String format(double amount) {
                return format(formatAmount(amount * 100));
            }

            @Override
            public String format(String amount) {
                return amount + "%";
            }
        };

        public static String formatAmount(double amount) {
            return (amount == Math.floor(amount) ? Integer.toString((int) amount) : Double.toString(amount));
        }

        public abstract String format(double amount);

        public abstract String format(String amount);

    }

}
