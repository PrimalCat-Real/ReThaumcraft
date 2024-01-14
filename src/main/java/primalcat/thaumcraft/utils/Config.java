package primalcat.thaumcraft.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.aspects.AspectList;
import primalcat.thaumcraft.common.registry.AspectRegistry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


public class Config {
    private static final String ASPECTS_FILE = "aspects.json";
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(Thaumcraft.MODID);
    private static final Path aspectsPath = CONFIG_PATH.resolve(ASPECTS_FILE);

    private static LinkedHashMap<String, AspectList> defaultAspect = new LinkedHashMap<>();

    public static void initConfig() {
        registerDefaultAspects();
        System.out.println(defaultAspect);
        try {
            ConfigManager.checkOrCreateConfigFolder(CONFIG_PATH);
        }
        catch (IOException e) {
            Thaumcraft.LOGGER.error("Failed to save config file: " + aspectsPath, e);
        }

    }
    public static LinkedHashMap<String, AspectList> getAspects() {
        return defaultAspect;
    }

    public static void setDefaultAspects(LinkedHashMap<String, AspectList> aspects) {
        defaultAspect = aspects;
    }

    public static void addDefaultAspects(String name, AspectList aspectList){
        defaultAspect.put(name, aspectList);
    }
    public static void addDefaultAspects(Item item, AspectList aspectList){
        defaultAspect.put(item.toString(), aspectList);
    }
    public static void addDefaultAspects(Block block, AspectList aspectList){
        defaultAspect.put(block.asItem().toString(), aspectList);
    }
    public static void addDefaultAspects(LivingEntity entity, AspectList aspectList){
        defaultAspect.put(entity.getName().toString(), aspectList);
    }

