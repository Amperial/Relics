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
package com.herocraftonline.items.crafting;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Ingredient;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Recipe;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.plugin.Plugin;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class RecipeRenderer<T extends Recipe> extends MapRenderer {

    private static final BufferedImage EMPTY = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static final BufferedImage[] NUMBER = new BufferedImage[10];
    private static final Map<String, BufferedImage> ICONS = new HashMap<>();

    private T recipe;

    public RecipeRenderer(T recipe) {
        this.recipe = recipe;
    }

    protected T getRecipe() {
        return recipe;
    }

    protected void drawIngredient(MapCanvas canvas, int x, int y, Ingredient ingredient) {
        if (ingredient == null) {
            return;
        }

        // Draw slot background image
        drawImage(canvas, x, y, getIcon("SLOT", true));

        // Draw reagent icon
        drawImage(canvas, x + 2, y + 2, getIcon(ingredient.getType().getDisplayIcon(), false));

        // Draw ingredient amount
        drawNumber(canvas, x + 13, y + 11, ingredient.getAmount());
    }

    private void drawNumber(MapCanvas canvas, int x, int y, int number) {
        if (number < 2 || number > 128) {
            return;
        }

        // Make sure number images are loaded
        if (!ICONS.containsKey("NUMBERS")) {
            BufferedImage numbers = getIcon("NUMBERS", true);
            if (numbers == EMPTY) {
                return;
            }
            for (int i = 0; i < 10; i++) {
                NUMBER[i] = numbers.getSubimage(i * 5, 0, 5, 7);
            }
        }
        // Draw each digit of the number
        while (number > 0) {
            int digit = number % 10;
            number = number / 10;
            drawImage(canvas, x, y, NUMBER[digit]);
            x -= 6;
        }
    }

    private void drawImage(MapCanvas canvas, int x, int y, Image image) {
        if (image == null) {
            return;
        }
        byte[] bytes = MapPalette.imageToBytes(image);
        for (int x2 = 0; x2 < image.getWidth(null); x2++) {
            for (int y2 = 0; y2 < image.getHeight(null); y2++) {
                byte color = bytes[y2 * image.getWidth(null) + x2];
                if (color != (byte) 0) {
                    canvas.setPixel(x + x2, y + y2, color);
                }
            }
        }
    }

    private BufferedImage getIcon(String name, boolean replace) {
        return ICONS.computeIfAbsent(name, icon -> loadIcon(icon, replace));
    }

    private BufferedImage loadIcon(String icon, boolean replace) {
        String fileName = "pack/icons/" + icon + ".png";

        // Save icon from plugin files
        Plugin plugin = Relics.instance();
        try {
            plugin.saveResource(fileName, replace);
        } catch (IllegalArgumentException e) {
            // File doesn't exist in plugin
        }

        // Make sure image exists. If not, use default icon
        File image = new File(plugin.getDataFolder(), fileName);
        if (!image.exists()) {
            return EMPTY;
        }

        // Load image from file
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
            return EMPTY;
        }
    }

}
