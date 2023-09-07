package primalcat.thaumcraft.init;

import net.minecraft.nbt.Tag;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.utilites.DataManipulation;

import java.lang.reflect.Array;
import java.util.*;

public class AspectInit {
    private static LinkedHashMap<String, Aspect> aspectsHolder = new LinkedHashMap<>();

    public static List<String> getItemsAspectsHolderName() {
        List<String> fullAspectsHolderNames = new ArrayList<>();
        DataManipulation.mergeLists(fullAspectsHolderNames, new ArrayList<>(itemAspects.keySet()));
        DataManipulation.mergeLists(fullAspectsHolderNames, new ArrayList<>(entityAspects.keySet()));
        return fullAspectsHolderNames;
    }

    private static LinkedHashMap<String, AspectList> itemAspects = new LinkedHashMap<>();
    private static LinkedHashMap<String, AspectList> entityAspects = new LinkedHashMap<>();

    public static void setItemAspects(LinkedHashMap<String, AspectList> itemAspects) {
        AspectInit.itemAspects = itemAspects;
    }

    public static void setEntityAspects(LinkedHashMap<String, AspectList> entityAspects) {
        AspectInit.entityAspects = entityAspects;
    }

    private static LinkedHashMap<Tag, AspectList> tagAspects = new LinkedHashMap<>();


    public static LinkedHashMap<String, AspectList> getItemAspects() {
        return itemAspects;
    }

    public static void putItemAspects(String itemName, AspectList itemAspects) {
        AspectInit.itemAspects.put(itemName, itemAspects);
    }

    public static LinkedHashMap<String, AspectList> getEntityAspects() {
        return entityAspects;
    }

    public static void putEntityAspects(String entityName, AspectList entityAspects) {
        AspectInit.entityAspects.put(entityName, entityAspects);
    }

    public static LinkedHashMap<Tag, AspectList> getTagAspects() {
        return tagAspects;
    }

    public static void setTagAspects(LinkedHashMap<Tag, AspectList> tagAspects) {
        AspectInit.tagAspects = tagAspects;
    }


    public static boolean checkAspectExist(String aspectName){
        return aspectsHolder.keySet().contains(aspectName);
    }

    public static Collection<Aspect> getAspectsStringsNames(){
        return aspectsHolder.values();
    }
    public static boolean checkAspectExist(Aspect aspect){
        return aspectsHolder.values().contains(aspect);
    }
    public static Set<String> getAspectsInitStringsNames(){
        return aspectsHolder.keySet();
    }

    public static Aspect getAspect(String aspectName){
        return  aspectsHolder.get(aspectName);
    }

    public static Aspect getAspect(Integer index){
        Set<String> aspectSet = aspectsHolder.keySet();
        List<String> aspectList = new ArrayList<>(aspectSet);
        int wrappedIndex = (index % aspectList.size() + aspectList.size()) % aspectList.size();
//        System.out.println(wrappedIndex + " length " + aspectsArray.length);
        return getAspect(aspectList.get(wrappedIndex));
    }
    public static void putAspect(String aspectName, Aspect aspect){
        aspectsHolder.put(aspectName, aspect);
    }


    // tier 0
    public static final Aspect AIR = new Aspect("aer", 0xFFFF7E, null, 1);
    public static final Aspect TERRA = new Aspect("terra", 0x56C000, null, 1);
    public static final Aspect IGNIS = new Aspect("ignis", 0xFF5A01, null, 1);
    public static final Aspect AQUA = new Aspect("aqua", 0x3CD4FC, null, 1);
    public static final Aspect ORDER = new Aspect("ordo", 0xD5D4EC, null, 1);
    public static final Aspect PERDITIO = new Aspect("perditio", 0x404040, null, 771);

    // tier 1
    public static final Aspect VACUOS = new Aspect("vacuos", 0x888888, new Aspect[] { AIR, PERDITIO}, 771);
    public static final Aspect LUX = new Aspect("lux", 0xFFF663, new Aspect[] { AIR, IGNIS});
    public static final Aspect TEMPESTAS = new Aspect("tempestas", 0xFFFFFF, new Aspect[] { AIR, AQUA});
    public static final Aspect MOTUS = new Aspect("motus", 0xCDCCF4, new Aspect[] { AIR, ORDER });
    public static final Aspect GELUM = new Aspect("gelum", 0xE1FFFF, new Aspect[] {IGNIS, PERDITIO});
    public static final Aspect VITREUS = new Aspect("vitreus", 0x80FFFF, new Aspect[] {TERRA, ORDER });
    public static final Aspect LIFE = new Aspect("victus", 14548997, new Aspect[] {AQUA, TERRA});
    public static final Aspect VENENUM = new Aspect("venenum", 9039872, new Aspect[] {AQUA, PERDITIO});
    public static final Aspect POTENTIA = new Aspect("potentia", 0xC0FFFF, new Aspect[] { ORDER, IGNIS});
    public static final Aspect PERMUTATIO = new Aspect("permutatio", 0x578357, new Aspect[] {PERDITIO, ORDER });

