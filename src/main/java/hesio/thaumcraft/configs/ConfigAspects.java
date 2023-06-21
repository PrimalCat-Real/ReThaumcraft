package hesio.thaumcraft.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.minecraft.client.ObjectMapper;
import hesio.thaumcraft.api.aspects.Aspect;
import hesio.thaumcraft.api.aspects.AspectList;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigAspects {
    private static final String CONFIG_FOLDER = "config/thaumcraft";
    private static final String CONFIG_FILE_PATH = CONFIG_FOLDER + "/aspects.json";

    public static void createConfigFile() {
        try {
            initAspects();
        } catch (IOException e) {
            System.err.println("Failed to create config file: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }

    private static void initAspects() throws IOException {
        Map<String, AspectList> defaultAspects = createDefaultAspects();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AspectList.class, new AspectList())
                .create();

        String json = gson.toJson(defaultAspects);

        FileWriter writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE_PATH);
            writer.write(json);
            System.out.println("Config file created: " + CONFIG_FILE_PATH);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static Map<String, AspectList> createDefaultAspects() {
        Map<String, AspectList> aspects = new HashMap<>();
        aspects.put("cobblestone", new AspectList().add(Aspect.AIR, 40));
        return aspects;
    }

    public static void main(String[] args) {
        createConfigFile();
    }
}