    public static void registerDefaultAspects(){
        addDefaultAspects("Zombie", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects("Husk", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.IGNIS, 1));
        addDefaultAspects("Giant", new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.HUMANUS, 5).add(AspectRegistry.TERRA, 2));
        addDefaultAspects("Skeleton", new AspectList().add(AspectRegistry.EXANIMIS, 3).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects("WitherSkeleton", new AspectList().add(AspectRegistry.EXANIMIS, 4).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.INFERNUS, 2));
        addDefaultAspects("Creeper", new AspectList().add(AspectRegistry.HERBA, 2).add(AspectRegistry.IGNIS, 2).add(AspectRegistry.TELUM, 2));
//        addDefaultAspects("Creeper", new AspectList().add(AspectInit.PLANT, 15).add(AspectInit.FIRE, 15).add(AspectInit.ENERGY, 15), new ThaumcraftApi.EntityTagsNBT("powered", 1));
        addDefaultAspects("Horse", new AspectList().add(AspectRegistry.BEAST, 4).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("Donkey", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("Mule", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("SkeletonHorse", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("ZombieHorse", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("Pig", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.TERRA, 2).add(AspectRegistry.GULA, 3));
        addDefaultAspects("XPOrb", new AspectList().add(AspectRegistry.COGNITIO, 2));
        addDefaultAspects("Sheep", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.TERRA, 2));
        addDefaultAspects("Cow", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 3));
        addDefaultAspects("MushroomCow", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.HERBA, 3).add(AspectRegistry.TERRA, 3));
        addDefaultAspects("SnowMan", new AspectList().add(AspectRegistry.GELUM, 4).add(AspectRegistry.AQUA, 1).add(AspectRegistry.PRAECANTATIO, 1));
        addDefaultAspects("Ozelot", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.PERDITIO, 2).add(AspectRegistry.MOTUS, 1));
        addDefaultAspects("Chicken", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.AIR, 1));
        addDefaultAspects("Squid", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.AQUA, 2));
        addDefaultAspects("Wolf", new AspectList().add(AspectRegistry.BEAST, 3).add(AspectRegistry.TERRA, 2).add(AspectRegistry.AVERSION, 1));
        addDefaultAspects("Bat", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.TENEBRAE, 1));
        addDefaultAspects("Spider", new AspectList().add(AspectRegistry.BEAST, 2).add(AspectRegistry.PERDITIO, 2).add(AspectRegistry.TRAP, 2));
        addDefaultAspects("Slime", new AspectList().add(AspectRegistry.LIFE, 2).add(AspectRegistry.LIMUS, 2).add(AspectRegistry.ALCHEMY, 1));
        addDefaultAspects("Ghast", new AspectList().add(AspectRegistry.EXANIMIS, 2).add(AspectRegistry.IGNIS, 2).add(AspectRegistry.INFERNUS, 3).add(AspectRegistry.IRA,2));
        addDefaultAspects("PigZombie", new AspectList().add(AspectRegistry.EXANIMIS, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.IRA, 5));
        addDefaultAspects("Enderman", new AspectList().add(AspectRegistry.ALIENIS, 4).add(AspectRegistry.ITER, 2).add(AspectRegistry.AIR, 1).add(AspectRegistry.INVIDIA, 2).add(AspectRegistry.SUPERBIA, 3));
        addDefaultAspects("CaveSpider", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VENENUM, 2).add(AspectRegistry.TRAP, 2));
        addDefaultAspects("Silverfish", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO,1));
        addDefaultAspects("Blaze", new AspectList().add(AspectRegistry.ALIENIS, 2).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.INFERNUS, 2));
        addDefaultAspects("LavaSlime", new AspectList().add(AspectRegistry.LIMUS, 3).add(AspectRegistry.IGNIS, 4).add(AspectRegistry.ALCHEMY, 1).add(AspectRegistry.INFERNUS, 2));
        addDefaultAspects("EnderDragon", new AspectList().add(AspectRegistry.ALIENIS, 20).add(AspectRegistry.BEAST, 20).add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.VOLATUS, 2).add(AspectRegistry.SUPERBIA, 10));
        addDefaultAspects("WitherBoss", new AspectList().add(AspectRegistry.EXANIMIS, 10).add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.IGNIS, 15).add(AspectRegistry.IRA, 7).add(AspectRegistry.INFERNUS, 7));
        addDefaultAspects("Witch", new AspectList().add(AspectRegistry.HUMANUS, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.ALCHEMY, 3));
        addDefaultAspects("Villager", new AspectList().add(AspectRegistry.HUMANUS, 3).add(AspectRegistry.GREED, 2));
        addDefaultAspects("VillagerGolem", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.TERRA, 5).add(AspectRegistry.MECHANISM, 3).add(AspectRegistry.PRAECANTATIO, 2));
        addDefaultAspects("EnderCrystal", new AspectList().add(AspectRegistry.ALIENIS, 3).add(AspectRegistry.AURA, 3).add(AspectRegistry.LIFE, 3).add(AspectRegistry.SUPERBIA, 2).add(AspectRegistry.VITREUS, 4));
        addDefaultAspects("ItemFrame", new AspectList().add(AspectRegistry.SENSUS, 2).add(AspectRegistry.FABRICO, 2));
        addDefaultAspects("Painting", new AspectList().add(AspectRegistry.SENSUS, 4).add(AspectRegistry.FABRICO, 2));
        addDefaultAspects("Guardian", new AspectList().add(AspectRegistry.BEAST, 4).add(AspectRegistry.ALIENIS, 5).add(AspectRegistry.AQUA, 5).add(AspectRegistry.TELUM, 5));