    // tier 2
    public static final Aspect METAL = new Aspect("metallum", 0xB5B5CD, new Aspect[] {TERRA, VITREUS});
    public static final Aspect MORTUUS = new Aspect("mortuus", 0x887788, new Aspect[] { LIFE, PERDITIO});
    public static final Aspect VOLATUS = new Aspect("volatus", 0xE7E7D7, new Aspect[] { AIR, MOTUS});
    public static final Aspect TENEBRAE = new Aspect("tenebrae", 0x222222, new Aspect[] {VACUOS, LUX});
    public static final Aspect SOUL = new Aspect("spiritus", 0xEBEBFB, new Aspect[] { LIFE, MORTUUS});
    public static final Aspect SANO = new Aspect("sano", 0xFF2F34, new Aspect[] { LIFE, ORDER });
    public static final Aspect ITER = new Aspect("iter", 0xE0585B, new Aspect[] {MOTUS, TERRA});
    public static final Aspect ALIENIS = new Aspect("alienis", 0x805080, new Aspect[] {VACUOS, TENEBRAE});
    public static final Aspect PRAECANTATIO = new Aspect("praecantatio", 0x9700C0, new Aspect[] {VACUOS, POTENTIA});
    public static final Aspect AURA = new Aspect("auram", 0xFFC0FF, new Aspect[] {PRAECANTATIO, AIR });
    public static final Aspect TAINT = new Aspect("vitium", 0x800080, new Aspect[] {PRAECANTATIO, PERDITIO});
    public static final Aspect LIMUS = new Aspect("limus", 0x1F800, new Aspect[] { LIFE, AQUA});
    public static final Aspect HERBA = new Aspect("herba", 0x1AC00, new Aspect[] { LIFE, TERRA});
    public static final Aspect ARBOR = new Aspect("arbor", 0x876531, new Aspect[] { AIR, HERBA});
    public static final Aspect BEAST = new Aspect("bestia", 0x9F6409, new Aspect[] {MOTUS, LIFE });
    public static final Aspect FLESH = new Aspect("corpus", 0xEE478D, new Aspect[] {MORTUUS, BEAST });
    public static final Aspect EXANIMIS = new Aspect("exanimis", 0x2a2e02, new Aspect[] {MOTUS, MORTUUS});
    public static final Aspect COGNITIO = new Aspect("cognitio", 0xFFC2B3, new Aspect[] {IGNIS, SOUL });
    public static final Aspect SENSUS = new Aspect("sensus", 0xFD9FF, new Aspect[] { AIR, SOUL });
    public static final Aspect HUMANUS = new Aspect("humanus", 0xFFD7C0, new Aspect[] { BEAST, COGNITIO});
    public static final Aspect MESSIS = new Aspect("messis", 0xE1B371, new Aspect[] {HERBA, HUMANUS});
    public static final Aspect PERFODIO = new Aspect("perfodio", 0xDCD2D8, new Aspect[] {HUMANUS, TERRA});
    public static final Aspect INSTRUMENTUM = new Aspect("instrumentum", 0x4040EE, new Aspect[] {HUMANUS, ORDER });
    public static final Aspect METO = new Aspect("meto", 0xEEAD82, new Aspect[] {MESSIS, INSTRUMENTUM});
    public static final Aspect TELUM = new Aspect("telum", 0xC05050, new Aspect[] {INSTRUMENTUM, IGNIS});
    public static final Aspect FAMES = new Aspect("fames", 0x9A0305, new Aspect[] { LIFE, VACUOS});
    public static final Aspect GREED = new Aspect("lucrum", 0xE6BE44, new Aspect[] {HUMANUS, FAMES});
    public static final Aspect FABRICO = new Aspect("fabrico", 0x809D80, new Aspect[] {HUMANUS, INSTRUMENTUM});
    public static final Aspect PANNUS = new Aspect("pannus", 0xEAEAC2, new Aspect[] {INSTRUMENTUM, BEAST });
    public static final Aspect MECHANISM = new Aspect("machina", 0x8080A0, new Aspect[] {MOTUS, INSTRUMENTUM});
    public static final Aspect TRAP = new Aspect("vinculum", 0x9A8080, new Aspect[] {MOTUS, PERDITIO});

    public static final Aspect AVERSION = new Aspect("aversio",0xC05050, new Aspect[] {SOUL, PERDITIO});

    public static final Aspect DESIRE = new Aspect("desiderium",0xE6BE44, new Aspect[] {SOUL, VACUOS});

    public static final Aspect ALCHEMY = new Aspect("alkimia",0x23AC9D, new Aspect[] {PRAECANTATIO, AQUA});

    public static final Aspect TUTAMEN = new Aspect("tutamen",0x00C0C0, new Aspect[] {INSTRUMENTUM, TERRA});

    public static final Aspect INFERNUS = new Aspect("infernus", 0xdb0715, new Aspect[] {IGNIS, PRAECANTATIO});
    public static final Aspect IRA = new Aspect("ira", 0xed3254, new Aspect[] {TELUM, IGNIS});

    public static final Aspect GULA = new Aspect("gula", 0xa6833d, new Aspect[] {FAMES, VACUOS});

    public static final Aspect SUPERBIA = new Aspect("superbia",0x73298a, new Aspect[] {VOLATUS, VACUOS});

    public static final Aspect INVIDIA = new Aspect("invidia", 0x14e085, new Aspect[] {SENSUS, FAMES});
    public static final Aspect TEMPUS = new Aspect("tempus", 0x14e085, new Aspect[] {VACUOS, ORDER});

}
