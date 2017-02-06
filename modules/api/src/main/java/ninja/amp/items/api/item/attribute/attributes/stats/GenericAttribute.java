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
package ninja.amp.items.api.item.attribute.attributes.stats;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public interface GenericAttribute extends StatAttribute<GenericAttribute.GenericAttributeStatType> {

    GenericAttributeStatType STAT_TYPE = new GenericAttributeStatType();

    Type getMinecraftType();

    Slot getSlot();

    void setSlot(Slot slot);

    Operation getOperation();

    void setOperation(Operation operation);

    boolean isStacking();

    void setStacking(boolean stacking);

    UUID getUUID();

    double getAmount();

    void setAmount(double amount);

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

        public String prefix(double amount, boolean stacking) {
            return stacking ? ChatColor.BLUE + (amount > 0 ? "+" : "") : ChatColor.GRAY.toString();
        }

        public String amount(double amount) {
            return (amount == Math.floor(amount) ? Integer.toString((int) amount) : Double.toString(amount));
        }

        public String suffix() {
            return suffix;
        }

    }

    class GenericAttributeStatType implements StatType<GenericAttributeStatType> {

        @Override
        public Position getPosition() {
            return Position.BOTTOM;
        }

        @Override
        public StatTotal<GenericAttributeStatType> newTotal(StatSpecifier<GenericAttributeStatType> specifier) {
            return new StatTotal<GenericAttributeStatType>(specifier) {
                double amount = 0;

                @Override
                public void addStat(StatAttribute<GenericAttributeStatType> stat) {
                    amount += ((GenericAttribute) stat).getAmount();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    GenericAttributeSpecifier specifier = (GenericAttributeSpecifier) getStatSpecifier();
                    lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, amount));
                }

                @Override
                public boolean shouldAddLore() {
                    return amount > 0 || amount < 0;
                }
            };
        }

        @Override
        public void addTo(List<String> lore, Map<StatSpecifier<GenericAttributeStatType>, StatTotal<GenericAttributeStatType>> stats) {
            stats.entrySet().stream()
                    .collect(Collectors.groupingBy(e -> ((GenericAttributeSpecifier) e.getKey()).slot, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                    .entrySet().forEach(slot -> {
                lore.add(ChatColor.GRAY + slot.getKey().getDisplayName());
                slot.getValue().forEach(total -> total.addTo(lore, "  "));
            });
        }

        public String formatModifier(Operation operation, Type type, boolean stacking, double amount) {
            return operation.prefix(amount, stacking) + operation.amount(amount) + operation.suffix() + " " + type.getDisplayName();
        }

    }

    class GenericAttributeSpecifier implements StatSpecifier<GenericAttributeStatType> {

        protected final Slot slot;
        protected final Type type;
        protected final Operation operation;
        protected final boolean stacking;

        public GenericAttributeSpecifier(GenericAttribute attribute) {
            this.slot = attribute.getSlot();
            this.type = attribute.getMinecraftType();
            this.operation = attribute.getOperation();
            this.stacking = attribute.isStacking();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GenericAttributeSpecifier)) return false;
            GenericAttributeSpecifier that = (GenericAttributeSpecifier) o;
            return stacking == that.stacking &&
                    slot == that.slot &&
                    type == that.type &&
                    operation == that.operation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(slot, type, operation, stacking);
        }

    }

}
