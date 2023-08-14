package primalcat.thaumcraft.events;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.aspects.PlayerAspectsProvider;

@Mod.EventBusSubscriber(modid = Thaumcraft.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).isPresent()) {
                System.out.println("Attaching PlayerAspects capability to player");
//                event.addCapability(new ResourceLocation(Thaumcraft.MOD_ID, "properties"), new PlayerThirstProvider());
                event.addCapability(new ResourceLocation(Thaumcraft.MOD_ID, "properties"), new PlayerAspectsProvider());
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps(); // Revive the old capabilities
            System.out.println("Original Capability Present: " + (event.getEntity().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).isPresent()));
            event.getOriginal().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(newStore -> {
                    System.out.println("Death from event");
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }

    }
//    @SubscribeEvent
//    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if(event.side == LogicalSide.SERVER) {
//            event.player.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(thirst -> {
//                event.player.sendSystemMessage(Component.literal("Subtracted Thirst"));
//            });
//        }
//    }

//    @SubscribeEvent
//    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//        event.register(PlayerAspects.class);
//        event.register(PlayerThirst.class);
//    }
}
