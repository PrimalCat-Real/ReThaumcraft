package thaumcraft.thaumcraft.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thaumcraft.thaumcraft.Thaumcraft;

public class ModSounds {

    /**
     * Registry sounds for mod
     */
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Thaumcraft.MODID);

    /**
     * All sounds
     */
    public static final RegistryObject<SoundEvent> scan = registerSoundEvent("scan");
    public static final RegistryObject<SoundEvent> alembicknock = registerSoundEvent("alembicknock");
    public static final RegistryObject<SoundEvent> cameraticks = registerSoundEvent("cameraticks");
    public static final RegistryObject<SoundEvent> cameraclack = registerSoundEvent("cameraclack");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Thaumcraft.MODID, name)));
    }

    public static void register(){
        SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
