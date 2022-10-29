package thaumcraft.thaumcraft.common.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thaumcraft.thaumcraft.Thaumcraft;
import net.minecraftforge.event.level.ChunkEvent;
import thaumcraft.thaumcraft.common.aspects.AspectEventRegister;
import thaumcraft.thaumcraft.common.player.ScannedAspectsProvider;


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


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(ScannedAspectsProvider.Player_Aspects).isPresent()) {
                event.addCapability(new ResourceLocation(Thaumcraft.MODID, "properties"), new ScannedAspectsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if (event.isWasDeath()){
            event.getOriginal().getCapability(ScannedAspectsProvider.Player_Aspects).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ScannedAspectsProvider.Player_Aspects).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
}
