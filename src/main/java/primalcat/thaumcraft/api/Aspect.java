package primalcat.thaumcraft.api;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

public class Aspect {
    private String name;
    private int color;
    private ResourceLocation aspectImage;
    private int blend;
    private Aspect[] components;

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public ResourceLocation getAspectImage() {
        return aspectImage;
    }

    public int getBlend() {
        return blend;
    }

    public Aspect[] getComponents() {
        return components;
    }


    public static LinkedHashMap<String,Aspect> aspects = new LinkedHashMap<String,Aspect>();

    public Aspect(String name, int color, Aspect[] components, ResourceLocation image, int blend){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = image;
        this.blend = blend;
        aspects.put(name, this);
    }

    public Aspect(String name, int color, Aspect[] components, int blend){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = new ResourceLocation("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png");
        this.blend = blend;
        aspects.put(name, this);
    }
    public Aspect(String name, int color, Aspect[] components){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = new ResourceLocation("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png");
        aspects.put(name, this);
    }

    public static Aspect getAspect(String tag) {
        return aspects.get(tag);
    }

    public boolean isPrimal() {
        return (getComponents() == null) || (getComponents().length != 2);
    }


    ///////////////////////////////

    // tier 0
    public static final Aspect AIR = new Aspect("aer", 16777086, null, 1);
    public static final Aspect EARTH = new Aspect("terra", 5685248, null, 1);
    public static final Aspect FIRE = new Aspect("ignis", 16734721, null, 1);
    public static final Aspect WATER = new Aspect("aqua", 3986684, null, 1);
    public static final Aspect ORDER = new Aspect("ordo", 14013676, null, 1);
    public static final Aspect ENTROPY = new Aspect("perditio", 4210752, null, 771);

    // tier 1
    public static final Aspect VOID = new Aspect("vacuos", 8947848, new Aspect[] { AIR, ENTROPY }, 771);
    public static final Aspect LIGHT = new Aspect("lux", 16774755, new Aspect[] { AIR, FIRE });
    public static final Aspect WEATHER = new Aspect("tempestas", 16777215, new Aspect[] { AIR, WATER });
    public static final Aspect MOTION = new Aspect("motus", 13487348, new Aspect[] { AIR, ORDER });
    public static final Aspect COLD = new Aspect("gelum", 14811135, new Aspect[] { FIRE, ENTROPY });
    public static final Aspect CRYSTAL = new Aspect("vitreus", 8454143, new Aspect[] { EARTH, ORDER });
    public static final Aspect LIFE = new Aspect("victus", 14548997, new Aspect[] { WATER, EARTH });
    public static final Aspect POISON = new Aspect("venenum", 9039872, new Aspect[] { WATER, ENTROPY });
    public static final Aspect ENERGY = new Aspect("potentia", 12648447, new Aspect[] { ORDER, FIRE });
    public static final Aspect EXCHANGE = new Aspect("permutatio", 5735255, new Aspect[] { ENTROPY, ORDER });

    // tier 2
    public static final Aspect METAL = new Aspect("metallum", 11908557, new Aspect[] { EARTH, CRYSTAL });
    public static final Aspect DEATH = new Aspect("mortuus", 8943496, new Aspect[] { LIFE, ENTROPY });
    public static final Aspect FLIGHT = new Aspect("volatus", 15198167, new Aspect[] { AIR, MOTION });
    public static final Aspect DARKNESS = new Aspect("tenebrae", 2236962, new Aspect[] { VOID, LIGHT });
    public static final Aspect SOUL = new Aspect("spiritus", 15461371, new Aspect[] { LIFE, DEATH });
    public static final Aspect HEAL = new Aspect("sano", 16723764, new Aspect[] { LIFE, ORDER });
    public static final Aspect TRAVEL = new Aspect("iter", 14702683, new Aspect[] { MOTION, EARTH });
    public static final Aspect ELDRITCH = new Aspect("alienis", 8409216, new Aspect[] { VOID, DARKNESS });
    public static final Aspect MAGIC = new Aspect("praecantatio", 9896128, new Aspect[] { VOID, ENERGY });
    public static final Aspect AURA = new Aspect("auram", 16761087, new Aspect[] { MAGIC, AIR });
    public static final Aspect TAINT = new Aspect("vitium", 8388736, new Aspect[] { MAGIC, ENTROPY });
    public static final Aspect SLIME = new Aspect("limus", 129024, new Aspect[] { LIFE, WATER });
    public static final Aspect PLANT = new Aspect("herba", 109568, new Aspect[] { LIFE, EARTH });
    public static final Aspect TREE = new Aspect("arbor", 8873265, new Aspect[] { AIR, PLANT });
    public static final Aspect BEAST = new Aspect("bestia", 10445833, new Aspect[] { MOTION, LIFE });
    public static final Aspect FLESH = new Aspect("corpus", 15615885, new Aspect[] { DEATH, BEAST });
    public static final Aspect UNDEAD = new Aspect("exanimis", 3817472, new Aspect[] { MOTION, DEATH });
    public static final Aspect MIND = new Aspect("cognitio", 16761523, new Aspect[] { FIRE, SOUL });
    public static final Aspect SENSES = new Aspect("sensus", 1038847, new Aspect[] { AIR, SOUL });
    public static final Aspect MAN = new Aspect("humanus", 16766912, new Aspect[] { BEAST, MIND });
    public static final Aspect CROP = new Aspect("messis", 14791537, new Aspect[] { PLANT, MAN });
    public static final Aspect MINE = new Aspect("perfodio", 14471896, new Aspect[] { MAN, EARTH });
    public static final Aspect TOOL = new Aspect("instrumentum", 4210926, new Aspect[] { MAN, ORDER });
    public static final Aspect HARVEST = new Aspect("meto", 15641986, new Aspect[] { CROP, TOOL });
    public static final Aspect WEAPON = new Aspect("telum", 12603472, new Aspect[] { TOOL, FIRE });
    public static final Aspect ARMOR = new Aspect("tutamen", 49344, new Aspect[] { TOOL, EARTH });
    public static final Aspect HUNGER = new Aspect("fames", 10093317, new Aspect[] { LIFE, VOID });
    public static final Aspect GREED = new Aspect("lucrum", 15121988, new Aspect[] { MAN, HUNGER });
    public static final Aspect CRAFT = new Aspect("fabrico", 8428928, new Aspect[] { MAN, TOOL });
    public static final Aspect CLOTH = new Aspect("pannus", 15395522, new Aspect[] { TOOL, BEAST });
    public static final Aspect MECHANISM = new Aspect("machina", 8421536, new Aspect[] { MOTION, TOOL });
    public static final Aspect TRAP = new Aspect("vinculum", 10125440, new Aspect[] { MOTION, ENTROPY });


    //TERTIARY
    public static Aspect ALCHEMY = new Aspect("alkimia",0x23ac9d, new Aspect[] {MAGIC, WATER});
    public static Aspect FLUX = new Aspect("vitium",0x800080, new Aspect[] {ENTROPY, MAGIC});
    public static Aspect AVERSION = new Aspect("aversio",0xc05050, new Aspect[] {SOUL, ENTROPY});
    public static Aspect PROTECT = new Aspect("praemunio",0x00c0c0, new Aspect[] {SOUL, EARTH});
    public static Aspect DESIRE = new Aspect("desiderium",0xe6be44, new Aspect[] {SOUL, VOID});
}