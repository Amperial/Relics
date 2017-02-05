/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.item.attributes;

import ninja.amp.items.api.item.attribute.AttributeFactory;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;

public enum DefaultAttributeType implements AttributeType {
    DAMAGE("damage", 2),
    GEM("gem", 4),
    GROUP("group", 5),
    MINECRAFT("minecraft", 6),
    MODEL("model", Integer.MAX_VALUE),
    RARITY("rarity", 0),
    SMITE("smite", Integer.MAX_VALUE),
    SOCKET("socket", 3),
    TEXT("text", 1);

    private final String name;
    private final String fileName;
    private int lorePosition;
    private AttributeFactory<? extends ItemAttribute> factory;

    DefaultAttributeType(String name, int lorePosition) {
        this.name = name;
        this.fileName = "attributes/" + name + ".yml";
        this.lorePosition = lorePosition;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getLorePosition() {
        return lorePosition;
    }

    @Override
    public void setLorePosition(int position) {
        this.lorePosition = position;
    }

    @Override
    public AttributeFactory<? extends ItemAttribute> getFactory() {
        return factory;
    }

    public void setFactory(AttributeFactory<? extends ItemAttribute> factory) {
        this.factory = factory;
    }

    @Override
    public boolean test(ItemAttribute itemAttribute) {
        return equals(itemAttribute.getType());
    }

}
