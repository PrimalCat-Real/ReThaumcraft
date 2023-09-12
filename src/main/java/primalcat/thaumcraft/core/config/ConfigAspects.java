package primalcat.thaumcraft.core.config;

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
import primalcat.thaumcraft.core.aspects.AspectList;
import primalcat.thaumcraft.core.registry.AspectRegistry;

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
        AspectRegistry.putItemAspects(item, aspectList);
    }
    public static void registerObjectTag(Item item, AspectList aspectList){
        AspectRegistry.putItemAspects(item.toString(), aspectList);
    }
    public static void registerObjectTag(Block block, AspectList aspectList){
        AspectRegistry.putItemAspects(block.toString(), aspectList);
    }
    public static void registerObjectTag(ItemStack item, AspectList aspectList){
        AspectRegistry.putItemAspects(item.toString(), aspectList);
    }
    public static void registerEntityTag(Entity entity, AspectList aspectList) {
        AspectRegistry.putEntityAspects(entity.toString(), aspectList);
    }
    public static void registerEntityTag(String entity, AspectList aspectList) {
        AspectRegistry.putEntityAspects(entity, aspectList);
    }

    private static String createDefaultConfigData(){
        Map<String, LinkedHashMap<String, AspectList>> categories = new HashMap<>();
        categories.put("items", AspectRegistry.getItemAspects());
        categories.put("entities", AspectRegistry.getEntityAspects());

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
                    AspectRegistry.setEntityAspects((LinkedHashMap<String, AspectList>) configFileContent.get("entities"));
                    AspectRegistry.setItemAspects((LinkedHashMap<String, AspectList>) configFileContent.get("items"));
                    Thaumcraft.LOGGER.warn("Config aspects successful");
                }else {
                    Thaumcraft.LOGGER.warn("Config file is not valid");
                }
            }
        }
    }


    private static void initDefaultAspects(){
        registerEntityTag("Zombie", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TERRA, 1));
        registerEntityTag("Husk", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.IGNIS, 1));
        registerEntityTag("Giant", new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.HUMANUS, 5).add(AspectRegistry.TERRA, 2));
        registerEntityTag("Skeleton", new AspectList().add(AspectRegistry.EXANIMIS, 3).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TERRA, 1));
        registerEntityTag("WitherSkeleton", new AspectList().add(AspectRegistry.EXANIMIS, 4).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.INFERNUS, 2));
        registerEntityTag("Creeper", new AspectList().add(AspectRegistry.HERBA, 2).add(AspectRegistry.IGNIS, 2).add(AspectRegistry.TELUM, 2));
