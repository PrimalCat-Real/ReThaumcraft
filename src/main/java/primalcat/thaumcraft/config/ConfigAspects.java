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
        registerEntityTag("Zombie", new AspectList().add(AspectInit.EXANIMIS, 2).add(AspectInit.HUMANUS, 1).add(AspectInit.TERRA, 1));
        registerEntityTag("Husk", new AspectList().add(AspectInit.EXANIMIS, 2).add(AspectInit.HUMANUS, 1).add(AspectInit.IGNIS, 1));
        registerEntityTag("Giant", new AspectList().add(AspectInit.EXANIMIS, 5).add(AspectInit.HUMANUS, 5).add(AspectInit.TERRA, 2));
        registerEntityTag("Skeleton", new AspectList().add(AspectInit.EXANIMIS, 3).add(AspectInit.HUMANUS, 1).add(AspectInit.TERRA, 1));
        registerEntityTag("WitherSkeleton", new AspectList().add(AspectInit.EXANIMIS, 4).add(AspectInit.HUMANUS, 1).add(AspectInit.PERDITIO, 1).add(AspectInit.INFERNUS, 2));
        registerEntityTag("Creeper", new AspectList().add(AspectInit.HERBA, 2).add(AspectInit.IGNIS, 2).add(AspectInit.TELUM, 2));
