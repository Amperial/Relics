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
package com.herocraftonline.items.api.storage.config;

/**
 * Custom configuration files used in the relics plugin.
 *
 * @author Austin Payne
 */
public enum DefaultConfig implements Config {
    ATTRIBUTES("attributes.yml"),
    EQUIPMENT("equipment.yml"),
    ITEMS("items.yml"),
    MESSAGE("lang.yml"),
    MODELS("pack/models.yml");

    private final String fileName;

    DefaultConfig(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

}
