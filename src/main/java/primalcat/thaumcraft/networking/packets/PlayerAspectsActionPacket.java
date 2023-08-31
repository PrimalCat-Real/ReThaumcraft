package primalcat.thaumcraft.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.init.AspectInit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PlayerAspectsActionPacket {
    private Integer aspectCount;
    private String aspectName;

    private UUID playerUUID;
    private String action;

    public PlayerAspectsActionPacket(String aspectName, Integer aspectCount, UUID playerUUID, String action) {
        this.aspectName = aspectName;
        this.aspectCount = aspectCount;
        this.playerUUID = playerUUID;
        this.action = action;
    }

    public PlayerAspectsActionPacket(Aspect aspect, Integer aspectCount, UUID playerUUID, String action) {
        this.aspectName = aspect.getName();
        this.aspectCount = aspectCount;
        this.playerUUID = playerUUID;
        this.action = action;
    }


    public PlayerAspectsActionPacket(FriendlyByteBuf buf) {
        this.aspectName = buf.readUtf();
        this.aspectCount = buf.readInt();
        this.playerUUID = buf.readUUID();
        this.action = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(aspectName);
        buf.writeInt(aspectCount);
        buf.writeUUID(playerUUID);
        buf.writeUtf(action);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Here we are on the server!

//            player = context.getSender();
            MinecraftServer minecraftServer = context.getSender().getServer();
            ServerPlayer serverPlayer = minecraftServer.getPlayerList().getPlayer(playerUUID);
//            Thaumcraft.LOGGER.info(serverPlayer.getGameProfile().getName() + " received aspectName: " + aspectName + " count: " + aspectCount);
            serverPlayer.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(aspectsProvider -> {
                if(serverPlayer != null){
                    switch (action) {
                        case "give":
                            // code block executed if expression equals value1
                            if (aspectName.equals("all")) {
                                Map<String, Integer> allAspects = new HashMap<>();
                                for (Aspect aspect : AspectInit.getAspectsStringsNames()) {
                                    allAspects.put(aspect.getName(), aspectCount);
                                }
                                aspectsProvider.mergeMaps(allAspects);
                            }else{
                                aspectsProvider.addAspect(aspectName, aspectCount);
                            }
                            serverPlayer.sendSystemMessage(Component.literal("Player aspects: "+aspectsProvider.getAspects().toString()));
                            break;
                        case "clear":
                            if(aspectName.equals("all") && aspectCount == 0){
                                HashMap<String, Integer> all = new HashMap<>();
                                aspectsProvider.setAspects(all);
                            } else if (aspectName.equals("all")) {
                                Map<String, Integer> allAspects = new HashMap<>();
                                for (Aspect aspect : AspectInit.getAspectsStringsNames()) {
                                    allAspects.put(aspect.getName(), aspectCount);
                                }
                                aspectsProvider.subtractMaps(allAspects);
                            }else{
                                aspectsProvider.addAspect(aspectName, aspectCount);
                            }
                            serverPlayer.sendSystemMessage(Component.literal("Player aspects: "+aspectsProvider.getAspects().toString()));
                            break;
                        case "info":
                            // code block executed if expression equals value2
                            if(aspectName.equals("all")){
                                serverPlayer.sendSystemMessage(Component.literal("Player aspects" + aspectName + ": "+aspectsProvider.getAspects().toString()));
                            }else{
                                serverPlayer.sendSystemMessage(Component.literal("Player aspect" + aspectName + ": "+aspectsProvider.getAspectCount(aspectName).toString()));
                            }

                            break;
                        // more cases...
                        default:
                            // code block executed if none of the cases match
                    }
                }
//                aspectsProvider.setAspects(all);
//
//                serverPlayer.sendSystemMessage(Component.literal("Received target data from client "+aspectsProvider.getAspects().toString()));
            });





            // You can perform your desired logic here based on the received values.
            // For example, you can update a player's aspects or perform other actions.
        });
        return true;
    }

}