//        registerEntityTag("Creeper", new AspectList().add(AspectInit.PLANT, 15).add(AspectInit.FIRE, 15).add(AspectInit.ENERGY, 15), new ThaumcraftApi.EntityTagsNBT("powered", 1));
        registerEntityTag("Horse", new AspectList().add(AspectInit.BEAST, 4).add(AspectInit.TERRA, 1).add(AspectInit.AIR, 1));
        registerEntityTag("Donkey", new AspectList().add(AspectInit.BEAST, 3).add(AspectInit.TERRA, 1).add(AspectInit.AIR, 1));
        registerEntityTag("Mule", new AspectList().add(AspectInit.BEAST, 3).add(AspectInit.TERRA, 1).add(AspectInit.AIR, 1));
        registerEntityTag("SkeletonHorse", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.EXANIMIS, 2).add(AspectInit.TERRA, 1).add(AspectInit.AIR, 1));
        registerEntityTag("ZombieHorse", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.EXANIMIS, 2).add(AspectInit.TERRA, 1).add(AspectInit.AIR, 1));
        registerEntityTag("Pig", new AspectList().add(AspectInit.BEAST, 2).add(AspectInit.TERRA, 2).add(AspectInit.GULA, 3));
        registerEntityTag("XPOrb", new AspectList().add(AspectInit.COGNITIO, 2));
        registerEntityTag("Sheep", new AspectList().add(AspectInit.BEAST, 2).add(AspectInit.TERRA, 2));
        registerEntityTag("Cow", new AspectList().add(AspectInit.BEAST, 3).add(AspectInit.TERRA, 3));
        registerEntityTag("MushroomCow", new AspectList().add(AspectInit.BEAST, 3).add(AspectInit.HERBA, 3).add(AspectInit.TERRA, 3));
        registerEntityTag("SnowMan", new AspectList().add(AspectInit.GELUM, 4).add(AspectInit.AQUA, 1).add(AspectInit.PRAECANTATIO, 1));
        registerEntityTag("Ozelot", new AspectList().add(AspectInit.BEAST, 2).add(AspectInit.PERDITIO, 2).add(AspectInit.MOTUS, 1));
        registerEntityTag("Chicken", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.VOLATUS, 1).add(AspectInit.AIR, 1));
        registerEntityTag("Squid", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.AQUA, 2));
        registerEntityTag("Wolf", new AspectList().add(AspectInit.BEAST, 3).add(AspectInit.TERRA, 2).add(AspectInit.AVERSION, 1));
        registerEntityTag("Bat", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.VOLATUS, 1).add(AspectInit.TENEBRAE, 1));
        registerEntityTag("Spider", new AspectList().add(AspectInit.BEAST, 2).add(AspectInit.PERDITIO, 2).add(AspectInit.TRAP, 2));
        registerEntityTag("Slime", new AspectList().add(AspectInit.LIFE, 2).add(AspectInit.LIMUS, 2).add(AspectInit.ALCHEMY, 1));
        registerEntityTag("Ghast", new AspectList().add(AspectInit.EXANIMIS, 2).add(AspectInit.IGNIS, 2).add(AspectInit.INFERNUS, 3).add(AspectInit.IRA,2));
        registerEntityTag("PigZombie", new AspectList().add(AspectInit.EXANIMIS, 3).add(AspectInit.IGNIS, 3).add(AspectInit.IRA, 5));
        registerEntityTag("Enderman", new AspectList().add(AspectInit.ALIENIS, 4).add(AspectInit.ITER, 2).add(AspectInit.AIR, 1).add(AspectInit.INVIDIA, 2).add(AspectInit.SUPERBIA, 3));
        registerEntityTag("CaveSpider", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.VENENUM, 2).add(AspectInit.TRAP, 2));
        registerEntityTag("Silverfish", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.TERRA, 1).add(AspectInit.PERDITIO,1));
        registerEntityTag("Blaze", new AspectList().add(AspectInit.ALIENIS, 2).add(AspectInit.IGNIS, 3).add(AspectInit.VOLATUS, 1).add(AspectInit.INFERNUS, 2));
        registerEntityTag("LavaSlime", new AspectList().add(AspectInit.LIMUS, 3).add(AspectInit.IGNIS, 4).add(AspectInit.ALCHEMY, 1).add(AspectInit.INFERNUS, 2));
        registerEntityTag("EnderDragon", new AspectList().add(AspectInit.ALIENIS, 20).add(AspectInit.BEAST, 20).add(AspectInit.PERDITIO, 10).add(AspectInit.VOLATUS, 2).add(AspectInit.SUPERBIA, 10));
        registerEntityTag("WitherBoss", new AspectList().add(AspectInit.EXANIMIS, 10).add(AspectInit.PERDITIO, 10).add(AspectInit.IGNIS, 15).add(AspectInit.IRA, 7).add(AspectInit.INFERNUS, 7));
        registerEntityTag("Witch", new AspectList().add(AspectInit.HUMANUS, 3).add(AspectInit.PRAECANTATIO, 2).add(AspectInit.ALCHEMY, 3));
        registerEntityTag("Villager", new AspectList().add(AspectInit.HUMANUS, 3).add(AspectInit.GREED, 2));
        registerEntityTag("VillagerGolem", new AspectList().add(AspectInit.METAL, 5).add(AspectInit.TERRA, 5).add(AspectInit.MECHANISM, 3).add(AspectInit.PRAECANTATIO, 2));
        registerEntityTag("EnderCrystal", new AspectList().add(AspectInit.ALIENIS, 3).add(AspectInit.AURA, 3).add(AspectInit.LIFE, 3).add(AspectInit.SUPERBIA, 2).add(AspectInit.VITREUS, 4));
        registerEntityTag("ItemFrame", new AspectList().add(AspectInit.SENSUS, 2).add(AspectInit.FABRICO, 2));
        registerEntityTag("Painting", new AspectList().add(AspectInit.SENSUS, 4).add(AspectInit.FABRICO, 2));
        registerEntityTag("Guardian", new AspectList().add(AspectInit.BEAST, 4).add(AspectInit.ALIENIS, 5).add(AspectInit.AQUA, 5).add(AspectInit.TELUM, 5));
