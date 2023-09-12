package primalcat.thaumcraft.common.networking.packets.scan;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.common.networking.PacketManager;
import primalcat.thaumcraft.core.aspects.AspectList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PlayerAspectSyncC2SPacket {
    private Map<String, Integer> aspectMap;
    private UUID playerUUID;

    public PlayerAspectSyncC2SPacket(Map<String, Integer> aspectMap,  UUID playerUUID) {
        this.aspectMap = aspectMap;
        this.playerUUID = playerUUID;
    }


    public PlayerAspectSyncC2SPacket(AspectList aspectList,  UUID playerUUID) {
        this.aspectMap = aspectList.toMap();
        this.playerUUID = playerUUID;
    }

    public PlayerAspectSyncC2SPacket(FriendlyByteBuf buf) {
        int aspectCount = buf.readInt();
        aspectMap = new HashMap<>();
        for (int i = 0; i < aspectCount; i++) {
            String aspectName = buf.readUtf();
            int value = buf.readInt();
            aspectMap.put(aspectName, value);
        }
        this.playerUUID = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(aspectMap.size());
        for (Map.Entry<String, Integer> entry : aspectMap.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
        buf.writeUUID(playerUUID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MinecraftServer minecraftServer = context.getSender().getServer();
            ServerPlayer serverPlayer = minecraftServer.getPlayerList().getPlayer(playerUUID);
            if(serverPlayer != null){
                serverPlayer.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(aspectsProvider -> {
                    aspectsProvider.mergeMaps(aspectMap);
                });
                PacketManager.sendToPlayer(new PlayerAspectsSyncS2CPacket(aspectMap), serverPlayer);
            }
        });
        return true;
    }
}
