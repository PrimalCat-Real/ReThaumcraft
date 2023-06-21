package hesio.thaumcraft.api.inits;


import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.configs.ConfigAspects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigInit {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        // Register the config registration event
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigAspects.COMMON_SPEC);
        ConfigAspects.createConfigFile();
    }
}
