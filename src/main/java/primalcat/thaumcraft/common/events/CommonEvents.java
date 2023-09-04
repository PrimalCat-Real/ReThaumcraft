package primalcat.thaumcraft.common.events;


import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.client.ScanManager;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.config.ThaumcraftClientConfig;
import primalcat.thaumcraft.init.AspectInit;
import primalcat.thaumcraft.networking.PacketManager;
import primalcat.thaumcraft.networking.packets.PlayerAspectsActionPacket;
import primalcat.thaumcraft.networking.packets.PlayerAspectsSyncS2CPacket;
import primalcat.thaumcraft.networking.packets.PlayerTargetsSyncS2CPacket;

@Mod.EventBusSubscriber(modid = Thaumcraft.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity() instanceof ServerPlayer player){

            player.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(playerAspectsData -> {
                if(playerAspectsData!= null){
                    if(playerAspectsData.getAspects().isEmpty()){
                        // firth time join give player primal aspects
                        playerAspectsData.addAspect(AspectInit.AIR.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectInit.FIRE.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectInit.EARTH.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectInit.WATER.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectInit.ORDER.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                        playerAspectsData.addAspect(AspectInit.ENTROPY.getName(), ThaumcraftClientConfig.PLAYER_PRIMAL_ASPECTS_COUNT.get());
                    }
                    PacketManager.sendToPlayer(new PlayerTargetsSyncS2CPacket(playerAspectsData.getTargetsList()), player);
                    PacketManager.sendToPlayer(new PlayerAspectsSyncS2CPacket(playerAspectsData.getAspects()), player);

                }
            });
        }
    }
}
