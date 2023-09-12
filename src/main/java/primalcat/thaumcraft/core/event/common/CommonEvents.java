package primalcat.thaumcraft.core.event.common;


import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.core.config.ClientConfig;
import primalcat.thaumcraft.core.registry.AspectRegistry;
import primalcat.thaumcraft.common.networking.PacketManager;
import primalcat.thaumcraft.common.networking.packets.scan.PlayerAspectsSyncS2CPacket;
import primalcat.thaumcraft.common.networking.packets.scan.PlayerTargetsSyncS2CPacket;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity() instanceof ServerPlayer player){
            SynchedEntityData playerEntityData = player.getEntityData();

            player.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(playerAspectsData -> {
                if(playerAspectsData!= null){
                    if(playerAspectsData.getAspects().isEmpty()){
                        // firth time join give player primal aspects
                        playerAspectsData.addAspect(AspectRegistry.AIR.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectRegistry.IGNIS.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectRegistry.TERRA.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectRegistry.AQUA.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectRegistry.ORDER.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectRegistry.PERDITIO.getName(), ClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                    }
                    PacketManager.sendToPlayer(new PlayerTargetsSyncS2CPacket(playerAspectsData.getTargetsList()), player);
                    PacketManager.sendToPlayer(new PlayerAspectsSyncS2CPacket(playerAspectsData.getAspects()), player);

                }
            });
        }
    }
}
