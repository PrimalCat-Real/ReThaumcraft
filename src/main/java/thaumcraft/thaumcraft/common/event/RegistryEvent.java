package thaumcraft.thaumcraft.common.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thaumcraft.thaumcraft.Thaumcraft;
import net.minecraftforge.event.level.ChunkEvent;
import thaumcraft.thaumcraft.common.aspects.AspectEventRegister;




@Mod.EventBusSubscriber(modid = Thaumcraft.MODID)
public class RegistryEvent {


    @SubscribeEvent
    public static void onRegisterEvent(ChunkEvent.Load event){



        
//        CompoundTag t = new CompoundTag();
//        t.putString("TestNbt", "Test");
//        new ItemStack(Items.CAKE).setTag(t);
//        System.out.println("on load");
//        LoadAspectsFormConfig aspectsConfig =  new LoadAspectsFormConfig();
//        aspectsConfig.getJsonFile();
//        System.out.println("Gen file");
    }
}
