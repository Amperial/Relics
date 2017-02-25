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

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.item.attribute.AttributeFactory;
import ninja.amp.items.api.item.attribute.AttributeType;
import ninja.amp.items.api.item.attribute.ItemAttribute;
import ninja.amp.items.item.attributes.misc.SmiteAttribute;
import ninja.amp.items.item.attributes.sockets.GemAttribute;
import ninja.amp.items.item.attributes.sockets.SocketAttribute;
import ninja.amp.items.item.attributes.stats.DamageAttribute;
import ninja.amp.items.item.attributes.stats.LevelRequirementAttribute;
import ninja.amp.items.item.attributes.stats.MinecraftAttribute;

public enum DefaultAttributeType implements AttributeType {
    DAMAGE("damage", Integer.MAX_VALUE) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new DamageAttribute.Factory(plugin);
        }
    },
    DURABILITY("durability", 6) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new DurabilityAttribute.Factory(plugin);
        }
    },
    GEM("gem", 5) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new GemAttribute.Factory(plugin);
        }
    },
    GROUP("group", 0) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new GroupAttribute.Factory(plugin);
        }
    },
    LEVEL("level", 2) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new LevelAttribute.Factory(plugin);
        }
    },
    LEVEL_REQUIREMENT("level-requirement", Integer.MIN_VALUE) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new LevelRequirementAttribute.Factory(plugin);
        }
    },
    MINECRAFT("minecraft", Integer.MAX_VALUE) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new MinecraftAttribute.Factory(plugin);
        }
    },
    MODEL("model", Integer.MAX_VALUE) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new ModelAttribute.Factory(plugin);
        }
    },
    RARITY("rarity", 1) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new RarityAttribute.Factory(plugin);
        }
    },
    SMITE("smite", Integer.MAX_VALUE) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new SmiteAttribute.Factory(plugin);
        }
    },
    SOCKET("socket", 4) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new SocketAttribute.Factory(plugin);
        }
    },
    SOULBOUND("soulbound", 7) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new SoulboundAttribute.Factory(plugin);
        }
    },
    TEXT("text", 3) {
        @Override
        AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin) {
            return new TextAttribute.Factory(plugin);
        }
    };

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
    public boolean test(ItemAttribute itemAttribute) {
        return equals(itemAttribute.getType());
    }

    @Override
    public AttributeFactory<? extends ItemAttribute> getFactory() {
        return factory;
    }

    public void setFactory(AttributeFactory<? extends ItemAttribute> factory) {
        this.factory = factory;
    }

    abstract AttributeFactory<? extends ItemAttribute> loadFactory(ItemPlugin plugin);

    public static void loadFactories(ItemPlugin plugin) {
        for (DefaultAttributeType type : values()) {
            type.setFactory(type.loadFactory(plugin));
        }
    }

}
