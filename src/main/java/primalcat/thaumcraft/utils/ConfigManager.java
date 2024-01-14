package primalcat.thaumcraft.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.aspects.AspectList;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigManager {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(AspectList.class, new AspectList())
            .setPrettyPrinting()
            .create();

    private static final String ASPECTS_FILE = "aspects.json";
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(Thaumcraft.MODID);
    private static Path aspectsPath = CONFIG_PATH.resolve(ASPECTS_FILE);

    private static boolean isValidJsonFile(Path filePath) {
        try {
            JsonObject jsonObject;
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                jsonObject = gson.fromJson(reader, JsonObject.class);
            }
            return jsonObject != null;
        } catch (IOException | JsonSyntaxException e) {
            return false;
        }
    }
    public static void checkOrCreateConfigFolder(Path configPath) throws IOException {
        try {
            if (Files.notExists(configPath)) {
                Files.createDirectories(configPath);
                Thaumcraft.LOGGER.info("Created '" + configPath + "' folder in 'config'.");
            }
            if (Files.notExists(configPath.resolve("aspects.json"))) {
                // create default config files
                createDefaultAspectsFile(configPath.resolve("aspects.json"));
                Thaumcraft.LOGGER.info("Created 'aspect.json' file in '" + CONFIG_PATH + "' folder.");
            }
            if(isValidJsonFile(configPath.resolve("aspects.json"))){
                readConfig(configPath.resolve("aspects.json"));
                // set default aspects
            }
        } catch (IOException e) {
            Thaumcraft.LOGGER.error("Failed to save config file: " + configPath.resolve("aspects.json"), e);
        }
    }


    private static void createDefaultAspectsFile(Path filePath) throws IOException {
        LinkedHashMap<String, AspectList> categories = Config.getAspects();
        String json = gson.toJson(categories);
        Files.writeString(filePath, json);
    }

    private static void readConfig(Path filePath) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            Type mapType = new TypeToken<Map<String, AspectList>>() {}.getType();
            Map<String, AspectList> data = gson.fromJson(reader, mapType);
            Config.setDefaultAspects(new LinkedHashMap<>(data));
            System.out.println(Config.getAspects());
        } catch (JsonSyntaxException e) {
            Thaumcraft.LOGGER.error("Invalid JSON syntax in file: " + filePath, e);
        }
    }

}