//        registerEntityTag("Creeper", new AspectList().add(AspectInit.PLANT, 15).add(AspectInit.FIRE, 15).add(AspectInit.ENERGY, 15), new ThaumcraftApi.EntityTagsNBT("powered", 1));
        registerEntityTag("Horse", new AspectList().add(AspectRegistry.BEAST, 4).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("Donkey", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("Mule", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("SkeletonHorse", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("ZombieHorse", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("Pig", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.TERRA, 2).add(AspectRegistry.GULA, 3));
        registerEntityTag("XPOrb", new AspectList().add(AspectRegistry.COGNITIO, 2));
        registerEntityTag("Sheep", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.TERRA, 2));
        registerEntityTag("Cow", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 3));
        registerEntityTag("MushroomCow", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.HERBA, 3).add(AspectRegistry.TERRA, 3));
        registerEntityTag("SnowMan", new AspectList().add(AspectRegistry.GELUM, 4).add(AspectRegistry.AQUA, 1).add(AspectRegistry.PRAECANTATIO, 1));
        registerEntityTag("Ozelot", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.PERDITIO, 2).add(AspectRegistry.MOTUS, 1));
        registerEntityTag("Chicken", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.AIR, 1));
        registerEntityTag("Squid", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.AQUA, 2));
        registerEntityTag("Wolf", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 2).add(AspectRegistry.AVERSION, 1));
        registerEntityTag("Bat", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.TENEBRAE, 1));
        registerEntityTag("Spider", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.PERDITIO, 2).add(AspectRegistry.TRAP, 2));
        registerEntityTag("Slime", new AspectList().add(AspectRegistry.LIFE, 2).add(AspectRegistry.LIMUS, 2).add(AspectRegistry.ALCHEMY, 1));
        registerEntityTag("Ghast", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.IGNIS, 2).add(AspectRegistry.INFERNUS, 3).add(AspectRegistry.IRA,2));
        registerEntityTag("PigZombie", new AspectList().add(AspectRegistry.EXANIMIS, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.IRA, 5));
        registerEntityTag("Enderman", new AspectList().add(AspectRegistry.ALIENIS, 4).add(AspectRegistry.ITER, 2).add(AspectRegistry.AIR, 1).add(AspectRegistry.INVIDIA, 2).add(AspectRegistry.SUPERBIA, 3));
        registerEntityTag("CaveSpider", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VENENUM, 2).add(AspectRegistry.TRAP, 2));
        registerEntityTag("Silverfish", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO,1));
        registerEntityTag("Blaze", new AspectList().add(AspectRegistry.ALIENIS, 2).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.INFERNUS, 2));
        registerEntityTag("LavaSlime", new AspectList().add(AspectRegistry.LIMUS, 3).add(AspectRegistry.IGNIS, 4).add(AspectRegistry.ALCHEMY, 1).add(AspectRegistry.INFERNUS, 2));
        registerEntityTag("EnderDragon", new AspectList().add(AspectRegistry.ALIENIS, 20).add(AspectRegistry.BEAST, 20).add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.VOLATUS, 2).add(AspectRegistry.SUPERBIA, 10));
        registerEntityTag("WitherBoss", new AspectList().add(AspectRegistry.EXANIMIS, 10).add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.IGNIS, 15).add(AspectRegistry.IRA, 7).add(AspectRegistry.INFERNUS, 7));
        registerEntityTag("Witch", new AspectList().add(AspectRegistry.HUMANUS, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.ALCHEMY, 3));
        registerEntityTag("Villager", new AspectList().add(AspectRegistry.HUMANUS, 3).add(AspectRegistry.GREED, 2));
        registerEntityTag("VillagerGolem", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.TERRA, 5).add(AspectRegistry.MECHANISM, 3).add(AspectRegistry.PRAECANTATIO, 2));
        registerEntityTag("EnderCrystal", new AspectList().add(AspectRegistry.ALIENIS, 3).add(AspectRegistry.AURA, 3).add(AspectRegistry.LIFE, 3).add(AspectRegistry.SUPERBIA, 2).add(AspectRegistry.VITREUS, 4));
        registerEntityTag("ItemFrame", new AspectList().add(AspectRegistry.SENSUS, 2).add(AspectRegistry.FABRICO, 2));
        registerEntityTag("Painting", new AspectList().add(AspectRegistry.SENSUS, 4).add(AspectRegistry.FABRICO, 2));
        registerEntityTag("Guardian", new AspectList().add(AspectRegistry.BEAST, 4).add(AspectRegistry.ALIENIS, 5).add(AspectRegistry.AQUA, 5).add(AspectRegistry.TELUM, 5));
