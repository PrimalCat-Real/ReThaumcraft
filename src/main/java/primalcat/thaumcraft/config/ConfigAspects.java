package primalcat.thaumcraft.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.api.AspectList;
import primalcat.thaumcraft.init.AspectInit;

import java.io.*;
import java.lang.reflect.Type;
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
            Type mapType = new TypeToken<Map<String, Map<String, AspectList>>>() {}.getType();
            Map<String, Map<String, AspectList>> data = gson.fromJson(reader, mapType);
            return data;
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // Handle JSON parsing or file not found errors here
            e.printStackTrace();
            // You may choose to throw an exception or return null/empty map if needed.
            return null;
        }
    }

    public static void registerObjectTag(String item, AspectList aspectList){
        AspectInit.putItemAspects(item, aspectList);
    }
    public static void registerObjectTag(Item item, AspectList aspectList){
        AspectInit.putItemAspects(item.toString(), aspectList);
    }
    public static void registerObjectTag(ItemStack item, AspectList aspectList){
        AspectInit.putItemAspects(item.toString(), aspectList);
    }
    public static void registerEntityTag(Entity entity, AspectList aspectList) {
        AspectInit.putEntityAspects(entity.toString(), aspectList);
    }
    public static void registerEntityTag(String entity, AspectList aspectList) {
        AspectInit.putEntityAspects(entity, aspectList);
    }

    private static String createDefaultConfigData(){
        Map<String, LinkedHashMap<String, AspectList>> categories = new HashMap<>();
        categories.put("items", AspectInit.getItemAspects());
        categories.put("entities", AspectInit.getEntityAspects());

        String json = gson.toJson(categories);
        return json;
    }

    private static void createAndFillConfigFile() {
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
            Thaumcraft.LOGGER.info("Check config folder is exist");
            File configFile = new File(CONFIG_FILE_PATH);

            initDefaultAspects();

            if (!configFile.exists()) {
                // The file does not exist, create it with default params
                createAndFillConfigFile();
            }else {
                Map<String, Map<String, AspectList>> configFileContent = readConfigFile();
                if (configFileContent != null){
                    AspectInit.setEntityAspects((LinkedHashMap<String, AspectList>) configFileContent.get("entities"));
                    AspectInit.setItemAspects((LinkedHashMap<String, AspectList>) configFileContent.get("items"));
                    Thaumcraft.LOGGER.warn("Config aspects successful");
                }else {
                    Thaumcraft.LOGGER.warn("Config file is not valid");
                }
            }
        }
    }


    private static void initDefaultAspects(){
        registerEntityTag("Zombie", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 10).add(AspectInit.EARTH, 5));
        registerEntityTag("Husk", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 10).add(AspectInit.FIRE, 5));
        registerEntityTag("Giant", new AspectList().add(AspectInit.UNDEAD, 25).add(AspectInit.MAN, 15).add(AspectInit.EARTH, 10));
        registerEntityTag("Skeleton", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 5).add(AspectInit.EARTH, 5));
        registerEntityTag("WitherSkeleton", new AspectList().add(AspectInit.UNDEAD, 25).add(AspectInit.MAN, 5).add(AspectInit.ENTROPY, 10));
        registerEntityTag("Creeper", new AspectList().add(AspectInit.PLANT, 15).add(AspectInit.FIRE, 15));