//        addDefaultAspects("Guardian", new AspectList().add(AspectInit.BEAST, 10).add(AspectInit.ELDRITCH, 15).add(AspectInit.WATER, 15), new ThaumcraftApi.EntityTagsNBT("Elder", true));
        addDefaultAspects("Rabbit", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.TERRA, 1).add(AspectRegistry.MOTUS, 1));
        addDefaultAspects("Endermite", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.MOTUS, 1).add(AspectRegistry.IRA,1));
        addDefaultAspects("PolarBear", new AspectList().add(AspectRegistry.BEAST, 5).add(AspectRegistry.GELUM, 2));
        addDefaultAspects("Shulker", new AspectList().add(AspectRegistry.ALIENIS, 4).add(AspectRegistry.TRAP, 2).add(AspectRegistry.VOLATUS, 2).add(AspectRegistry.TUTAMEN, 4));
        addDefaultAspects("EvocationIllager", new AspectList().add(AspectRegistry.ALIENIS, 3).add(AspectRegistry.PRAECANTATIO, 4).add(AspectRegistry.HUMANUS, 4));
        addDefaultAspects("VindicationIllager", new AspectList().add(AspectRegistry.AVERSION, 3).add(AspectRegistry.PRAECANTATIO, 1).add(AspectRegistry.HUMANUS, 4));
        addDefaultAspects("IllusionIllager", new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.PRAECANTATIO, 4).add(AspectRegistry.HUMANUS, 4));
        addDefaultAspects("Llama", new AspectList().add(AspectRegistry.BEAST, 5).add(AspectRegistry.AQUA, 2));
        addDefaultAspects("Parrot", new AspectList().add(AspectRegistry.BEAST, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.SENSUS, 1));
        addDefaultAspects("Stray", new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.HUMANUS, 1).add(AspectRegistry.TRAP, 1));
        addDefaultAspects("Vex", new AspectList().add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.VOLATUS, 1).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.HUMANUS, 1));
        addDefaultAspects("forge:gem/emerald", new AspectList().add(AspectRegistry.VITREUS, 4).add(AspectRegistry.GREED, 5));
        addDefaultAspects("forge:nuggets/iron", new AspectList().add(AspectRegistry.METAL, 1));
        addDefaultAspects("forge:gem/diamond", new AspectList().add(AspectRegistry.VITREUS, 4).add(AspectRegistry.GREED, 4));

        addDefaultAspects("forge:dusts/iron", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1));

        addDefaultAspects("forge:dusts/gold", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        addDefaultAspects("forge:dusts/redstone", new AspectList().add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.MECHANISM, 1));
        addDefaultAspects("forge:dusts/glowstone", new AspectList().add(AspectRegistry.SENSUS, 1).add(AspectRegistry.LUX, 2));
        addDefaultAspects(Items.GLOWSTONE, new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.LUX, 10));
        addDefaultAspects("forge:nuggets/copper", new AspectList().add(AspectRegistry.METAL, 1));
        addDefaultAspects("forge:ingots/copper", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERMUTATIO, 1));
        addDefaultAspects("forge:dusts/copper", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.PERMUTATIO, 1));
        addDefaultAspects("forge:nuggets/tin", new AspectList().add(AspectRegistry.METAL, 1));
        addDefaultAspects("forge:ingots/tin", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.VITREUS, 1));
        addDefaultAspects("forge:dusts/tin", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.VITREUS, 1));
        addDefaultAspects("forge:ores/tin", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.VITREUS, 1));
        addDefaultAspects("forge:nuggets/silver", new AspectList().add(AspectRegistry.METAL, 1));
        addDefaultAspects("forge:ingots/silver", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.GREED, 1));
        addDefaultAspects("forge:dusts/silver", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        addDefaultAspects("forge:nuggets/lead", new AspectList().add(AspectRegistry.METAL, 1));
        addDefaultAspects("forge:ingots/lead", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.ORDER, 1));
        addDefaultAspects("forge:dusts/lead", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.ORDER, 1));
        addDefaultAspects(Items.COAL, new AspectList().add(AspectRegistry.POTENTIA, 10).add(AspectRegistry.IGNIS, 10));
        addDefaultAspects("forge:ingots/uranium", new AspectList().add(AspectRegistry.METAL, 10).add(AspectRegistry.MORTUUS, 5).add(AspectRegistry.POTENTIA, 10));
        addDefaultAspects("forge:gems/ruby", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        addDefaultAspects("forge:gems/green_sapphire", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        addDefaultAspects("forge:gems/sapphire", new AspectList().add(AspectRegistry.VITREUS, 10).add(AspectRegistry.DESIRE, 10));
        addDefaultAspects("forge:ingots/steel", new AspectList().add(AspectRegistry.METAL, 15).add(AspectRegistry.ORDER, 5));
        addDefaultAspects("forge:items/rubber", new AspectList().add(AspectRegistry.MOTUS, 5).add(AspectRegistry.INSTRUMENTUM, 5));
        addDefaultAspects(Items.BEDROCK, new AspectList().add(AspectRegistry.VACUOS, 25).add(AspectRegistry.PERDITIO, 25).add(AspectRegistry.TERRA, 25).add(AspectRegistry.TENEBRAE, 25));
        addDefaultAspects(Items.FARMLAND, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.AQUA, 2).add(AspectRegistry.ORDER, 2));
        addDefaultAspects("forge:grass", new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.HERBA, 2));
        addDefaultAspects(Items.DIRT_PATH, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 2).add(AspectRegistry.ORDER, 2));
        addDefaultAspects("forge:endstone", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.TENEBRAE, 5));
        addDefaultAspects("forge:gravel", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.PERDITIO, 2));
        addDefaultAspects(Items.MYCELIUM, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 1).add(AspectRegistry.TAINT, 1));
        addDefaultAspects(Items.CLAY_BALL, new AspectList().add(AspectRegistry.AQUA, 5).add(AspectRegistry.TERRA, 5));
        addDefaultAspects(Items.BRICK, new AspectList().add(AspectRegistry.IGNIS, 1));
        addDefaultAspects(Items.BRICKS, new AspectList().add(AspectRegistry.IGNIS, 1).add(AspectRegistry.SENSUS, 1));
        addDefaultAspects("forge:netherrack", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.IGNIS, 2));
        addDefaultAspects("forge:ingots/brick_nether", new AspectList().add(AspectRegistry.IGNIS, 1).add(AspectRegistry.ORDER, 1));

        addDefaultAspects("forge:glass", new AspectList().add(AspectRegistry.VITREUS, 5));
        addDefaultAspects(Items.MOSSY_COBBLESTONE, new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.HERBA, 3).add(AspectRegistry.PERDITIO, 1));
        addDefaultAspects("forge:obsidian", new AspectList().add(AspectRegistry.TERRA, 5).add(AspectRegistry.IGNIS, 5).add(AspectRegistry.TENEBRAE, 5));
        addDefaultAspects(Items.STONE_BRICKS, new AspectList().add(AspectRegistry.TERRA, 1));