//        registerEntityTag("Guardian", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ELDRITCH, 15).add(AspectInit.WATER, 15), new ThaumcraftApi.EntityTagsNBT("Elder", true));
        registerEntityTag("Rabbit", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.TERRA, 1).add(AspectRegistry.MOTUS, 1));
        registerEntityTag("Endermite", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.MOTUS, 1).add(AspectRegistry.IRA,1));
        registerEntityTag("PolarBear", new AspectList().add(AspectRegistry.BEAST, 5).add(AspectRegistry.GELUM, 2));
        registerEntityTag("Shulker", new AspectList().add(AspectRegistry.ALIENIS, 4).add(AspectRegistry.TRAP, 2).add(AspectRegistry.VOLATUS, 2).add(AspectRegistry.TUTAMEN, 4));
        registerEntityTag("EvocationIllager", new AspectList().add(AspectRegistry.ALIENIS, 3).add(AspectRegistry.PRAECANTATIO, 4).add(AspectRegistry.HUMANUS, 4));
        registerEntityTag("VindicationIllager", new AspectList().add(AspectRegistry.AVERSION, 3).add(AspectRegistry.PRAECANTATIO, 1).add(AspectRegistry.HUMANUS, 4));
        registerEntityTag("IllusionIllager", new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.PRAECANTATIO, 4).add(AspectRegistry.HUMANUS, 4));
        registerEntityTag("Llama", new AspectList().add(AspectRegistry.BEAST, 5).add(AspectRegistry.AQUA, 2));
        registerEntityTag("Parrot", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.SENSUS, 1));
        registerEntityTag("Stray", new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TRAP, 1));
        registerEntityTag("Vex", new AspectList().add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.HUMANUS, 1));

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

        registerObjectTag("forge:gem/emerald", new AspectList().add(AspectRegistry.VITREUS, 4).add(AspectRegistry.GREED, 5));
        registerObjectTag("forge:nuggets/iron", new AspectList().add(AspectRegistry.METAL, 1));
        registerObjectTag("forge:gem/diamond", new AspectList().add(AspectRegistry.VITREUS, 4).add(AspectRegistry.GREED, 4));

        registerObjectTag("forge:dusts/iron", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1));

        registerObjectTag("forge:dusts/gold", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        registerObjectTag("forge:dusts/redstone", new AspectList().add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.MECHANISM, 1));
        registerObjectTag("forge:dusts/glowstone", new AspectList().add(AspectRegistry.SENSUS, 1).add(AspectRegistry.LUX, 2));
        registerObjectTag(Items.GLOWSTONE, new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.LUX, 10));
        registerObjectTag("forge:nuggets/copper", new AspectList().add(AspectRegistry.METAL, 1));
        registerObjectTag("forge:ingots/copper", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERMUTATIO, 1));
        registerObjectTag("forge:dusts/copper", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.PERMUTATIO, 1));
        registerObjectTag("forge:nuggets/tin", new AspectList().add(AspectRegistry.METAL, 1));
        registerObjectTag("forge:ingots/tin", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.VITREUS, 1));
        registerObjectTag("forge:dusts/tin", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.VITREUS, 1));
        registerObjectTag("forge:ores/tin", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.VITREUS, 1));
        registerObjectTag("forge:nuggets/silver", new AspectList().add(AspectRegistry.METAL, 1));
        registerObjectTag("forge:ingots/silver", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.GREED, 1));
        registerObjectTag("forge:dusts/silver", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        registerObjectTag("forge:nuggets/lead", new AspectList().add(AspectRegistry.METAL, 1));
        registerObjectTag("forge:ingots/lead", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.ORDER, 1));
        registerObjectTag("forge:dusts/lead", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.ORDER, 1));
        registerObjectTag(Items.COAL, new AspectList().add(AspectRegistry.POTENTIA, 10).add(AspectRegistry.IGNIS, 10));
        registerObjectTag("forge:ingots/uranium", new AspectList().add(AspectRegistry.METAL, 10).add(AspectRegistry.MORTUUS, 5).add(AspectRegistry.POTENTIA, 10));
        registerObjectTag("forge:gems/ruby", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        registerObjectTag("forge:gems/green_sapphire", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        registerObjectTag("forge:gems/sapphire", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        registerObjectTag("forge:ingots/steel", new AspectList().add(AspectRegistry.METAL, 15).add(AspectRegistry.ORDER, 5));
        registerObjectTag("forge:items/rubber", new AspectList().add(AspectRegistry.MOTUS, 5).add(AspectRegistry.INSTRUMENTUM, 5));
        registerObjectTag(Items.BEDROCK, new AspectList().add(AspectRegistry.VACUOS, 25).add(AspectRegistry.PERDITIO, 25).add(AspectRegistry.TERRA, 25).add(AspectRegistry.TENEBRAE, 25));
        registerObjectTag(Items.FARMLAND, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.AQUA, 2).add(AspectRegistry.ORDER, 2));
        registerObjectTag("forge:grass", new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.HERBA, 2));
        registerObjectTag(Items.DIRT_PATH, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 2).add(AspectRegistry.ORDER, 2));
        registerObjectTag("forge:endstone", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.TENEBRAE, 5));
        registerObjectTag("forge:gravel", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.PERDITIO, 2));
        registerObjectTag(Items.MYCELIUM, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 1).add(AspectRegistry.TAINT, 1));
        registerObjectTag(Items.CLAY_BALL, new AspectList().add(AspectRegistry.AQUA, 5).add(AspectRegistry.TERRA, 5));
        registerObjectTag(Items.BRICK, new AspectList().add(AspectRegistry.IGNIS, 1));
        registerObjectTag(Items.BRICKS, new AspectList().add(AspectRegistry.IGNIS, 1).add(AspectRegistry.SENSUS, 1));
        registerObjectTag("forge:netherrack", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.IGNIS, 2));
        registerObjectTag("forge:ingots/brick_nether", new AspectList().add(AspectRegistry.IGNIS, 1).add(AspectRegistry.ORDER, 1));

        registerObjectTag("forge:glass", new AspectList().add(AspectRegistry.VITREUS, 5));
        registerObjectTag(Items.MOSSY_COBBLESTONE, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 3).add(AspectRegistry.PERDITIO, 1));
        registerObjectTag("forge:obsidian", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.IGNIS, 5).add(AspectRegistry.TENEBRAE, 5));
        registerObjectTag(Items.STONE_BRICKS, new AspectList().add(AspectRegistry.TERRA, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 2), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ENTROPY, 1));
