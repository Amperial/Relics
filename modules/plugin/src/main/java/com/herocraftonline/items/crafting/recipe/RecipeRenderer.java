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
package com.herocraftonline.items.crafting.recipe;

import com.herocraftonline.items.Relics;
import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Ingredient;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Recipe;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Result;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class RecipeRenderer<T extends Recipe> extends MapRenderer {

    private static Map<String, BufferedImage> icons = new HashMap<>();
    private static BufferedImage[] numbers = new BufferedImage[10];
    private static BufferedImage unknown = null;

    private T recipe;

    public RecipeRenderer(T recipe) {
        this.recipe = recipe;
    }

    protected T getRecipe() {
        return recipe;
    }

    protected void drawIngredient(MapCanvas canvas, int x, int y, Ingredient ingredient) {
        // Load required icons
        if (unknown == null) {
            unknown = loadIcon("UNKNOWN").orElse(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
            BufferedImage number = loadIcon("NUMBERS").orElse(new BufferedImage(50, 7, BufferedImage.TYPE_INT_ARGB));
            for (int i = 0; i < 10; i++) {
                numbers[i] = number.getSubimage(i * 5, 0, 5, 7);
            }
        }

        // Draw slot background image
        drawImage(canvas, x, y, getIcon("SLOT"));

        if (ingredient != null) {
            // Draw reagent icon
            drawImage(canvas, x + 2, y + 2, getIcon(ingredient.getType().getDisplayIcon()));

            // Draw ingredient amount
            drawNumber(canvas, x + 13, y + 11, ingredient.getAmount());
        }
    }

    protected void drawResult(MapCanvas canvas, int x, int y, Result result, boolean arrow) {
        // Draw arrow image
        if (arrow) {
            drawImage(canvas, x, y + 2, getIcon("RESULT"));
            x += 18;
        }

        // Draw slot background image
        drawImage(canvas, x, y, getIcon("SLOT"));

        // Draw result material icon
        drawImage(canvas, x + 2, y + 2, getIcon(result.getDisplayIcon()));

        // Draw result amount
        drawNumber(canvas, x + 13, y + 11, result.getItem().getAmount());
    }

    private void drawNumber(MapCanvas canvas, int x, int y, int number) {
        if (number < 1 || number > 128) {
            return;
        }

        // Draw each digit of the number
        while (number > 0) {
            int digit = number % 10;
            number = number / 10;
            drawImage(canvas, x, y, numbers[digit]);
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

    private BufferedImage getIcon(String name) {
        return icons.computeIfAbsent(name, icon -> loadIcon(icon).orElse(unknown));
    }

    private Optional<BufferedImage> loadIcon(String icon) {
        String fileName = "pack/icons/" + icon + ".png";

        // Save icon from plugin files
        ItemPlugin plugin = Relics.instance();
        plugin.saveResource(fileName);

        // Make sure image exists. If not, use default icon
        File image = new File(plugin.getDataFolder(), fileName);
        if (!image.exists()) {
            return Optional.empty();
        }

        // Load image from file
        try {
            return Optional.of(ImageIO.read(image));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
