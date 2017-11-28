/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.config;

import com.herocraftonline.items.api.storage.config.Config;

/**
 * Item configuration files used in the relics plugin.
 *
 * @author Austin Payne
 */
public class ItemConfig implements Config {

    private final String item;
    private final String fileName;

    public ItemConfig(String item) {
        this.item = item;
        this.fileName = "items/" + item + ".yml";
    }

    public String getItem() {
        return item;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ItemConfig && fileName.equals(((ItemConfig) obj).getFileName());
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

}