//        registerObjectTag(new ItemStack(Blocks.STONEBRICK, 1, 3), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ORDER, 1));
        registerObjectTag(Blocks.SANDSTONE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.CYAN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.ORDER, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag("forge:terracotta", new AspectList().add(AspectRegistry.SENSUS, 1).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.TERRA, 3));

        registerObjectTag(Items.BLACK_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.BLACK_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.BLUE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.BLUE_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.WHITE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.WHITE_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.RED_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.RED_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.YELLOW_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.YELLOW_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.GREEN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.GREEN_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.LIME_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.LIME_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.ORANGE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.ORANGE_CONCRETE,new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.BROWN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.BROWN_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));

        // my variants
        registerObjectTag("forge:sand", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.IGNIS,1));
        registerObjectTag("forge:cobblestone", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO, 1));
        registerObjectTag("minecraft:planks", new AspectList().add(AspectRegistry.ARBOR, 1));
        registerObjectTag("minecraft:logs", new AspectList().add(AspectRegistry.ARBOR, 4));
        registerObjectTag("forge:gravel", new AspectList().add(AspectRegistry.TERRA, 2));
        registerObjectTag("minecraft:bedrock", new AspectList().add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.TENEBRAE, 10).add(AspectRegistry.TERRA,10).add(AspectRegistry.VACUOS, 10));
        registerObjectTag("forge:glass", new AspectList().add(AspectRegistry.VITREUS, 1));
        registerObjectTag(Items.RAW_GOLD_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.GREED, 1));
        registerObjectTag(Items.RAW_IRON_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 3));
        registerObjectTag(Items.RAW_COPPER_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.PERMUTATIO, 1));
        registerObjectTag("minecraft:crystal_sound_blocks", new AspectList().add(AspectRegistry.VITREUS, 2).add(AspectRegistry.PRAECANTATIO, 1));
        registerObjectTag("forge:storage_blocks/iron", new AspectList().add(AspectRegistry.METAL, 27));
        registerObjectTag("forge:storage_blocks/gold", new AspectList().add(AspectRegistry.METAL, 20).add(AspectRegistry.GREED, 13));
        registerObjectTag("forge:storage_blocks/diamond", new AspectList().add(AspectRegistry.VITREUS, 27).add(AspectRegistry.GREED, 27));
        registerObjectTag("forge:storage_blocks/copper", new AspectList().add(AspectRegistry.METAL, 27).add(AspectRegistry.PERMUTATIO, 13));
        registerObjectTag("forge:storage_blocks/lapis", new AspectList().add(AspectRegistry.SENSUS, 27));
        registerObjectTag("minecraft:wool", new AspectList().add(AspectRegistry.FABRICO, 1).add(AspectRegistry.PANNUS, 1));
        registerObjectTag(Items.PURPUR_BLOCK, new AspectList().add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        registerObjectTag(Items.BLUE_ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        registerObjectTag(Items.PACKED_ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        registerObjectTag(Items.SNOW, new AspectList().add(AspectRegistry.GELUM, 4));
        registerObjectTag(Items.PUMPKIN, new AspectList().add(AspectRegistry.MESSIS, 4));
        registerObjectTag(Items.JACK_O_LANTERN, new AspectList().add(AspectRegistry.MESSIS, 4).add(AspectRegistry.LUX, 3));
        registerObjectTag(Items.CLAY, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.AQUA, 3));
        registerObjectTag("forge:bookshelves", new AspectList().add(AspectRegistry.ARBOR, 4).add(AspectRegistry.PANNUS, 2).add(AspectRegistry.COGNITIO, 6));
        registerObjectTag(Items.SOUL_SAND, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.TRAP, 2).add(AspectRegistry.SOUL, 1));
        registerObjectTag(Items.SOUL_SOIL, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.TRAP, 1).add(AspectRegistry.SOUL, 2));
        registerObjectTag(Items.BASALT, new AspectList().add(AspectRegistry.TERRA, 4).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.PERDITIO, 1));
        registerObjectTag(Items.PACKED_MUD, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.FABRICO, 1));
        registerObjectTag(Items.MUD_BRICKS, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.FABRICO, 2));
        registerObjectTag(Items.NETHER_BRICK, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.INFERNUS, 1));
        registerObjectTag(Items.MYCELIUM, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.HERBA, 1).add(AspectRegistry.TAINT, 1));
        registerObjectTag(Items.END_STONE, new AspectList().add(AspectRegistry.TENEBRAE, 1).add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.MELON, new AspectList().add(AspectRegistry.MESSIS, 4).add(AspectRegistry.AQUA, 1).add(AspectRegistry.FAMES, 2));
        registerObjectTag(Items.REINFORCED_DEEPSLATE, new AspectList().add(AspectRegistry.TERRA, 10).add(AspectRegistry.TENEBRAE, 9).add(AspectRegistry.AURA, 9).add(AspectRegistry.ITER, 5));
        registerObjectTag(Items.PRISMARINE_BRICKS, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.TERRA, 2).add(AspectRegistry.VITREUS, 9));
        registerObjectTag(Items.PRISMARINE, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9));
        registerObjectTag(Items.DARK_PRISMARINE, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9).add(AspectRegistry.TENEBRAE, 1).add(AspectRegistry.TERRA, 3));
        registerObjectTag(Items.SEA_LANTERN, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9).add(AspectRegistry.LUX, 7).add(AspectRegistry.SENSUS, 2));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));
        registerObjectTag(Items.NETHER_WART_BLOCK, new AspectList().add(AspectRegistry.HERBA, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.INFERNUS, 3));
        registerObjectTag(Items.WARPED_WART_BLOCK, new AspectList().add(AspectRegistry.HERBA, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.INFERNUS, 1).add(AspectRegistry.ALIENIS, 1));
        registerObjectTag(Items.RED_NETHER_BRICKS, new AspectList().add(AspectRegistry.IGNIS, 3).add(AspectRegistry.TERRA, 3).add(AspectRegistry.ORDER, 1).add(AspectRegistry.INFERNUS, 1));
        registerObjectTag(Items.BONE_BLOCK, new AspectList().add(AspectRegistry.MORTUUS, 9).add(AspectRegistry.FLESH, 5));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));
        registerObjectTag(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));

        registerObjectTag("forge:blocks/glowstone", new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.LUX, 6));

        registerObjectTag("forge:storage_blocks/netherite", new AspectList().add(AspectRegistry.METAL, 36).add(AspectRegistry.POTENTIA, 13).add(AspectRegistry.GREED, 13).add(AspectRegistry.VITREUS, 27).add(AspectRegistry.ORDER,13));
        registerObjectTag("forge:storage_blocks/emerald", new AspectList().add(AspectRegistry.VITREUS, 36).add(AspectRegistry.GREED, 33));
        registerObjectTag(Items.EXPOSED_COPPER, new AspectList().add(AspectRegistry.METAL, 11).add(AspectRegistry.PERMUTATIO, 9).add(AspectRegistry.AQUA, 9));
        registerObjectTag(Items.WEATHERED_COPPER, new AspectList().add(AspectRegistry.METAL, 11).add(AspectRegistry.PERMUTATIO, 5).add(AspectRegistry.AQUA, 7).add(AspectRegistry.PERDITIO, 11));
        registerObjectTag(Items.OXIDIZED_COPPER, new AspectList().add(AspectRegistry.METAL, 9).add(AspectRegistry.PERMUTATIO, 9).add(AspectRegistry.AQUA, 9).add(AspectRegistry.PERDITIO, 9));
        registerObjectTag(Items.WAXED_COPPER_BLOCK, new AspectList().add(AspectRegistry.METAL, 27).add(AspectRegistry.PERMUTATIO, 13).add(AspectRegistry.LIMUS, 9).add(AspectRegistry.SENSUS, 2));
        registerObjectTag(Items.SPONGE, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.VACUOS, 2).add(AspectRegistry.TRAP, 2));
        registerObjectTag(Items.WET_SPONGE, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.AQUA, 2).add(AspectRegistry.TRAP, 2));
        registerObjectTag(Items.TINTED_GLASS, new AspectList().add(AspectRegistry.TENEBRAE, 2).add(AspectRegistry.VITREUS, 1).add(AspectRegistry.PRAECANTATIO, 1));
        registerObjectTag(Items.HAY_BLOCK, new AspectList().add(AspectRegistry.FAMES, 6).add(AspectRegistry.MESSIS, 9));
        // TODO
        // CORALS
        // blackstone
        // crying obsidian
        // kelp block

        // garden
        registerObjectTag(Items.MANGROVE_ROOTS, new AspectList().add(AspectRegistry.ARBOR, 2).add(AspectRegistry.AIR, 2).add(AspectRegistry.HERBA, 2));
        registerObjectTag(Items.MUDDY_MANGROVE_ROOTS, new AspectList().add(AspectRegistry.ARBOR, 2).add(AspectRegistry.TERRA, 2).add(AspectRegistry.HERBA, 2));

        registerObjectTag("minecraft:saplings", new AspectList().add(AspectRegistry.ARBOR, 1).add(AspectRegistry.HERBA, 2));
        // dirt
        registerObjectTag("forge:dirt", new AspectList().add(AspectRegistry.TERRA, 2));
        registerObjectTag(Items.COARSE_DIRT, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.PERDITIO, 1));
        registerObjectTag(Items.ROOTED_DIRT, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.ARBOR, 1));

        registerObjectTag(Items.DIRT, new AspectList().add(AspectRegistry.TERRA, 1));
        registerObjectTag(Items.MUD, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.AQUA,2));
        registerObjectTag("minecraft:nylium", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.IGNIS,1).add(AspectRegistry.HERBA, 1));
        registerObjectTag("minecraft:dirt", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.HERBA, 1));

        // stones
        registerObjectTag("forge:stones/granite", new AspectList().add(AspectRegistry.TERRA, 4));
        registerObjectTag("forge:stones/diorite", new AspectList().add(AspectRegistry.TERRA, 4));
        registerObjectTag("forge:stones/andesite", new AspectList().add(AspectRegistry.TERRA, 4));
        registerObjectTag("forge:stone", new AspectList().add(AspectRegistry.TERRA, 4));
        registerObjectTag("forge:sandstone", new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.PERDITIO, 3));
        registerObjectTag(Items.DRIPSTONE_BLOCK, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.AQUA, 2));
        // ores
        registerObjectTag("forge:ores/gold", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.GREED, 1));
        registerObjectTag("forge:ores/iron", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 3));
        registerObjectTag("forge:ores/coal", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.IGNIS, 1));
        registerObjectTag("forge:ores/copper", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERMUTATIO, 1));
        registerObjectTag("forge:ores/lead", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.ORDER, 1));
        registerObjectTag("forge:ores/diamond", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.GREED, 3).add(AspectRegistry.VITREUS, 3));
        registerObjectTag("forge:ores/lapis", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.SENSUS, 3));
        registerObjectTag("forge:ores/redstone", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.MECHANISM, 2));
        registerObjectTag("forge:ores/emerald", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.GREED, 4).add(AspectRegistry.VITREUS, 3));
        registerObjectTag("forge:ores/quartz", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.IGNIS, 1));
        registerObjectTag("forge:ores/silver", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        registerObjectTag("forge:ores/netherite_scrap", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.GREED, 2).add(AspectRegistry.VITREUS, 4).add(AspectRegistry.ORDER,2));

        // items test
        registerObjectTag(Items.GHAST_TEAR, new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.SOUL, 10).add(AspectRegistry.ALCHEMY, 10));
        registerObjectTag("forge:ingots/iron", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.SOUL, 10).add(AspectRegistry.ALCHEMY, 10));
        registerObjectTag(Items.CAKE, new AspectList().add(AspectRegistry.DESIRE, 1).add(AspectRegistry.LIFE, 2));
        registerObjectTag(Blocks.DARK_OAK_FENCE_GATE, new AspectList().add(AspectRegistry.TRAP, 5).add(AspectRegistry.MECHANISM, 5));
        registerObjectTag("minecraft:water", new AspectList().add(AspectRegistry.AQUA, 5));
    }


}
