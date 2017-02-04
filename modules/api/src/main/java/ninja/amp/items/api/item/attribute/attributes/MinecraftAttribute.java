/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
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
        ARMOR("Armor", "armor", UUID.fromString("E4A6AA09-6F0E-483F-B599-70A99CAB3A60")),
        ARMOR_TOUGHNESS("Armor Toughness", "armorToughness", UUID.fromString("299080F9-436A-4230-BEBA-193D896293EA")),
        ATTACK_DAMAGE("Attack Damage", "attackDamage", UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")),
        ATTACK_SPEED("Attack Speed", "attackSpeed", UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3")),
        LUCK("Luck", "luck", UUID.fromString("940A0E67-89D3-4A4F-907F-ACE9524BE34E")),
        MAX_HEALTH("Max Health", "maxHealth", UUID.fromString("493E3AEC-0B14-48DF-985B-E6CAD96838BD")),
        MOVEMENT_SPEED("Move Speed", "movementSpeed", UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));

        private final String displayName;
        private final String name;
        private final UUID uuid;

        Type(String displayName, String name, UUID uuid) {
            this.displayName = displayName;
            this.name = "generic." + name;
            this.uuid = uuid;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getName() {
            return name;
        }

        public UUID getUUID() {
            return uuid;
        }

    }

    enum Slot {
        ANY("When equipped:", "any"),
        MAIN_HAND("When equipped in main hand:", "mainhand"),
        OFF_HAND("When equipped in off hand:", "offhand"),
        HEAD("When equipped on head:", "head"),
        CHEST("When equipped on chest:", "chest"),
        LEGS("When equipped on legs:", "legs"),
        FEET("When equipped on feet:", "feet");

        private final String displayName;
        private final String name;

        Slot(String displayName, String name) {
            this.displayName = displayName;
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getName() {
            return name;
        }

    }

    enum Operation {
        ADD_NUMBER("+"),
        ADD_SCALAR("x"),
        MULTIPLY_SCALAR("x");

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

    }

}
