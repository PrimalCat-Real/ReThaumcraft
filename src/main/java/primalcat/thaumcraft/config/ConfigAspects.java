package primalcat.thaumcraft.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import primalcat.thaumcraft.api.Aspect;
import primalcat.thaumcraft.api.AspectList;
import primalcat.thaumcraft.init.AspectInit;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigAspects {
    private static final String CONFIG_FOLDER = "config/thaumcraft";
    private static final String CONFIG_FILE_PATH = CONFIG_FOLDER + "/aspects.json";


    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(AspectList.class, new AspectList())
            .setPrettyPrinting()
            .create();

    private static Map<String, Map<String, AspectList>> readConfig;


    // check if json config is correct
    public static boolean isValid(String json) {
        try {
            JsonParser parser = new JsonParser();
            parser.parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createConfigFile() {
        File location = new File(Minecraft.getInstance().gameDirectory + "/" + CONFIG_FOLDER);
        if (!location.exists()) {
            location.mkdirs();
            return true;
        } else if (location.exists()) {
            return true;
        }else {
            return false;
        }
//            initAspects();
    }

    private static Map<String, Map<String, AspectList>> readConfigFile() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            Type mapType = new TypeToken<Map<String, Map<String, AspectList>>>() {
            }.getType();
            Map<String, Map<String, AspectList>> data = gson.fromJson(reader, mapType);
            return data;
        }
    }

    public static void registerObjectTag(String item, AspectList aspectList){
        AspectInit.putItemAspects(item, aspectList);
    }
    public static void registerObjectTag(Item item, AspectList aspectList){
        AspectInit.putItemAspects(item.toString(), aspectList);
    }
    public static void registerEntityTag(String entity, AspectList aspectList) {
        AspectInit.putEntityAspects(entity, aspectList);

//        aspects.put("oreLapis", new AspectList().add(Aspect.EARTH, 5).add(Aspect.SENSES, 15));
//        aspects.put("oreRedstone", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENERGY, 15));
//        aspects.put("oreQuartz", new AspectList().add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 10));
    }

    private static String createDefaultConfigData(){
        Map<String, LinkedHashMap<String, AspectList>> categories = new HashMap<>();
        categories.put("items", AspectInit.getItemAspects());
        categories.put("entities", AspectInit.getEntityAspects());

        String json = gson.toJson(categories);
        return json;
    }

    private static void createAndFillConfigFile() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(readConfig);

        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            writer.write(createDefaultConfigData());
            System.out.println("Config file created and filled: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to create config file: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }
    public static void initAspectsConfig() throws IOException {
        if (createConfigFile()){
            System.out.println("Config folder created");
            File configFile = new File(CONFIG_FILE_PATH);
            if (!configFile.exists()) {
                // The file does not exist, create it with your params
                createAndFillConfigFile();
            }else {
                Map<String, Map<String, AspectList>> configFileContent = readConfigFile();
                if (isValid(gson.toJson(configFileContent))){
                    System.out.println("Only fill at least");
                }else {

                }
            }
        }
    }


}
