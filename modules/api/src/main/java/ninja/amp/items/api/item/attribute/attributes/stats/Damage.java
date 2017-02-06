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

import java.util.List;

public interface Damage extends GenericAttribute {

    DamageStatType STAT_TYPE = new DamageStatType();

    double getDamage();

    void setDamage(double damage);

    double getVariation();

    void setVariation(double variation);

    class DamageStatType extends GenericAttributeStatType {

        @Override
        public StatTotal<GenericAttributeStatType> newTotal(StatSpecifier<GenericAttributeStatType> specifier) {
            return new StatTotal<GenericAttributeStatType>(specifier) {
                double damage = 0;
                double variation = 0;

                @Override
                public void addStat(StatAttribute<GenericAttributeStatType> stat) {
                    damage += ((Damage) stat).getDamage();
                    variation += ((Damage) stat).getVariation();
                }

                @Override
                public void addTo(List<String> lore, String prefix) {
                    GenericAttributeSpecifier specifier = (GenericAttributeSpecifier) getStatSpecifier();
                    if (variation > 0) {
                        double lower = damage - variation;
                        double higher = damage + variation;
                        lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, lower, higher));
                    } else {
                        lore.add(prefix + formatModifier(specifier.operation, specifier.type, specifier.stacking, damage));
                    }
                }

                @Override
                public boolean shouldAddLore() {
                    return damage > 0 || damage < 0 || variation > 0;
                }
            };
        }

        public String formatModifier(Operation operation, Type type, boolean stacking, double lower, double higher) {
            return operation.prefix(lower, stacking) + operation.amount(lower) + "-" + operation.amount(higher) + operation.suffix() + " " + type.getDisplayName();
        }

    }

}
