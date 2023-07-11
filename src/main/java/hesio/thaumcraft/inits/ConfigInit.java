package hesio.thaumcraft.inits;


import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.configs.ConfigAspects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ConfigInit {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) throws IOException {
        // Register the config registration event
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigAspects.COMMON_SPEC);


        ConfigAspects.main();
    }

//    @SubscribeEvent
//    public static void onServerAboutToStart(final ServerAboutToStartEvent event) {
//        System.out.println("Test before items");
////        PocketRegistryManager.loadData();
////
////        LOGGER.info(LOGGER_PREFIX + "[FMLServerAboutToStartEvent] Server about to start...");
//    }
//    @SubscribeEvent
//    public static void onCommonSetup(FMLCommonSetupEvent event) {
//        for (Item item : ForgeRegistries.ITEMS) {
//            ItemStack stack = new ItemStack(item);
//            CompoundTag nbt = stack.getOrCreateTag();
//            nbt.putString("test", "test");
//        }
//    }
}