//        addDefaultAspects(new ItemStack(Blocks.STONEBRICK, 1, 2), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ENTROPY, 1));
//        addDefaultAspects(new ItemStack(Blocks.STONEBRICK, 1, 3), new AspectList(new ItemStack(Blocks.STONEBRICK)).add(AspectInit.ORDER, 1));
        addDefaultAspects(Blocks.SANDSTONE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.CYAN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.ORDER, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects("forge:terracotta", new AspectList().add(AspectRegistry.SENSUS, 1).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.TERRA, 3));

        addDefaultAspects(Items.BLACK_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.BLACK_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.BLUE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.BLUE_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.WHITE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.WHITE_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.RED_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.RED_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.YELLOW_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.YELLOW_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.GREEN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.GREEN_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.LIME_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.LIME_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.ORANGE_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.ORANGE_CONCRETE,new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.BROWN_CONCRETE_POWDER, new AspectList().add(AspectRegistry.AQUA, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.BROWN_CONCRETE, new AspectList().add(AspectRegistry.ORDER, 1).add(AspectRegistry.TERRA, 1));

        // my variants
        addDefaultAspects("forge:sand", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.IGNIS,1));
        addDefaultAspects("forge:cobblestone", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERDITIO, 1));
        addDefaultAspects("minecraft:planks", new AspectList().add(AspectRegistry.ARBOR, 1));
        addDefaultAspects("minecraft:logs", new AspectList().add(AspectRegistry.ARBOR, 4));
        addDefaultAspects("forge:gravel", new AspectList().add(AspectRegistry.TERRA, 2));
        addDefaultAspects("minecraft:bedrock", new AspectList().add(AspectRegistry.PERDITIO, 10).add(AspectRegistry.TENEBRAE, 10).add(AspectRegistry.TERRA,10).add(AspectRegistry.VACUOS, 10));
        addDefaultAspects("forge:glass", new AspectList().add(AspectRegistry.VITREUS, 1));
        addDefaultAspects(Items.RAW_GOLD_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.GREED, 1));
        addDefaultAspects(Items.RAW_IRON_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 3));
        addDefaultAspects(Items.RAW_COPPER_BLOCK, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.PERMUTATIO, 1));
        addDefaultAspects("minecraft:crystal_sound_blocks", new AspectList().add(AspectRegistry.VITREUS, 2).add(AspectRegistry.PRAECANTATIO, 1));
        addDefaultAspects("forge:storage_blocks/iron", new AspectList().add(AspectRegistry.METAL, 27));
        addDefaultAspects("forge:storage_blocks/gold", new AspectList().add(AspectRegistry.METAL, 20).add(AspectRegistry.GREED, 13));
        addDefaultAspects("forge:storage_blocks/diamond", new AspectList().add(AspectRegistry.VITREUS, 27).add(AspectRegistry.GREED, 27));
        addDefaultAspects("forge:storage_blocks/copper", new AspectList().add(AspectRegistry.METAL, 27).add(AspectRegistry.PERMUTATIO, 13));
        addDefaultAspects("forge:storage_blocks/lapis", new AspectList().add(AspectRegistry.SENSUS, 27));
        addDefaultAspects("minecraft:wool", new AspectList().add(AspectRegistry.FABRICO, 1).add(AspectRegistry.PANNUS, 1));
        addDefaultAspects(Items.PURPUR_BLOCK, new AspectList().add(AspectRegistry.ALIENIS, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        addDefaultAspects(Items.BLUE_ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        addDefaultAspects(Items.PACKED_ICE, new AspectList().add(AspectRegistry.GELUM, 4));
        addDefaultAspects(Items.SNOW, new AspectList().add(AspectRegistry.GELUM, 4));
        addDefaultAspects(Items.PUMPKIN, new AspectList().add(AspectRegistry.MESSIS, 4));
        addDefaultAspects(Items.JACK_O_LANTERN, new AspectList().add(AspectRegistry.MESSIS, 4).add(AspectRegistry.LUX, 3));
        addDefaultAspects(Items.CLAY, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.AQUA, 3));
        addDefaultAspects("forge:bookshelves", new AspectList().add(AspectRegistry.ARBOR, 4).add(AspectRegistry.PANNUS, 2).add(AspectRegistry.COGNITIO, 6));
        addDefaultAspects(Items.SOUL_SAND, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.TRAP, 2).add(AspectRegistry.SOUL, 1));
        addDefaultAspects(Items.SOUL_SOIL, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.TRAP, 1).add(AspectRegistry.SOUL, 2));
        addDefaultAspects(Items.BASALT, new AspectList().add(AspectRegistry.TERRA, 4).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.PERDITIO, 1));
        addDefaultAspects(Items.PACKED_MUD, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.FABRICO, 1));
        addDefaultAspects(Items.MUD_BRICKS, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.FABRICO, 2));
        addDefaultAspects(Items.NETHER_BRICK, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.IGNIS, 1).add(AspectRegistry.INFERNUS, 1));
        addDefaultAspects(Items.MYCELIUM, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.HERBA, 1).add(AspectRegistry.TAINT, 1));
        addDefaultAspects(Items.END_STONE, new AspectList().add(AspectRegistry.TENEBRAE, 1).add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.MELON, new AspectList().add(AspectRegistry.MESSIS, 4).add(AspectRegistry.AQUA, 1).add(AspectRegistry.FAMES, 2));
        addDefaultAspects(Items.REINFORCED_DEEPSLATE, new AspectList().add(AspectRegistry.TERRA, 10).add(AspectRegistry.TENEBRAE, 9).add(AspectRegistry.AURA, 9).add(AspectRegistry.ITER, 5));
        addDefaultAspects(Items.PRISMARINE_BRICKS, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.TERRA, 2).add(AspectRegistry.VITREUS, 9));
        addDefaultAspects(Items.PRISMARINE, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9));
        addDefaultAspects(Items.DARK_PRISMARINE, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9).add(AspectRegistry.TENEBRAE, 1).add(AspectRegistry.TERRA, 3));
        addDefaultAspects(Items.SEA_LANTERN, new AspectList().add(AspectRegistry.AQUA, 3).add(AspectRegistry.PRAECANTATIO, 3).add(AspectRegistry.VITREUS, 9).add(AspectRegistry.LUX, 7).add(AspectRegistry.SENSUS, 2));
        addDefaultAspects(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));
        addDefaultAspects(Items.NETHER_WART_BLOCK, new AspectList().add(AspectRegistry.HERBA, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.INFERNUS, 3));
        addDefaultAspects(Items.WARPED_WART_BLOCK, new AspectList().add(AspectRegistry.HERBA, 3).add(AspectRegistry.PRAECANTATIO, 2).add(AspectRegistry.INFERNUS, 1).add(AspectRegistry.ALIENIS, 1));
        addDefaultAspects(Items.RED_NETHER_BRICKS, new AspectList().add(AspectRegistry.IGNIS, 3).add(AspectRegistry.TERRA, 3).add(AspectRegistry.ORDER, 1).add(AspectRegistry.INFERNUS, 1));
        addDefaultAspects(Items.BONE_BLOCK, new AspectList().add(AspectRegistry.MORTUUS, 9).add(AspectRegistry.FLESH, 5));
        addDefaultAspects(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));
        addDefaultAspects(Items.MAGMA_BLOCK, new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.IGNIS, 3).add(AspectRegistry.LUX, 1));

        addDefaultAspects("forge:blocks/glowstone", new AspectList().add(AspectRegistry.SENSUS, 3).add(AspectRegistry.LUX, 6));

        addDefaultAspects("forge:storage_blocks/netherite", new AspectList().add(AspectRegistry.METAL, 36).add(AspectRegistry.POTENTIA, 13).add(AspectRegistry.GREED, 13).add(AspectRegistry.VITREUS, 27).add(AspectRegistry.ORDER,13));
        addDefaultAspects("forge:storage_blocks/emerald", new AspectList().add(AspectRegistry.VITREUS, 36).add(AspectRegistry.GREED, 33));
        addDefaultAspects(Items.EXPOSED_COPPER, new AspectList().add(AspectRegistry.METAL, 11).add(AspectRegistry.PERMUTATIO, 9).add(AspectRegistry.AQUA, 9));
        addDefaultAspects(Items.WEATHERED_COPPER, new AspectList().add(AspectRegistry.METAL, 11).add(AspectRegistry.PERMUTATIO, 5).add(AspectRegistry.AQUA, 7).add(AspectRegistry.PERDITIO, 11));
        addDefaultAspects(Items.OXIDIZED_COPPER, new AspectList().add(AspectRegistry.METAL, 9).add(AspectRegistry.PERMUTATIO, 9).add(AspectRegistry.AQUA, 9).add(AspectRegistry.PERDITIO, 9));
        addDefaultAspects(Items.WAXED_COPPER_BLOCK, new AspectList().add(AspectRegistry.METAL, 27).add(AspectRegistry.PERMUTATIO, 13).add(AspectRegistry.LIMUS, 9).add(AspectRegistry.SENSUS, 2));
        addDefaultAspects(Items.SPONGE, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.VACUOS, 2).add(AspectRegistry.TRAP, 2));
        addDefaultAspects(Items.WET_SPONGE, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.AQUA, 2).add(AspectRegistry.TRAP, 2));
        addDefaultAspects(Items.TINTED_GLASS, new AspectList().add(AspectRegistry.TENEBRAE, 2).add(AspectRegistry.VITREUS, 1).add(AspectRegistry.PRAECANTATIO, 1));
        addDefaultAspects(Items.HAY_BLOCK, new AspectList().add(AspectRegistry.FAMES, 6).add(AspectRegistry.MESSIS, 9));
        // TODO
        // CORALS
        // blackstone
        // crying obsidian
        // kelp block

        // garden
        addDefaultAspects(Items.MANGROVE_ROOTS, new AspectList().add(AspectRegistry.ARBOR, 2).add(AspectRegistry.AIR, 2).add(AspectRegistry.HERBA, 2));
        addDefaultAspects(Items.MUDDY_MANGROVE_ROOTS, new AspectList().add(AspectRegistry.ARBOR, 2).add(AspectRegistry.TERRA, 2).add(AspectRegistry.HERBA, 2));

        addDefaultAspects("minecraft:saplings", new AspectList().add(AspectRegistry.ARBOR, 1).add(AspectRegistry.HERBA, 2));
        // dirt
        addDefaultAspects("forge:dirt", new AspectList().add(AspectRegistry.TERRA, 2));
        addDefaultAspects(Items.COARSE_DIRT, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.PERDITIO, 1));
        addDefaultAspects(Items.ROOTED_DIRT, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.ARBOR, 1));

        addDefaultAspects(Items.DIRT, new AspectList().add(AspectRegistry.TERRA, 1));
        addDefaultAspects(Items.MUD, new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.AQUA,2));
        addDefaultAspects("minecraft:nylium", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.IGNIS,1).add(AspectRegistry.HERBA, 1));
        addDefaultAspects("minecraft:dirt", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.HERBA, 1));

        // stones
        addDefaultAspects("forge:stones/granite", new AspectList().add(AspectRegistry.TERRA, 4));
        addDefaultAspects("forge:stones/diorite", new AspectList().add(AspectRegistry.TERRA, 4));
        addDefaultAspects("forge:stones/andesite", new AspectList().add(AspectRegistry.TERRA, 4));
        addDefaultAspects("forge:stone", new AspectList().add(AspectRegistry.TERRA, 4));
        addDefaultAspects("forge:sandstone", new AspectList().add(AspectRegistry.TERRA, 3).add(AspectRegistry.PERDITIO, 3));
        addDefaultAspects(Items.DRIPSTONE_BLOCK, new AspectList().add(AspectRegistry.TERRA, 2).add(AspectRegistry.AQUA, 2));
        // ores
        addDefaultAspects("forge:ores/gold", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 2).add(AspectRegistry.GREED, 1));
        addDefaultAspects("forge:ores/iron", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.METAL, 3));
        addDefaultAspects("forge:ores/coal", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.IGNIS, 1));
        addDefaultAspects("forge:ores/copper", new AspectList().add(AspectRegistry.METAL, 2).add(AspectRegistry.TERRA, 1).add(AspectRegistry.PERMUTATIO, 1));
        addDefaultAspects("forge:ores/lead", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.ORDER, 1));
        addDefaultAspects("forge:ores/diamond", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.GREED, 3).add(AspectRegistry.VITREUS, 3));
        addDefaultAspects("forge:ores/lapis", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.SENSUS, 3));
        addDefaultAspects("forge:ores/redstone", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.MECHANISM, 2));
        addDefaultAspects("forge:ores/emerald", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.GREED, 4).add(AspectRegistry.VITREUS, 3));
        addDefaultAspects("forge:ores/quartz", new AspectList().add(AspectRegistry.TERRA, 1).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.IGNIS, 1));
        addDefaultAspects("forge:ores/silver", new AspectList().add(AspectRegistry.METAL, 3).add(AspectRegistry.PERDITIO, 1).add(AspectRegistry.GREED, 1));
        addDefaultAspects("forge:ores/netherite_scrap", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.POTENTIA, 2).add(AspectRegistry.GREED, 2).add(AspectRegistry.VITREUS, 4).add(AspectRegistry.ORDER,2));

        // items test
        addDefaultAspects(Items.GHAST_TEAR, new AspectList().add(AspectRegistry.EXANIMIS, 5).add(AspectRegistry.SOUL, 10).add(AspectRegistry.ALCHEMY, 10));
        addDefaultAspects("forge:ingots/iron", new AspectList().add(AspectRegistry.METAL, 5).add(AspectRegistry.SOUL, 10).add(AspectRegistry.ALCHEMY, 10));
        addDefaultAspects(Items.CAKE, new AspectList().add(AspectRegistry.DESIRE, 1).add(AspectRegistry.LIFE, 2));
        addDefaultAspects(Blocks.DARK_OAK_FENCE_GATE, new AspectList().add(AspectRegistry.TRAP, 5).add(AspectRegistry.MECHANISM, 5));
        addDefaultAspects("minecraft:water", new AspectList().add(AspectRegistry.AQUA, 5));
    }
}