//        registerEntityTag("Creeper", new AspectList().add(AspectInit.PLANT, 15).add(AspectInit.FIRE, 15).add(AspectInit.ENERGY, 15), new ThaumcraftApi.EntityTagsNBT("powered", 1));
        registerEntityTag("Horse", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.EARTH, 5).add(AspectInit.AIR, 5));
        registerEntityTag("Donkey", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.EARTH, 5).add(AspectInit.AIR, 5));
        registerEntityTag("Mule", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.EARTH, 5).add(AspectInit.AIR, 5));
        registerEntityTag("SkeletonHorse", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.UNDEAD, 10).add(AspectInit.EARTH, 5).add(AspectInit.AIR, 5));
        registerEntityTag("ZombieHorse", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.UNDEAD, 5).add(AspectInit.EARTH, 5).add(AspectInit.AIR, 5));
        registerEntityTag("Pig", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.EARTH, 10).add(AspectInit.DESIRE, 5));
        registerEntityTag("XPOrb", new AspectList().add(AspectInit.MIND, 10));
        registerEntityTag("Sheep", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.EARTH, 10));
        registerEntityTag("Cow", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.EARTH, 15));
        registerEntityTag("MushroomCow", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.PLANT, 15).add(AspectInit.EARTH, 15));
        registerEntityTag("SnowMan", new AspectList().add(AspectInit.COLD, 10).add(AspectInit.MAN, 5).add(AspectInit.MECHANISM, 5).add(AspectInit.MAGIC, 5));
        registerEntityTag("Ozelot", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ENTROPY, 10));
        registerEntityTag("Chicken", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.AIR, 5));
        registerEntityTag("Squid", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.WATER, 10));
        registerEntityTag("Wolf", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.EARTH, 10).add(AspectInit.AVERSION, 5));
        registerEntityTag("Bat", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.DARKNESS, 5));
        registerEntityTag("Spider", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ENTROPY, 10).add(AspectInit.TRAP, 10));
        registerEntityTag("Slime", new AspectList().add(AspectInit.LIFE, 10).add(AspectInit.WATER, 10).add(AspectInit.ALCHEMY, 5));
        registerEntityTag("Ghast", new AspectList().add(AspectInit.UNDEAD, 15).add(AspectInit.FIRE, 15));
        registerEntityTag("PigZombie", new AspectList().add(AspectInit.UNDEAD, 15).add(AspectInit.FIRE, 15).add(AspectInit.BEAST, 10));
        registerEntityTag("Enderman", new AspectList().add(AspectInit.ELDRITCH, 10).add(AspectInit.MOTION, 15).add(AspectInit.DESIRE, 5));
        registerEntityTag("CaveSpider", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.DEATH, 10).add(AspectInit.TRAP, 10));
        registerEntityTag("Silverfish", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.EARTH, 10));
        registerEntityTag("Blaze", new AspectList().add(AspectInit.ELDRITCH, 5).add(AspectInit.FIRE, 15).add(AspectInit.FLIGHT, 5));
        registerEntityTag("LavaSlime", new AspectList().add(AspectInit.WATER, 5).add(AspectInit.FIRE, 10).add(AspectInit.ALCHEMY, 5));
        registerEntityTag("EnderDragon", new AspectList().add(AspectInit.ELDRITCH, 50).add(AspectInit.BEAST, 30).add(AspectInit.ENTROPY, 50).add(AspectInit.FLIGHT, 10));
        registerEntityTag("WitherBoss", new AspectList().add(AspectInit.UNDEAD, 50).add(AspectInit.ENTROPY, 25).add(AspectInit.FIRE, 25));
        registerEntityTag("Witch", new AspectList().add(AspectInit.MAN, 15).add(AspectInit.MAGIC, 5).add(AspectInit.ALCHEMY, 10));
        registerEntityTag("Villager", new AspectList().add(AspectInit.MAN, 15));
        registerEntityTag("VillagerGolem", new AspectList().add(AspectInit.METAL, 15).add(AspectInit.MAN, 5).add(AspectInit.MECHANISM, 5).add(AspectInit.MAGIC, 5));
        registerEntityTag("EnderCrystal", new AspectList().add(AspectInit.ELDRITCH, 15).add(AspectInit.AURA, 15).add(AspectInit.LIFE, 15));
        registerEntityTag("ItemFrame", new AspectList().add(AspectInit.SENSES, 5).add(AspectInit.CRAFT, 5));
        registerEntityTag("Painting", new AspectList().add(AspectInit.SENSES, 10).add(AspectInit.CRAFT, 5));
        registerEntityTag("Guardian", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ELDRITCH, 10).add(AspectInit.WATER, 10));
//        registerEntityTag("Guardian", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ELDRITCH, 15).add(AspectInit.WATER, 15), new ThaumcraftApi.EntityTagsNBT("Elder", true));
        registerEntityTag("Rabbit", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.EARTH, 5).add(AspectInit.MOTION, 5));
        registerEntityTag("Endermite", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.ELDRITCH, 5).add(AspectInit.MOTION, 5));
        registerEntityTag("PolarBear", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.COLD, 10));
        registerEntityTag("Shulker", new AspectList().add(AspectInit.ELDRITCH, 10).add(AspectInit.TRAP, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.PROTECT, 5));
        registerEntityTag("EvocationIllager", new AspectList().add(AspectInit.ELDRITCH, 5).add(AspectInit.MAGIC, 5).add(AspectInit.MAN, 10));
        registerEntityTag("VindicationIllager", new AspectList().add(AspectInit.AVERSION, 5).add(AspectInit.MAGIC, 5).add(AspectInit.MAN, 10));
        registerEntityTag("IllusionIllager", new AspectList().add(AspectInit.SENSES, 5).add(AspectInit.MAGIC, 5).add(AspectInit.MAN, 10));
        registerEntityTag("Llama", new AspectList().add(AspectInit.BEAST, 15).add(AspectInit.WATER, 5));
        registerEntityTag("Parrot", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.SENSES, 5));
        registerEntityTag("Stray", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 5).add(AspectInit.TRAP, 5));
        registerEntityTag("Vex", new AspectList().add(AspectInit.ELDRITCH, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.MAGIC, 5).add(AspectInit.MAN, 5));
        registerEntityTag("Stray", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 5).add(AspectInit.TRAP, 5));
