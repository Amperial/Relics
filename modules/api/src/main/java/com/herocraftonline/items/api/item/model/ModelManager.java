/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.model;

/**
 * Manages custom item models.
 *
 * @author Austin Payne
 */
public interface ModelManager {

    /**
     * Gets the model of a certain name.
     *
     * @param name the model's name
     * @return the model
     */
    Model getModel(String name);

}
