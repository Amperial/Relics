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
package ninja.amp.items.api.item.attribute.attributes.sockets;

import ninja.amp.items.api.item.Item;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.api.item.attribute.attributes.AttributeContainer;

public interface Gem extends ItemAttribute, AttributeContainer {

    String getDisplayName();

    SocketColor getColor();

    void setColor(SocketColor color);

    boolean hasItem();

    Item getItem();

    void setItem(Item item);

}