//        registerEntityTag("Guardian", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ELDRITCH, 15).add(AspectInit.WATER, 15), new ThaumcraftApi.EntityTagsNBT("Elder", true));
        registerEntityTag("Rabbit", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.TERRA, 1).add(AspectInit.MOTUS, 1));
        registerEntityTag("Endermite", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.ALIENIS, 1).add(AspectInit.MOTUS, 1).add(AspectInit.IRA,1));
        registerEntityTag("PolarBear", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.GELUM, 2));
        registerEntityTag("Shulker", new AspectList().add(AspectInit.ALIENIS, 4).add(AspectInit.TRAP, 2).add(AspectInit.VOLATUS, 2).add(AspectInit.TUTAMEN, 4));
        registerEntityTag("EvocationIllager", new AspectList().add(AspectInit.ALIENIS, 3).add(AspectInit.PRAECANTATIO, 4).add(AspectInit.HUMANUS, 4));
        registerEntityTag("VindicationIllager", new AspectList().add(AspectInit.AVERSION, 3).add(AspectInit.PRAECANTATIO, 1).add(AspectInit.HUMANUS, 4));
        registerEntityTag("IllusionIllager", new AspectList().add(AspectInit.SENSUS, 3).add(AspectInit.PRAECANTATIO, 4).add(AspectInit.HUMANUS, 4));
        registerEntityTag("Llama", new AspectList().add(AspectInit.BEAST, 5).add(AspectInit.AQUA, 2));
        registerEntityTag("Parrot", new AspectList().add(AspectInit.BEAST, 1).add(AspectInit.VOLATUS, 1).add(AspectInit.SENSUS, 1));
        registerEntityTag("Stray", new AspectList().add(AspectInit.EXANIMIS, 5).add(AspectInit.HUMANUS, 1).add(AspectInit.TRAP, 1));
        registerEntityTag("Vex", new AspectList().add(AspectInit.ALIENIS, 1).add(AspectInit.VOLATUS, 1).add(AspectInit.PRAECANTATIO, 2).add(AspectInit.HUMANUS, 1));

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

        registerObjectTag("forge:gem/emerald", new AspectList().add(AspectInit.VITREUS, 4).add(AspectInit.GREED, 5));
        registerObjectTag("forge:nuggets/iron", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:gem/diamond", new AspectList().add(AspectInit.VITREUS, 4).add(AspectInit.GREED, 4));

        registerObjectTag("forge:dusts/iron", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.PERDITIO, 1));

        registerObjectTag("forge:dusts/gold", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.PERDITIO, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:dusts/redstone", new AspectList().add(AspectInit.POTENTIA, 2).add(AspectInit.MECHANISM, 1));
        registerObjectTag("forge:dusts/glowstone", new AspectList().add(AspectInit.SENSUS, 1).add(AspectInit.LUX, 2));
        registerObjectTag(Items.GLOWSTONE, new AspectList().add(AspectInit.SENSUS, 3).add(AspectInit.LUX, 10));
        registerObjectTag("forge:nuggets/copper", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/copper", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.PERMUTATIO, 1));
        registerObjectTag("forge:dusts/copper", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.PERDITIO, 1).add(AspectInit.PERMUTATIO, 1));
        registerObjectTag("forge:nuggets/tin", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/tin", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.VITREUS, 1));
        registerObjectTag("forge:dusts/tin", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.PERDITIO, 1).add(AspectInit.VITREUS, 1));
        registerObjectTag("forge:ores/tin", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.PERDITIO, 1).add(AspectInit.VITREUS, 1));
        registerObjectTag("forge:nuggets/silver", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/silver", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.GREED, 1));
        registerObjectTag("forge:dusts/silver", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.PERDITIO, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:nuggets/lead", new AspectList().add(AspectInit.METAL, 1));
        registerObjectTag("forge:ingots/lead", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:dusts/lead", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.PERDITIO, 1).add(AspectInit.ORDER, 1));
        registerObjectTag(Items.COAL, new AspectList().add(AspectInit.POTENTIA, 10).add(AspectInit.IGNIS, 10));
        registerObjectTag("forge:ingots/uranium", new AspectList().add(AspectInit.METAL, 10).add(AspectInit.MORTUUS, 5).add(AspectInit.POTENTIA, 10));
        registerObjectTag("forge:gems/ruby", new AspectList().add(AspectInit.VITREUS, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:gems/green_sapphire", new AspectList().add(AspectInit.VITREUS, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:gems/sapphire", new AspectList().add(AspectInit.VITREUS, 10).add(AspectInit.DESIRE, 10));
        registerObjectTag("forge:ingots/steel", new AspectList().add(AspectInit.METAL, 15).add(AspectInit.ORDER, 5));
        registerObjectTag("forge:items/rubber", new AspectList().add(AspectInit.MOTUS, 5).add(AspectInit.INSTRUMENTUM, 5));
        registerObjectTag(Items.BEDROCK, new AspectList().add(AspectInit.VACUOS, 25).add(AspectInit.PERDITIO, 25).add(AspectInit.TERRA, 25).add(AspectInit.TENEBRAE, 25));
        registerObjectTag(Items.FARMLAND, new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.AQUA, 2).add(AspectInit.ORDER, 2));
        registerObjectTag("forge:grass", new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.HERBA, 2));
        registerObjectTag(Items.DIRT_PATH, new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.HERBA, 2).add(AspectInit.ORDER, 2));
        registerObjectTag("forge:endstone", new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.TENEBRAE, 5));
        registerObjectTag("forge:gravel", new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.PERDITIO, 2));
        registerObjectTag(Items.MYCELIUM, new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.HERBA, 1).add(AspectInit.TAINT, 1));
        registerObjectTag(Items.CLAY_BALL, new AspectList().add(AspectInit.AQUA, 5).add(AspectInit.TERRA, 5));
        registerObjectTag(Items.BRICK, new AspectList().add(AspectInit.IGNIS, 1));
        registerObjectTag(Items.BRICKS, new AspectList().add(AspectInit.IGNIS, 1).add(AspectInit.SENSUS, 1));
        registerObjectTag("forge:netherrack", new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.IGNIS, 2));
        registerObjectTag("forge:ingots/brick_nether", new AspectList().add(AspectInit.IGNIS, 1).add(AspectInit.ORDER, 1));

        registerObjectTag("forge:glass", new AspectList().add(AspectInit.VITREUS, 5));
        registerObjectTag(Items.MOSSY_COBBLESTONE, new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.HERBA, 3).add(AspectInit.PERDITIO, 1));
        registerObjectTag("forge:obsidian", new AspectList().add(AspectInit.TERRA, 5).add(AspectInit.IGNIS, 5).add(AspectInit.TENEBRAE, 5));
        registerObjectTag(Items.STONE_BRICKS, new AspectList().add(AspectInit.TERRA, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 2), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ENTROPY, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 3), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ORDER, 1));
        registerObjectTag(Blocks.SANDSTONE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.PERDITIO, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.CYAN_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.ORDER, 1).add(AspectInit.PERDITIO, 1).add(AspectInit.TERRA, 1));
        registerObjectTag("forge:terracotta", new AspectList().add(AspectInit.SENSUS, 1).add(AspectInit.IGNIS, 1).add(AspectInit.TERRA, 3));

        registerObjectTag(Items.BLACK_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.BLACK_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.BLUE_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.BLUE_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.WHITE_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.WHITE_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.RED_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.RED_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.YELLOW_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.YELLOW_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.GREEN_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.GREEN_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.LIME_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.LIME_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.ORANGE_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.ORANGE_CONCRETE,new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.BROWN_CONCRETE_POWDER, new AspectList().add(AspectInit.AQUA, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.BROWN_CONCRETE, new AspectList().add(AspectInit.ORDER, 1).add(AspectInit.TERRA, 1));

        // my variants
        registerObjectTag("forge:sand", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.PERDITIO, 1).add(AspectInit.IGNIS,1));
        registerObjectTag("forge:cobblestone", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.PERDITIO, 1));
        registerObjectTag("minecraft:planks", new AspectList().add(AspectInit.ARBOR, 1));
        registerObjectTag("minecraft:logs", new AspectList().add(AspectInit.ARBOR, 4));
        registerObjectTag("forge:gravel", new AspectList().add(AspectInit.TERRA, 2));
        registerObjectTag("minecraft:bedrock", new AspectList().add(AspectInit.PERDITIO, 10).add(AspectInit.TENEBRAE, 10).add(AspectInit.TERRA,10).add(AspectInit.VACUOS, 10));
        registerObjectTag("forge:glass", new AspectList().add(AspectInit.VITREUS, 1));
        registerObjectTag(Items.RAW_GOLD_BLOCK, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.METAL, 2).add(AspectInit.GREED, 1));
        registerObjectTag(Items.RAW_IRON_BLOCK, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.METAL, 3));
        registerObjectTag(Items.RAW_COPPER_BLOCK, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.METAL, 2).add(AspectInit.PERMUTATIO, 1));
        registerObjectTag("minecraft:crystal_sound_blocks", new AspectList().add(AspectInit.VITREUS, 2).add(AspectInit.PRAECANTATIO, 1));
        registerObjectTag("forge:storage_blocks/iron", new AspectList().add(AspectInit.METAL, 27));
        registerObjectTag("forge:storage_blocks/gold", new AspectList().add(AspectInit.METAL, 20).add(AspectInit.GREED, 13));
        registerObjectTag("forge:storage_blocks/diamond", new AspectList().add(AspectInit.VITREUS, 27).add(AspectInit.GREED, 27));
        registerObjectTag("forge:storage_blocks/copper", new AspectList().add(AspectInit.METAL, 27).add(AspectInit.PERMUTATIO, 13));
        registerObjectTag("forge:storage_blocks/lapis", new AspectList().add(AspectInit.SENSUS, 27));
        registerObjectTag("minecraft:wool", new AspectList().add(AspectInit.FABRICO, 1).add(AspectInit.PANNUS, 1));
        registerObjectTag(Items.PURPUR_BLOCK, new AspectList().add(AspectInit.ALIENIS, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.ICE, new AspectList().add(AspectInit.GELUM, 4));
        registerObjectTag(Items.BLUE_ICE, new AspectList().add(AspectInit.GELUM, 4));
        registerObjectTag(Items.PACKED_ICE, new AspectList().add(AspectInit.GELUM, 4));
        registerObjectTag(Items.SNOW, new AspectList().add(AspectInit.GELUM, 4));
        registerObjectTag(Items.PUMPKIN, new AspectList().add(AspectInit.MESSIS, 4));
        registerObjectTag(Items.JACK_O_LANTERN, new AspectList().add(AspectInit.MESSIS, 4).add(AspectInit.LUX, 3));
        registerObjectTag(Items.CLAY, new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.AQUA, 3));
        registerObjectTag("forge:bookshelves", new AspectList().add(AspectInit.ARBOR, 4).add(AspectInit.PANNUS, 2).add(AspectInit.COGNITIO, 6));
        registerObjectTag(Items.SOUL_SAND, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.TRAP, 2).add(AspectInit.SOUL, 1));
        registerObjectTag(Items.SOUL_SOIL, new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.TRAP, 1).add(AspectInit.SOUL, 2));
        registerObjectTag(Items.BASALT, new AspectList().add(AspectInit.TERRA, 4).add(AspectInit.IGNIS, 1).add(AspectInit.PERDITIO, 1));
        registerObjectTag(Items.PACKED_MUD, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.FABRICO, 1));
        registerObjectTag(Items.MUD_BRICKS, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.FABRICO, 2));
        registerObjectTag(Items.NETHER_BRICK, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.IGNIS, 1).add(AspectInit.INFERNUS, 1));
        registerObjectTag(Items.MYCELIUM, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.HERBA, 1).add(AspectInit.TAINT, 1));
        registerObjectTag(Items.END_STONE, new AspectList().add(AspectInit.TENEBRAE, 1).add(AspectInit.TERRA, 1));
        registerObjectTag(Items.MELON, new AspectList().add(AspectInit.MESSIS, 4).add(AspectInit.AQUA, 1).add(AspectInit.FAMES, 2));
        registerObjectTag(Items.REINFORCED_DEEPSLATE, new AspectList().add(AspectInit.TERRA, 10).add(AspectInit.TENEBRAE, 9).add(AspectInit.AURA, 9).add(AspectInit.ITER, 5));
        registerObjectTag(Items.PRISMARINE_BRICKS, new AspectList().add(AspectInit.AQUA, 3).add(AspectInit.PRAECANTATIO, 3).add(AspectInit.TERRA, 2).add(AspectInit.VITREUS, 9));
        registerObjectTag(Items.PRISMARINE, new AspectList().add(AspectInit.AQUA, 3).add(AspectInit.PRAECANTATIO, 3).add(AspectInit.VITREUS, 9));
        registerObjectTag(Items.DARK_PRISMARINE, new AspectList().add(AspectInit.AQUA, 3).add(AspectInit.PRAECANTATIO, 3).add(AspectInit.VITREUS, 9).add(AspectInit.TENEBRAE, 1).add(AspectInit.TERRA, 3));
        registerObjectTag(Items.SEA_LANTERN, new AspectList().add(AspectInit.AQUA, 3).add(AspectInit.PRAECANTATIO, 3).add(AspectInit.VITREUS, 9).add(AspectInit.LUX, 7).add(AspectInit.SENSUS, 2));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.IGNIS, 3).add(AspectInit.LUX, 1));
        registerObjectTag(Items.NETHER_WART_BLOCK, new AspectList().add(AspectInit.HERBA, 3).add(AspectInit.PRAECANTATIO, 2).add(AspectInit.INFERNUS, 3));
        registerObjectTag(Items.WARPED_WART_BLOCK, new AspectList().add(AspectInit.HERBA, 3).add(AspectInit.PRAECANTATIO, 2).add(AspectInit.INFERNUS, 1).add(AspectInit.ALIENIS, 1));
        registerObjectTag(Items.RED_NETHER_BRICKS, new AspectList().add(AspectInit.IGNIS, 3).add(AspectInit.TERRA, 3).add(AspectInit.ORDER, 1).add(AspectInit.INFERNUS, 1));
        registerObjectTag(Items.BONE_BLOCK, new AspectList().add(AspectInit.MORTUUS, 9).add(AspectInit.FLESH, 5));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.IGNIS, 3).add(AspectInit.LUX, 1));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.IGNIS, 3).add(AspectInit.LUX, 1));

        registerObjectTag("forge:blocks/glowstone", new AspectList().add(AspectInit.SENSUS, 3).add(AspectInit.LUX, 6));

        registerObjectTag("forge:storage_blocks/netherite", new AspectList().add(AspectInit.METAL, 36).add(AspectInit.POTENTIA, 13).add(AspectInit.GREED, 13).add(AspectInit.VITREUS, 27).add(AspectInit.ORDER,13));
        registerObjectTag("forge:storage_blocks/emerald", new AspectList().add(AspectInit.VITREUS, 36).add(AspectInit.GREED, 33));
        registerObjectTag(Items.EXPOSED_COPPER, new AspectList().add(AspectInit.METAL, 11).add(AspectInit.PERMUTATIO, 9).add(AspectInit.AQUA, 9));
        registerObjectTag(Items.WEATHERED_COPPER, new AspectList().add(AspectInit.METAL, 11).add(AspectInit.PERMUTATIO, 5).add(AspectInit.AQUA, 7).add(AspectInit.PERDITIO, 11));
        registerObjectTag(Items.OXIDIZED_COPPER, new AspectList().add(AspectInit.METAL, 9).add(AspectInit.PERMUTATIO, 9).add(AspectInit.AQUA, 9).add(AspectInit.PERDITIO, 9));
        registerObjectTag(Items.WAXED_COPPER_BLOCK, new AspectList().add(AspectInit.METAL, 27).add(AspectInit.PERMUTATIO, 13).add(AspectInit.LIMUS, 9).add(AspectInit.SENSUS, 2));
        registerObjectTag(Items.SPONGE, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.VACUOS, 2).add(AspectInit.TRAP, 2));
        registerObjectTag(Items.WET_SPONGE, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.AQUA, 2).add(AspectInit.TRAP, 2));
        registerObjectTag(Items.TINTED_GLASS, new AspectList().add(AspectInit.TENEBRAE, 2).add(AspectInit.VITREUS, 1).add(AspectInit.PRAECANTATIO, 1));
        registerObjectTag(Items.HAY_BLOCK, new AspectList().add(AspectInit.FAMES, 6).add(AspectInit.MESSIS, 9));
        // TODO
        // CORALS
        // blackstone
        // crying obsidian
        // kelp block

        // garden
        registerObjectTag(Items.MANGROVE_ROOTS, new AspectList().add(AspectInit.ARBOR, 2).add(AspectInit.AIR, 2).add(AspectInit.HERBA, 2));
        registerObjectTag(Items.MUDDY_MANGROVE_ROOTS, new AspectList().add(AspectInit.ARBOR, 2).add(AspectInit.TERRA, 2).add(AspectInit.HERBA, 2));

        registerObjectTag("minecraft:saplings", new AspectList().add(AspectInit.ARBOR, 1).add(AspectInit.HERBA, 2));
        // dirt
        registerObjectTag("forge:dirt", new AspectList().add(AspectInit.TERRA, 2));
        registerObjectTag(Items.COARSE_DIRT, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.PERDITIO, 1));
        registerObjectTag(Items.ROOTED_DIRT, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.ARBOR, 1));

        registerObjectTag(Items.DIRT, new AspectList().add(AspectInit.TERRA, 1));
        registerObjectTag(Items.MUD, new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.AQUA,2));
        registerObjectTag("minecraft:nylium", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.IGNIS,1).add(AspectInit.HERBA, 1));
        registerObjectTag("minecraft:dirt", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.HERBA, 1));

        // stones
        registerObjectTag("forge:stones/granite", new AspectList().add(AspectInit.TERRA, 4));
        registerObjectTag("forge:stones/diorite", new AspectList().add(AspectInit.TERRA, 4));
        registerObjectTag("forge:stones/andesite", new AspectList().add(AspectInit.TERRA, 4));
        registerObjectTag("forge:stone", new AspectList().add(AspectInit.TERRA, 4));
        registerObjectTag("forge:sandstone", new AspectList().add(AspectInit.TERRA, 3).add(AspectInit.PERDITIO, 3));
        registerObjectTag(Items.DRIPSTONE_BLOCK, new AspectList().add(AspectInit.TERRA, 2).add(AspectInit.AQUA, 2));
        // ores
        registerObjectTag("forge:ores/gold", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.METAL, 2).add(AspectInit.GREED, 1));
        registerObjectTag("forge:ores/iron", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.METAL, 3));
        registerObjectTag("forge:ores/coal", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.POTENTIA, 2).add(AspectInit.IGNIS, 1));
        registerObjectTag("forge:ores/copper", new AspectList().add(AspectInit.METAL, 2).add(AspectInit.TERRA, 1).add(AspectInit.PERMUTATIO, 1));
        registerObjectTag("forge:ores/lead", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.PERDITIO, 1).add(AspectInit.ORDER, 1));
        registerObjectTag("forge:ores/diamond", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.GREED, 3).add(AspectInit.VITREUS, 3));
        registerObjectTag("forge:ores/lapis", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.SENSUS, 3));
        registerObjectTag("forge:ores/redstone", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.POTENTIA, 2).add(AspectInit.MECHANISM, 2));
        registerObjectTag("forge:ores/emerald", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.GREED, 4).add(AspectInit.VITREUS, 3));
        registerObjectTag("forge:ores/quartz", new AspectList().add(AspectInit.TERRA, 1).add(AspectInit.POTENTIA, 2).add(AspectInit.IGNIS, 1));
        registerObjectTag("forge:ores/silver", new AspectList().add(AspectInit.METAL, 3).add(AspectInit.PERDITIO, 1).add(AspectInit.GREED, 1));
        registerObjectTag("forge:ores/netherite_scrap", new AspectList().add(AspectInit.METAL, 5).add(AspectInit.POTENTIA, 2).add(AspectInit.GREED, 2).add(AspectInit.VITREUS, 4).add(AspectInit.ORDER,2));

        // items test
        registerObjectTag(Items.GHAST_TEAR, new AspectList().add(AspectInit.EXANIMIS, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
        registerObjectTag("forge:ingots/iron", new AspectList().add(AspectInit.METAL, 5).add(AspectInit.SOUL, 10).add(AspectInit.ALCHEMY, 10));
        registerObjectTag(Items.CAKE, new AspectList().add(AspectInit.DESIRE, 1).add(AspectInit.LIFE, 2));
        registerObjectTag(Blocks.DARK_OAK_FENCE_GATE, new AspectList().add(AspectInit.TRAP, 5).add(AspectInit.MECHANISM, 5));
        registerObjectTag("minecraft:water", new AspectList().add(AspectInit.AQUA, 5));
    }


}
