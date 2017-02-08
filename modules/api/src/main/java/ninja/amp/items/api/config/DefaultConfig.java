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
package ninja.amp.items.api.config;

/**
 * Custom configuration files used in the amp items plugin.
 *
 * @author Austin Payne
 */
public enum DefaultConfig implements Config {
    ATTRIBUTES("attributes.yml"),
    EQUIPMENT("equipment.yml"),
    ITEMS("items.yml"),
    MESSAGE("lang.yml"),
    SOCKETS("sockets.yml");

    private final String fileName;

    DefaultConfig(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

}
