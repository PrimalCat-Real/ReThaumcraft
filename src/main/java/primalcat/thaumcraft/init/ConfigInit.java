package primalcat.thaumcraft.init;


import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.config.ConfigAspects;

import java.io.IOException;

import static primalcat.thaumcraft.Thaumcraft.LOGGER;



public class ConfigInit {
    public static void setup(){
        // Register the config registration event
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigAspects.COMMON_SPEC);
        try{
            ConfigAspects.main();
        }catch (Exception e){
            LOGGER.error("Error register config " + e.getMessage());
        }
//        System.out.println(Minecraft.getInstance().gameDirectory);
        System.out.println("Setup thaumcraft config");

    }
}
