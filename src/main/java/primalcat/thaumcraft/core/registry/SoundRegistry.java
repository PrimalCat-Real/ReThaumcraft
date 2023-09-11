package primalcat.thaumcraft.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.Thaumcraft;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Thaumcraft.MODID);

    // sounds
    public static final RegistryObject<SoundEvent> scan = registerSoundEvent("scan");
    public static final RegistryObject<SoundEvent> alembicknock = registerSoundEvent("alembicknock");
    public static final RegistryObject<SoundEvent> cameraticks = registerSoundEvent("cameraticks");

    public static final RegistryObject<SoundEvent> fireloop = registerSoundEvent("fireloop");
    public static final RegistryObject<SoundEvent> heartbeat = registerSoundEvent("heartbeat");

    public static final RegistryObject<SoundEvent> spill = registerSoundEvent("spill");
    public static final RegistryObject<SoundEvent> dust = registerSoundEvent("dust");
    public static final RegistryObject<SoundEvent> learn = registerSoundEvent("learn");
    public static final RegistryObject<SoundEvent> bubble = registerSoundEvent("bubble");
    public static final RegistryObject<SoundEvent> creak = registerSoundEvent("creak");
    public static final RegistryObject<SoundEvent> squeek = registerSoundEvent("squeek");
    public static final RegistryObject<SoundEvent> jar = registerSoundEvent("jar");
    public static final RegistryObject<SoundEvent> swarm = registerSoundEvent("swarm");
    public static final RegistryObject<SoundEvent> swarmattack = registerSoundEvent("swarmattack");

    public static final RegistryObject<SoundEvent> fly = registerSoundEvent("fly");

    public static final RegistryObject<SoundEvent> key = registerSoundEvent("key");
    public static final RegistryObject<SoundEvent> ticks = registerSoundEvent("ticks");

    public static final RegistryObject<SoundEvent> clack = registerSoundEvent("clack");
    public static final RegistryObject<SoundEvent> pump = registerSoundEvent("pump");
    public static final RegistryObject<SoundEvent> poof = registerSoundEvent("poof");
    public static final RegistryObject<SoundEvent> page = registerSoundEvent("page");
    public static final RegistryObject<SoundEvent> pageturn = registerSoundEvent("pageturn");
    public static final RegistryObject<SoundEvent> cameraclack = registerSoundEvent("cameraclack");
    public static final RegistryObject<SoundEvent> write = registerSoundEvent("write");
    public static final RegistryObject<SoundEvent> erase = registerSoundEvent("erase");
    public static final RegistryObject<SoundEvent> brain = registerSoundEvent("brain");
    public static final RegistryObject<SoundEvent> crystal = registerSoundEvent("crystal");
    public static final RegistryObject<SoundEvent> wispdead = registerSoundEvent("wispdead");
    public static final RegistryObject<SoundEvent> wisplive = registerSoundEvent("wisplive");
    public static final RegistryObject<SoundEvent> wand = registerSoundEvent("wand");
    public static final RegistryObject<SoundEvent> wandfail = registerSoundEvent("wandfail");
    public static final RegistryObject<SoundEvent> rumble = registerSoundEvent("rumble");
    public static final RegistryObject<SoundEvent> ice = registerSoundEvent("ice");
    public static final RegistryObject<SoundEvent> jacobs = registerSoundEvent("jacobs");
    public static final RegistryObject<SoundEvent> hhoff = registerSoundEvent("hhoff");
    public static final RegistryObject<SoundEvent> hhon = registerSoundEvent("hhon");
    public static final RegistryObject<SoundEvent> pech_idle = registerSoundEvent("pech_idle");
    public static final RegistryObject<SoundEvent> pech_trade = registerSoundEvent("pech_trade");
    public static final RegistryObject<SoundEvent> pech_dice = registerSoundEvent("pech_dice");
    public static final RegistryObject<SoundEvent> pech_hit = registerSoundEvent("pech_hit");
    public static final RegistryObject<SoundEvent> pech_death = registerSoundEvent("pech_death");
    public static final RegistryObject<SoundEvent> pech_charge = registerSoundEvent("pech_charge");
    public static final RegistryObject<SoundEvent> shock = registerSoundEvent("shock");
    public static final RegistryObject<SoundEvent> zap = registerSoundEvent("zap");
    public static final RegistryObject<SoundEvent> craftfail = registerSoundEvent("craftfail");
    public static final RegistryObject<SoundEvent> craftstart = registerSoundEvent("craftstart");
    public static final RegistryObject<SoundEvent> runicshieldeffect = registerSoundEvent("runicshieldeffect");
    public static final RegistryObject<SoundEvent> runicshieldecharge = registerSoundEvent("runicshieldecharge");
    public static final RegistryObject<SoundEvent> wind = registerSoundEvent("wind");
    public static final RegistryObject<SoundEvent> tool = registerSoundEvent("tool");
    public static final RegistryObject<SoundEvent> gore = registerSoundEvent("gore");
    public static final RegistryObject<SoundEvent> tentacle = registerSoundEvent("tentacle");
    public static final RegistryObject<SoundEvent> upgrade = registerSoundEvent("upgrade");
    public static final RegistryObject<SoundEvent> whispers = registerSoundEvent("whispers");
    public static final RegistryObject<SoundEvent> monolith = registerSoundEvent("monolith");
    public static final RegistryObject<SoundEvent> infuser = registerSoundEvent("infuser");
    public static final RegistryObject<SoundEvent> infuserstart = registerSoundEvent("infuserstart");
    public static final RegistryObject<SoundEvent> egidle = registerSoundEvent("egidle");
    public static final RegistryObject<SoundEvent> egattack = registerSoundEvent("egattack");
    public static final RegistryObject<SoundEvent> egdeath = registerSoundEvent("egdeath");
    public static final RegistryObject<SoundEvent> egscreech = registerSoundEvent("egscreech");
    public static final RegistryObject<SoundEvent> crabclaw = registerSoundEvent("crabclaw");
    public static final RegistryObject<SoundEvent> crabdeath = registerSoundEvent("crabdeath");
    public static final RegistryObject<SoundEvent> crabtalk = registerSoundEvent("crabtalk");
    public static final RegistryObject<SoundEvent> chant = registerSoundEvent("chant");
    public static final RegistryObject<SoundEvent> coins = registerSoundEvent("coins");
    public static final RegistryObject<SoundEvent> urnbreak = registerSoundEvent("urnbreak");
    public static final RegistryObject<SoundEvent> evilportal = registerSoundEvent("evilportal");
    public static final RegistryObject<SoundEvent> grind = registerSoundEvent("grind");

//    public static final RegistryObject<SoundEvent> SCAN = registerSoundEvent("scan");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Thaumcraft.MODID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