//        registerEntityTag("Thaumcraft.TAINTRift", new AspectList().add(AspectInit.TAINT, 20).add(AspectInit.ELDRITCH, 20).add(AspectInit.AURA, 20));
//        registerEntityTag("Thaumcraft.Firebat", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.FLIGHT, 5).add(AspectInit.FIRE, 10));
//        registerEntityTag("Thaumcraft.Pech", new AspectList().add(AspectInit.MAN, 10).add(AspectInit.AURA, 5).add(AspectInit.EXCHANGE, 10).add(AspectInit.DESIRE, 5), new ThaumcraftApi.EntityTagsNBT("PechType", 0));
//        registerEntityTag("Thaumcraft.Pech", new AspectList().add(AspectInit.MAN, 10).add(AspectInit.AURA, 5).add(AspectInit.EXCHANGE, 10).add(AspectInit.AVERSION, 5), new ThaumcraftApi.EntityTagsNBT("PechType", 1));
//        registerEntityTag("Thaumcraft.Pech", new AspectList().add(AspectInit.MAN, 10).add(AspectInit.AURA, 5).add(AspectInit.EXCHANGE, 10).add(AspectInit.MAGIC, 5), new ThaumcraftApi.EntityTagsNBT("PechType", 2));
//        registerEntityTag("Thaumcraft.ThaumSlime", new AspectList().add(AspectInit.LIFE, 5).add(AspectInit.WATER, 5).add(AspectInit.TAINT, 5).add(AspectInit.ALCHEMY, 5));
//        registerEntityTag("Thaumcraft.BrainyZombie", new AspectList().add(AspectInit.UNDEAD, 20).add(AspectInit.MAN, 10).add(AspectInit.MIND, 5).add(AspectInit.AVERSION, 5));
//        registerEntityTag("Thaumcraft.GiantBrainyZombie", new AspectList().add(AspectInit.UNDEAD, 25).add(AspectInit.MAN, 15).add(AspectInit.MIND, 5).add(AspectInit.AVERSION, 10));
//        registerEntityTag("Thaumcraft.Taintacle", new AspectList().add(AspectInit.TAINT, 15).add(AspectInit.BEAST, 10));
//        registerEntityTag("Thaumcraft.TaintSeed", new AspectList().add(AspectInit.TAINT, 20).add(AspectInit.AURA, 10).add(AspectInit.PLANT, 5));
//        registerEntityTag("Thaumcraft.TaintSeedPrime", new AspectList().add(AspectInit.TAINT, 25).add(AspectInit.AURA, 15).add(AspectInit.PLANT, 5));
//        registerEntityTag("Thaumcraft.TaintacleTiny", new AspectList().add(AspectInit.TAINT, 5).add(AspectInit.BEAST, 5));
//        registerEntityTag("Thaumcraft.TaintSwarm", new AspectList().add(AspectInit.TAINT, 15).add(AspectInit.AIR, 5));
//        registerEntityTag("Thaumcraft.MindSpider", new AspectList().add(AspectInit.TAINT, 5).add(AspectInit.FIRE, 5));
//        registerEntityTag("Thaumcraft.EldritchGuardian", new AspectList().add(AspectInit.ELDRITCH, 20).add(AspectInit.DEATH, 20).add(AspectInit.UNDEAD, 20));
//        registerEntityTag("Thaumcraft.CultistKnight", new AspectList().add(AspectInit.ELDRITCH, 5).add(AspectInit.MAN, 15).add(AspectInit.AVERSION, 5));
//        registerEntityTag("Thaumcraft.CultistCleric", new AspectList().add(AspectInit.ELDRITCH, 5).add(AspectInit.MAN, 15).add(AspectInit.AVERSION, 5));
//        registerEntityTag("Thaumcraft.EldritchCrab", new AspectList().add(AspectInit.ELDRITCH, 10).add(AspectInit.BEAST, 10).add(AspectInit.TRAP, 10));
//        registerEntityTag("Thaumcraft.InhabitedZombie", new AspectList().add(AspectInit.ELDRITCH, 10).add(AspectInit.UNDEAD, 10).add(AspectInit.MAN, 5));
//        registerEntityTag("Thaumcraft.TaintSeed", new AspectList().add(AspectInit.PLANT, 20).add(AspectInit.BEAST, 20).add(AspectInit.TAINT, 20));
//        registerEntityTag("Thaumcraft.TaintSeedPrime", new AspectList().add(AspectInit.PLANT, 30).add(AspectInit.BEAST, 30).add(AspectInit.TAINT, 30));
//        registerEntityTag("Thaumcraft.EldritchWarden", new AspectList().add(AspectInit.ELDRITCH, 40).add(AspectInit.DEATH, 40).add(AspectInit.UNDEAD, 40));
//        registerEntityTag("Thaumcraft.EldritchGolem", new AspectList().add(AspectInit.ELDRITCH, 40).add(AspectInit.ENERGY, 40).add(AspectInit.MECHANISM, 40));
//        registerEntityTag("Thaumcraft.CultistLeader", new AspectList().add(AspectInit.ELDRITCH, 40).add(AspectInit.AVERSION, 40).add(AspectInit.MAN, 40));
//        registerEntityTag("Thaumcraft.TaintacleGiant", new AspectList().add(AspectInit.ELDRITCH, 40).add(AspectInit.BEAST, 40).add(AspectInit.TAINT, 40));

        // items
        registerObjectTag(Items.GHAST_TEAR, new AspectList().add(AspectInit.UNDEAD, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
        registerObjectTag("forge:ingots/iron", new AspectList().add(AspectInit.METAL, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
    }


}
