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

import java.util.Map;

public interface AttributeGroup extends ItemAttribute, AttributeContainer {

    Map<String, ? extends ItemAttribute> getAttributesByName();

    void addAttribute(ItemAttribute... attributes);

    void removeAttribute(ItemAttribute attribute);

}
