package primalcat.thaumcraft.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import primalcat.thaumcraft.api.Aspect;
import primalcat.thaumcraft.api.AspectList;

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

    private static Map<Object, AspectList> itemAspects = new HashMap<>();
    private static Map<String, AspectList> entityAspects = new HashMap<>();
    private static Map<Tag, AspectList> tagAspects = new HashMap<>();

    private static Map<String, Map<String, AspectList>> readConfig;

    public static Map<String, Map<String, AspectList>> getReadConfig(){
        try{
            return readConfigFile();
        }catch (IOException e){
            System.out.println("error for read");
        }
        return null;
    }

//        if(readConfig != null){
//            return readConfig;
//        }else{
////            main();
//            readConfig = new HashMap<>();
//            Map<String, AspectList> test = new HashMap<>();
//            readConfig.put("test", (Map<String, AspectList>) test.put("diamond", new AspectList().add(Aspect.AIR, 1)));
//            try{
//                System.out.println("config file");
//                System.out.println(readConfigFile());
//            }catch (IOException e){
//                System.out.println("error for read");
//            }
//
//            return null;
//        }



    public static void createConfigFile() {
        try {
            File location = new File (Minecraft.getInstance().gameDirectory + "/" + CONFIG_FOLDER);
            if (!location.exists()) {
                location.mkdirs();
            }
            initAspects();
        } catch (IOException e) {
            System.err.println("Failed to create config file: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, AspectList>> readConfigFile() throws IOException {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(CONFIG_FILE_PATH);
            Type mapType = new TypeToken<Map<String, Map<String, AspectList>>>() {}.getType();
            Map<String, Map<String, AspectList>> data = gson.fromJson(reader, mapType);
            return data;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void initAspects() throws IOException {
        Map<String, Map> categories = new HashMap<>();


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AspectList.class, new AspectList())
                .setPrettyPrinting() // Enable pretty printing
                .create();

//        itemAspects.put(Items.DIAMOND, new AspectList().add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 10));
        categories.put("items", itemAspects);
        categories.put("entities", entityAspects);
        String json = gson.toJson(categories);

        String comment = "// Aspect List\n";
        String content = comment + json;

        FileWriter writer = null;
        try {
//            File location = new File(Minecraft.getInstance().getResourcePackDirectory() + "/<foldername>/");
            writer = new FileWriter(CONFIG_FILE_PATH);
            writer.write(content);
            System.out.println("Config file created: " + CONFIG_FILE_PATH);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }


    }

    public static void registerObjectTag(Item item, AspectList aspectList){
        itemAspects.put(item, aspectList);
    }
    public static void registerObjectTag(String item, AspectList aspectList){
        itemAspects.put(item, aspectList);
    }
    public static void registerEntityTag(String entity, AspectList aspectList) {
        entityAspects.put(entity, aspectList);

//        aspects.put("oreLapis", new AspectList().add(Aspect.EARTH, 5).add(Aspect.SENSES, 15));
//        aspects.put("oreRedstone", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENERGY, 15));
//        aspects.put("oreQuartz", new AspectList().add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 10));
    }

    public static void main() throws IOException {
        System.out.println("Init Aspects");
        // entity
        registerEntityTag("Zombie", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 10).add(Aspect.EARTH, 5));
        registerEntityTag("Husk", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 10).add(Aspect.FIRE, 5));
        registerEntityTag("Giant", new AspectList().add(Aspect.UNDEAD, 25).add(Aspect.MAN, 15).add(Aspect.EARTH, 10));
        registerEntityTag("Skeleton", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 5).add(Aspect.EARTH, 5));
        registerEntityTag("WitherSkeleton", new AspectList().add(Aspect.UNDEAD, 25).add(Aspect.MAN, 5).add(Aspect.ENTROPY, 10));
        registerEntityTag("Creeper", new AspectList().add(Aspect.PLANT, 15).add(Aspect.FIRE, 15));
        registerEntityTag("Creeper", new AspectList().add(Aspect.PLANT, 15).add(Aspect.FIRE, 15).add(Aspect.ENERGY, 15));
        registerEntityTag("Horse", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        registerEntityTag("Donkey", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        registerEntityTag("Mule", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        registerEntityTag("SkeletonHorse", new AspectList().add(Aspect.BEAST, 5).add(Aspect.UNDEAD, 10).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        registerEntityTag("ZombieHorse", new AspectList().add(Aspect.BEAST, 10).add(Aspect.UNDEAD, 5).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        registerEntityTag("Pig", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.DESIRE, 5));
        registerEntityTag("XPOrb", new AspectList().add(Aspect.MIND, 10));
        registerEntityTag("Sheep", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10));
        registerEntityTag("Cow", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15));
        registerEntityTag("MushroomCow", new AspectList().add(Aspect.BEAST, 15).add(Aspect.PLANT, 15).add(Aspect.EARTH, 15));
        registerEntityTag("SnowMan", new AspectList().add(Aspect.COLD, 10).add(Aspect.MAN, 5).add(Aspect.MECHANISM, 5).add(Aspect.MAGIC, 5));
        registerEntityTag("Ozelot", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ENTROPY, 10));
        registerEntityTag("Chicken", new AspectList().add(Aspect.BEAST, 5).add(Aspect.FLIGHT, 5).add(Aspect.AIR, 5));
        registerEntityTag("Squid", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 10));
        registerEntityTag("Wolf", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 10).add(Aspect.AVERSION, 5));
        registerEntityTag("Bat", new AspectList().add(Aspect.BEAST, 5).add(Aspect.FLIGHT, 5).add(Aspect.DARKNESS, 5));
        registerEntityTag("Spider", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ENTROPY, 10).add(Aspect.TRAP, 10));
        registerEntityTag("Slime", new AspectList().add(Aspect.LIFE, 10).add(Aspect.WATER, 10).add(Aspect.ALCHEMY, 5));
        registerEntityTag("Ghast", new AspectList().add(Aspect.UNDEAD, 15).add(Aspect.FIRE, 15));
        registerEntityTag("PigZombie", new AspectList().add(Aspect.UNDEAD, 15).add(Aspect.FIRE, 15).add(Aspect.BEAST, 10));
        registerEntityTag("Enderman", new AspectList().add(Aspect.ELDRITCH, 10).add(Aspect.MOTION, 15).add(Aspect.DESIRE, 5));
        registerEntityTag("CaveSpider", new AspectList().add(Aspect.BEAST, 5).add(Aspect.DEATH, 10).add(Aspect.TRAP, 10));
        registerEntityTag("Silverfish", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 10));
        registerEntityTag("Blaze", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.FIRE, 15).add(Aspect.FLIGHT, 5));
        registerEntityTag("LavaSlime", new AspectList().add(Aspect.WATER, 5).add(Aspect.FIRE, 10).add(Aspect.ALCHEMY, 5));
        registerEntityTag("EnderDragon", new AspectList().add(Aspect.ELDRITCH, 50).add(Aspect.BEAST, 30).add(Aspect.ENTROPY, 50).add(Aspect.FLIGHT, 10));
        registerEntityTag("WitherBoss", new AspectList().add(Aspect.UNDEAD, 50).add(Aspect.ENTROPY, 25).add(Aspect.FIRE, 25));
        registerEntityTag("Witch", new AspectList().add(Aspect.MAN, 15).add(Aspect.MAGIC, 5).add(Aspect.ALCHEMY, 10));
        registerEntityTag("Villager", new AspectList().add(Aspect.MAN, 15));
        registerEntityTag("VillagerGolem", new AspectList().add(Aspect.METAL, 15).add(Aspect.MAN, 5).add(Aspect.MECHANISM, 5).add(Aspect.MAGIC, 5));
        registerEntityTag("EnderCrystal", new AspectList().add(Aspect.ELDRITCH, 15).add(Aspect.AURA, 15).add(Aspect.LIFE, 15));
        registerEntityTag("ItemFrame", new AspectList().add(Aspect.SENSES, 5).add(Aspect.CRAFT, 5));
        registerEntityTag("Painting", new AspectList().add(Aspect.SENSES, 10).add(Aspect.CRAFT, 5));
        registerEntityTag("Guardian", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ELDRITCH, 10).add(Aspect.WATER, 10));
        registerEntityTag("Guardian", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ELDRITCH, 15).add(Aspect.WATER, 15));
        registerEntityTag("Rabbit", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5).add(Aspect.MOTION, 5));
        registerEntityTag("Endermite", new AspectList().add(Aspect.BEAST, 5).add(Aspect.ELDRITCH, 5).add(Aspect.MOTION, 5));
        registerEntityTag("PolarBear", new AspectList().add(Aspect.BEAST, 15).add(Aspect.COLD, 10));
        registerEntityTag("Shulker", new AspectList().add(Aspect.ELDRITCH, 10).add(Aspect.TRAP, 5).add(Aspect.FLIGHT, 5).add(Aspect.PROTECT, 5));
        registerEntityTag("EvocationIllager", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.MAGIC, 5).add(Aspect.MAN, 10));
        registerEntityTag("VindicationIllager", new AspectList().add(Aspect.AVERSION, 5).add(Aspect.MAGIC, 5).add(Aspect.MAN, 10));
        registerEntityTag("IllusionIllager", new AspectList().add(Aspect.SENSES, 5).add(Aspect.MAGIC, 5).add(Aspect.MAN, 10));
        registerEntityTag("Llama", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 5));
        registerEntityTag("Parrot", new AspectList().add(Aspect.BEAST, 5).add(Aspect.FLIGHT, 5).add(Aspect.SENSES, 5));
        registerEntityTag("Stray", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 5).add(Aspect.TRAP, 5));
        registerEntityTag("Vex", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.FLIGHT, 5).add(Aspect.MAGIC, 5).add(Aspect.MAN, 5));
        registerEntityTag("Stray", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 5).add(Aspect.TRAP, 5));
        // items
        registerObjectTag("oreLapis", new AspectList().add(Aspect.EARTH, 5).add(Aspect.SENSES, 15));
        registerObjectTag("oreDiamond", new AspectList().add(Aspect.EARTH, 5).add(Aspect.DESIRE, 15).add(Aspect.CRYSTAL, 15));
        registerObjectTag("gemDiamond", new AspectList().add(Aspect.CRYSTAL, 15).add(Aspect.DESIRE, 15));
        registerObjectTag("oreRedstone", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENERGY, 15));
        registerObjectTag(new ItemStack(Blocks.REDSTONE_ORE).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENERGY, 15));
        registerObjectTag("oreEmerald", new AspectList().add(Aspect.EARTH, 5).add(Aspect.DESIRE, 10).add(Aspect.CRYSTAL, 15));
        registerObjectTag("gemEmerald", new AspectList().add(Aspect.CRYSTAL, 15).add(Aspect.DESIRE, 10));
        registerObjectTag("oreQuartz", new AspectList().add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 10));
        registerObjectTag("gemQuartz", new AspectList().add(Aspect.CRYSTAL, 5));
        registerObjectTag("oreIron", new AspectList().add(Aspect.EARTH, 5).add(Aspect.METAL, 15));
        registerObjectTag("dustIron", new AspectList().add(Aspect.METAL, 15).add(Aspect.ENTROPY, 1));
        registerObjectTag("ingotIron", new AspectList().add(Aspect.METAL, 15));
        registerObjectTag("oreGold", new AspectList().add(Aspect.EARTH, 5).add(Aspect.METAL, 10).add(Aspect.DESIRE, 10));
        registerObjectTag("dustGold", new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 10).add(Aspect.ENTROPY, 1));
        registerObjectTag("ingotGold", new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 10));
        registerObjectTag(new ItemStack(Blocks.COAL_ORE).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENERGY, 15).add(Aspect.FIRE, 15));
        registerObjectTag(new ItemStack(Items.COAL).getItem(), new AspectList().add(Aspect.ENERGY, 10).add(Aspect.FIRE, 10));
        registerObjectTag("dustRedstone", new AspectList().add(Aspect.ENERGY, 10));
        registerObjectTag("dustGlowstone", new AspectList().add(Aspect.SENSES, 5).add(Aspect.LIGHT, 10));
        registerObjectTag("ingotCopper", new AspectList().add(Aspect.METAL, 10).add(Aspect.EXCHANGE, 5));
        registerObjectTag("dustCopper", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.EXCHANGE, 5));
        registerObjectTag("oreCopper", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.EXCHANGE, 5));
        registerObjectTag("clusterCopper", new AspectList().add(Aspect.ORDER, 5).add(Aspect.METAL, 15).add(Aspect.EARTH, 5).add(Aspect.EXCHANGE, 10));
        registerObjectTag("ingotTin", new AspectList().add(Aspect.METAL, 10).add(Aspect.CRYSTAL, 5));
        registerObjectTag("dustTin", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.CRYSTAL, 5));
        registerObjectTag("oreTin", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 5));
        registerObjectTag("clusterTin", new AspectList().add(Aspect.ORDER, 5).add(Aspect.METAL, 15).add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 10));
        registerObjectTag("ingotSilver", new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5));
        registerObjectTag("dustSilver", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.DESIRE, 5));
        registerObjectTag("oreSilver", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.DESIRE, 5));
        registerObjectTag("clusterSilver", new AspectList().add(Aspect.ORDER, 5).add(Aspect.METAL, 15).add(Aspect.EARTH, 5).add(Aspect.DESIRE, 10));
        registerObjectTag("ingotLead", new AspectList().add(Aspect.METAL, 10).add(Aspect.ORDER, 5));
        registerObjectTag("dustLead", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.ORDER, 5));
        registerObjectTag("oreLead", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.ORDER, 5));
        registerObjectTag("clusterLead", new AspectList().add(Aspect.ORDER, 5).add(Aspect.METAL, 15).add(Aspect.EARTH, 5).add(Aspect.ORDER, 10));
        registerObjectTag("ingotBrass", new AspectList().add(Aspect.METAL, 10).add(Aspect.TOOL, 5));
        registerObjectTag("dustBrass", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.TOOL, 5));
        registerObjectTag("ingotBronze", new AspectList().add(Aspect.METAL, 10).add(Aspect.TOOL, 5));
        registerObjectTag("dustBronze", new AspectList().add(Aspect.METAL, 10).add(Aspect.ENTROPY, 1).add(Aspect.TOOL, 5));
        registerObjectTag("oreUranium", new AspectList().add(Aspect.METAL, 10).add(Aspect.DEATH, 5).add(Aspect.ENERGY, 10));
        registerObjectTag("itemDropUranium", new AspectList().add(Aspect.METAL, 10).add(Aspect.DEATH, 5).add(Aspect.ENERGY, 10));
        registerObjectTag("ingotUranium", new AspectList().add(Aspect.METAL, 10).add(Aspect.DEATH, 5).add(Aspect.ENERGY, 10));
        registerObjectTag("gemRuby", new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.DESIRE, 10));
        registerObjectTag("gemGreenSapphire", new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.DESIRE, 10));
        registerObjectTag("gemSapphire", new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.DESIRE, 10));
        registerObjectTag("ingotSteel", new AspectList().add(Aspect.METAL, 15).add(Aspect.ORDER, 5));
        registerObjectTag("itemRubber", new AspectList().add(Aspect.MOTION, 5).add(Aspect.TOOL, 5));
        registerObjectTag("stone", new AspectList().add(Aspect.EARTH, 5));
        registerObjectTag("stoneGranite", new AspectList().add(Aspect.EARTH, 5));
        registerObjectTag("stoneDiorite", new AspectList().add(Aspect.EARTH, 5));
        registerObjectTag("stoneAndesite", new AspectList().add(Aspect.EARTH, 5));
        registerObjectTag("cobblestone", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 1));
        registerObjectTag(new ItemStack(Blocks.BEDROCK).getItem(), new AspectList().add(Aspect.VOID, 25).add(Aspect.ENTROPY, 25).add(Aspect.EARTH, 25).add(Aspect.DARKNESS, 25));
        registerObjectTag(new ItemStack(Blocks.DIRT).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 1));
        registerObjectTag(new ItemStack(Blocks.FARMLAND).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 2).add(Aspect.ORDER, 2));
        registerObjectTag("sand", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5));
        registerObjectTag("grass", new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2));
        registerObjectTag(new ItemStack(Blocks.DIRT_PATH).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.ORDER, 2));
        registerObjectTag("endstone", new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5));
        registerObjectTag("gravel", new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 2));
        registerObjectTag(new ItemStack(Blocks.MYCELIUM).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 1).add(Aspect.FLUX, 1));
        registerObjectTag(new ItemStack(Items.CLAY_BALL).getItem(), new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5));
        registerObjectTag(new ItemStack(Blocks.CLAY).getItem(), new AspectList().add(Aspect.FIRE, 1));
        registerObjectTag(new ItemStack(Items.BRICK).getItem(), new AspectList().add(Aspect.FIRE, 1).add(Aspect.EARTH, 1));
        registerObjectTag("netherrack", new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 2));
        registerObjectTag("ingotBrickNether", new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1));
        registerObjectTag(new ItemStack(Blocks.SOUL_SAND).getItem(), new AspectList().add(Aspect.EARTH, 3).add(Aspect.TRAP, 1).add(Aspect.SOUL, 3));
        registerObjectTag("blockGlass", new AspectList().add(Aspect.CRYSTAL, 5));
        registerObjectTag(new ItemStack(Blocks.MOSSY_COBBLESTONE).getItem(), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 1));
        registerObjectTag("obsidian", new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.DARKNESS, 5));

        createConfigFile();
        readConfig = readConfigFile();
    }
}
