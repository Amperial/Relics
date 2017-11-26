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
package com.herocraftonline.items.item.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.model.Model;
import com.herocraftonline.items.api.storage.config.DefaultConfig;
import com.herocraftonline.items.nms.NMSHandler;
import com.herocraftonline.items.util.JsonUtil;
import com.herocraftonline.items.util.ZipUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ModelManager implements com.herocraftonline.items.api.item.model.ModelManager {

    private final ItemPlugin plugin;
    private final Map<String, Model> models;

    public ModelManager(ItemPlugin plugin) {
        this.plugin = plugin;
        this.models = new HashMap<>();

        // Load configured models and generate pack
        loadModels();
    }

    @Override
    public Model getModel(String name) {
        return models.getOrDefault(name, new Model() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getPath() {
                return "";
            }

            @Override
            public void apply(ItemStack item) {
            }
        });
    }

    private void loadModels() {
        ConfigurationSection config = plugin.getConfigManager().getConfig(DefaultConfig.MODELS);

        // Load the base model names for item and block materials
        Map<Material, String> itemModels = getMaterialModels(config, "item");
        Map<Material, String> blockModels = getMaterialModels(config, "block");

        // Materials with models and their associated models
        Map<Material, List<Model>> materialModels = new HashMap<>();

        // Load custom models and associated materials
        if (config.isConfigurationSection("models")) {
            config = config.getConfigurationSection("models");

            for (String name : config.getKeys(false)) {
                ConfigurationSection modelConfig = config.getConfigurationSection(name);

                // Get model path, assets, and associated materials
                String path = modelConfig.getString("model");
                List<String> assets = modelConfig.getStringList("assets");
                Set<Material> materials = modelConfig.getStringList("materials").stream()
                        .map(Material::valueOf).collect(Collectors.toSet());

                try {
                    // Attempt to save model assets located in the plugin
                    plugin.saveResource("pack/models/" + path + ".json");
                    for (String asset : assets) {
                        plugin.saveResource("pack/" + asset);
                    }
                } catch (Exception e) {
                    // Model assets not found in plugin
                }

                // Load custom model
                Model model = new ItemModel(name, path);
                models.put(name, model);

                // Add model to associated material model lists
                for (Material material : materials) {
                    if (!materialModels.containsKey(material)) {
                        materialModels.put(material, new ArrayList<>());
                    }
                    materialModels.get(material).add(model);
                }
            }

            // Set durabilities of custom item models
            for (Material material : itemModels.keySet()) {
                if (materialModels.containsKey(material)) {
                    int maxDurability = material.getMaxDurability();
                    List<Model> models = materialModels.get(material);
                    int modelLimit = Math.min(models.size(), maxDurability);
                    for (int i = 0; i < modelLimit; i++) {
                        ((ItemModel) models.get(i)).setDurability((short) (i + 1));
                    }
                }
            }
        }

        generatePack(materialModels, itemModels, blockModels);
    }

    private void generatePack(Map<Material, List<Model>> materialModels, Map<Material, String> itemModels, Map<Material, String> blockModels) {
        // Save base pack files and example input pack
        plugin.saveResource("pack/models/base/v1_10_R1.zip");
        plugin.saveResource("pack/models/base/v1_11_R1.zip");
        plugin.saveResource("pack/models/base/v1_12_R1.zip");
        plugin.saveResource("pack/input.zip");

        try {
            // Check base, input, and output resource pack files
            File baseFile = new File(plugin.getDataFolder(), "pack/models/base/" + NMSHandler.getVersion() + ".zip");
            File inputFile = new File(plugin.getDataFolder(), "pack/input.zip");
            File outputFile = new File(plugin.getDataFolder(), "pack/output.zip");
            if (!inputFile.exists() || !baseFile.exists() || (outputFile.exists() && !outputFile.delete())) {
                // Must have an input zip, base zip, and location to write output zip
                plugin.getMessenger().log(Level.WARNING, "Input resource pack and base model files required to generate resource pack for custom models.");
                return;
            }

            // Setup base, input, and output zip files
            ZipFile base = new ZipFile(baseFile);
            ZipFile input = new ZipFile(inputFile);
            ZipOutputStream output = new ZipOutputStream(new FileOutputStream(outputFile));

            // Set to keep track of entries that have been written to the output zip
            Set<String> entries = new HashSet<>();

            // Process zip entries for items
            for (Map.Entry<Material, String> item : itemModels.entrySet()) {
                Material material = item.getKey();
                if (!materialModels.containsKey(material)) {
                    // Material is not associated with any custom models
                    continue;
                }

                // Get base path and resource pack path of model file
                String baseName = item.getValue();
                String basePath = "models/" + baseName + ".json";
                String packPath = "assets/minecraft/" + basePath;

                // Load base model stream from zip
                InputStream modelStream = ZipUtil.getEntryStream(input, base, basePath);
                if (modelStream == null) {
                    // Model json file not found in input or base zip
                    continue;
                }

                // Parse json from model stream
                JsonObject modelJson = new JsonParser().parse(new InputStreamReader(modelStream)).getAsJsonObject();

                // Modify overrides
                JsonArray overrides = modelJson.has("overrides") ? modelJson.getAsJsonArray("overrides") : new JsonArray();

                List<Model> models = materialModels.get(material);
                int maxDurability = material.getMaxDurability();
                int modelLimit = Math.min(models.size(), maxDurability);
                for (int i = 0; i < modelLimit; i++) {
                    ItemModel model = (ItemModel) models.get(i);

                    // Create override json to display model
                    JsonObject override = new JsonObject();
                    JsonObject predicate = new JsonObject();
                    predicate.addProperty("damaged", 0);
                    predicate.addProperty("damage", model.getDurability() / (double) maxDurability);
                    override.add("predicate", predicate);
                    override.addProperty("model", model.getPath());

                    // Add override to model overrides
                    overrides.add(override);
                }

                // Add override to display original model by default
                JsonObject override = new JsonObject();
                JsonObject predicate = new JsonObject();
                predicate.addProperty("damaged", 1);
                override.add("predicate", predicate);
                override.addProperty("model", baseName);
                overrides.add(override);

                // Add overrides back to model json
                modelJson.add("overrides", overrides);

                // Save json to output zip
                ZipUtil.newZipEntry(output, JsonUtil.toStream(modelJson), packPath);
                entries.add(packPath);
            }

            // Process zip entries for blocks
            for (Map.Entry<Material, String> block : blockModels.entrySet()) {
                // TODO Process block models
            }

            // Write additional pack files
            writePackFiles(output, "models/item/", entries);
            writePackFiles(output, "models/block/", entries);
            writePackFiles(output, "textures/items/", entries);
            writePackFiles(output, "textures/blocks/", entries);

            // Copy all remaining files from input zip
            ZipUtil.copyZipEntries(output, input, entries);

            output.close();
        } catch (IOException e) {
            // Error generating resource pack
            e.printStackTrace();
        }
    }

    private void writePackFiles(ZipOutputStream output, String dir, Set<String> entries) throws IOException {
        File directory = new File(plugin.getDataFolder(), "pack/" + dir);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File source : files) {
                String path = "assets/minecraft/" + dir + source.getName();
                ZipUtil.newZipEntry(output, new FileInputStream(source), path);
                entries.add(path);
            }
        }
    }

    private static Map<Material, String> getMaterialModels(ConfigurationSection config, String base) {
        Map<Material, String> models = new HashMap<>();
        if (config.isConfigurationSection(base)) {
            config = config.getConfigurationSection(base);
            for (String key : config.getKeys(false)) {
                models.put(Material.valueOf(key), base + "/" + config.getString(key));
            }
        }
        return models;
    }

}
