package primalcat.thaumcraft.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.AspectList;
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
    public static void registerObjectTag(Block block, AspectList aspectList){
        AspectInit.putItemAspects(block.toString(), aspectList);
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

        // Flux = TAINT
        //items
        registerObjectTag("forge:ores/diamond", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.GREED, 3).add(AspectInit.CRYSTAL, 3));
        registerObjectTag("forge:ores/lapis", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.SENSES, 3));
        registerObjectTag("forge:gem/diamond", new AspectList().add(AspectInit.CRYSTAL, 4).add(AspectInit.GREED, 4));
        registerObjectTag("forge:ores/redstone", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.ENERGY, 2).add(AspectInit.MECHANISM, 2));
        registerObjectTag("forge:ores/emerald", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.GREED, 4).add(AspectInit.CRYSTAL, 3));
        registerObjectTag("forge:gem/emerald", new AspectList().add(AspectInit.CRYSTAL, 4).add(AspectInit.GREED, 5));
        registerObjectTag("forge:nuggets/iron", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ores/iron", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.METAL, 3));
        registerObjectTag("forge:dusts/iron", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ENTROPY, 1));
        registerObjectTag("forge:ores/gold", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.METAL, 2).add(AspectInit.GREED, 1));
        registerObjectTag("forge:dusts/gold", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.ENTROPY, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:ores/quartz", new AspectList().add(AspectInit.EARTH, 1).add(AspectInit.ENERGY, 2).add(AspectInit.FIRE, 1));
        registerObjectTag("forge:dusts/redstone", new AspectList().add(AspectInit.ENERGY, 2).add(AspectInit.MECHANISM, 1));
        registerObjectTag("forge:dusts/glowstone", new AspectList().add(AspectInit.SENSES, 1).add(AspectInit.LIGHT, 2));
        registerObjectTag(Items.GLOWSTONE, new AspectList().add(AspectInit.SENSES, 3).add(AspectInit.LIGHT, 10));
        registerObjectTag("forge:blocks/glowstone", new AspectList().add(AspectInit.SENSES, 3).add(AspectInit.LIGHT, 10));
        registerObjectTag("forge:nuggets/copper", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/copper", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.EXCHANGE, 1));
        registerObjectTag("forge:dusts/copper", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.ENTROPY, 1).add(AspectInit.EXCHANGE, 1));
        registerObjectTag("forge:ores/copper", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.EARTH, 1).add(AspectInit.EXCHANGE, 1));
        registerObjectTag("forge:nuggets/tin", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/tin", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.CRYSTAL, 1));
        registerObjectTag("forge:dusts/tin", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.ENTROPY, 1).add(AspectInit.CRYSTAL, 1));
        registerObjectTag("forge:ores/tin", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ENTROPY, 1).add(AspectInit.CRYSTAL, 1));
        registerObjectTag("forge:nuggets/silver", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/silver", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.GREED, 1));
        registerObjectTag("forge:dusts/silver", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.ENTROPY, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:ores/silver", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ENTROPY, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:nuggets/lead", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/lead", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:dusts/lead", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.ENTROPY, 1).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:ores/lead", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ENTROPY, 1).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:ores/lead", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ENTROPY, 1).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:ores/coal", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.ENERGY, 15).add(AspectInit.FIRE, 15));
        registerObjectTag(Items.COAL, new AspectList().add(AspectInit.ENERGY, 10).add(AspectInit.FIRE, 10));
        registerObjectTag("forge:ingots/uranium", new AspectList().add(AspectInit.METAL, 10).add(AspectInit.DEATH, 5).add(AspectInit.ENERGY, 10));
        registerObjectTag("forge:gems/ruby", new AspectList().add(AspectInit.CRYSTAL, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:gems/green_sapphire", new AspectList().add(AspectInit.CRYSTAL, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:gems/sapphire", new AspectList().add(AspectInit.CRYSTAL, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:ingots/steel", new AspectList().add(AspectInit.METAL, 15).add(AspectInit.ORDER, 5));
        registerObjectTag("forge:items/rubber", new AspectList().add(AspectInit.MOTION, 5).add(AspectInit.TOOL, 5));
        registerObjectTag("forge:stone", new AspectList().add(AspectInit.EARTH, 5));
        registerObjectTag("forge:stones/granite", new AspectList().add(AspectInit.EARTH, 5));
        registerObjectTag("forge:stones/diorite", new AspectList().add(AspectInit.EARTH, 5));
        registerObjectTag("forge:stones/andesite", new AspectList().add(AspectInit.EARTH, 5));
        registerObjectTag("forge:cobblestone", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.ENTROPY, 1));
        registerObjectTag(Items.BEDROCK, new AspectList().add(AspectInit.VOID, 25).add(AspectInit.ENTROPY, 25).add(AspectInit.EARTH, 25).add(AspectInit.DARKNESS, 25));
        registerObjectTag("forge:dirt", new AspectList().add(AspectInit.EARTH, 5));
        registerObjectTag(Items.DIRT, new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.PLANT, 1));
        registerObjectTag(Items.FARMLAND, new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.WATER, 2).add(AspectInit.ORDER, 2));
        registerObjectTag("forge:sand", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.ENTROPY, 5));
        registerObjectTag("forge:grass", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.PLANT, 2));
        registerObjectTag(Items.DIRT_PATH, new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.PLANT, 2).add(AspectInit.ORDER, 2));
        registerObjectTag("forge:endstone", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.DARKNESS, 5));
        registerObjectTag("forge:gravel", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.ENTROPY, 2));
        registerObjectTag(Items.MYCELIUM, new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.PLANT, 1).add(AspectInit.TAINT, 1));
        registerObjectTag(Items.CLAY_BALL, new AspectList().add(AspectInit.WATER, 5).add(AspectInit.EARTH, 5));
        registerObjectTag(Items.BRICK, new AspectList().add(AspectInit.FIRE, 1));
        registerObjectTag(Items.BRICKS, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.SENSES, 1));
        registerObjectTag("forge:netherrack", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.FIRE, 2));
        registerObjectTag("forge:ingots/brick_nether", new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1));

        registerObjectTag(Items.SOUL_SAND, new AspectList().add(AspectInit.EARTH, 3).add(AspectInit.TRAP, 1).add(AspectInit.SOUL, 3));
        registerObjectTag("forge:glass", new AspectList().add(AspectInit.CRYSTAL, 5));
        registerObjectTag(Items.MOSSY_COBBLESTONE, new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.PLANT, 3).add(AspectInit.ENTROPY, 1));
        registerObjectTag("forge:obsidian", new AspectList().add(AspectInit.EARTH, 5).add(AspectInit.FIRE, 5).add(AspectInit.DARKNESS, 5));
        registerObjectTag(Items.STONE_BRICKS, new AspectList().add(AspectInit.EARTH, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 2), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ENTROPY, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 3), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ORDER, 1));
        registerObjectTag(Blocks.SANDSTONE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.CYAN_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag("forge:terracotta", new AspectList().add(AspectInit.SENSES, 1).add(AspectInit.FIRE, 1).add(AspectInit.EARTH, 1));

        registerObjectTag(Items.BLACK_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.BLACK_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.BLUE_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.BLUE_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.WHITE_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.WHITE_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.RED_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.RED_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.YELLOW_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.YELLOW_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.GREEN_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.GREEN_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.LIME_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.LIME_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.ORANGE_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.ORANGE_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.BROWN_CONCRETE_POWDER, new AspectList().add(AspectInit.WATER, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        registerObjectTag(Items.BROWN_CONCRETE, new AspectList().add(AspectInit.FIRE, 1).add(AspectInit.ORDER, 1).add(AspectInit.ENTROPY, 1).add(AspectInit.EARTH, 1));
        // items test
        registerObjectTag(Items.GHAST_TEAR, new AspectList().add(AspectInit.UNDEAD, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
        registerObjectTag("forge:ingots/iron", new AspectList().add(AspectInit.METAL, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
        registerObjectTag(Items.CAKE, new AspectList().add(AspectInit.DESIRE, 1).add(AspectInit.LIFE, 2));
        registerObjectTag(Blocks.DARK_OAK_FENCE_GATE, new AspectList().add(AspectInit.TRAP, 5).add(AspectInit.MECHANISM, 5));
        registerObjectTag("minecraft:water", new AspectList().add(AspectInit.WATER, 5));
    }


}
