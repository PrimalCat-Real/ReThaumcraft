package primalcat.thaumcraft.common.networking.packets.scan;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.common.networking.PacketManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class PlayerTargetSyncC2SPacket {

    private List<String> targets = new ArrayList<>();
    private UUID playerUUID;

    public PlayerTargetSyncC2SPacket(String target, UUID playerUUID) {
        List<String> targets = new ArrayList<>();
        targets.add(target);
        this.targets = targets;
        this.playerUUID = playerUUID;
    }
    public PlayerTargetSyncC2SPacket(List<String> targets, UUID playerUUID) {
        this.targets = targets;
        this.playerUUID = playerUUID;
    }

    public PlayerTargetSyncC2SPacket(FriendlyByteBuf buf) {
        int targetCount = buf.readInt();
        for (int i = 0; i < targetCount; i++) {
            targets.add(buf.readUtf());
        }
        this.playerUUID = buf.readUUID();

    }

    public void toBytes(FriendlyByteBuf buf) {
        int targetCount = targets.size();
        buf.writeInt(targetCount); // Write the number of strings in the list

        for (String target : targets) {
            buf.writeUtf(target); // Write each string in the list
        }
        buf.writeUUID(playerUUID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            MinecraftServer minecraftServer = context.getSender().getServer();
            ServerPlayer serverPlayer = minecraftServer.getPlayerList().getPlayer(playerUUID);
            if(serverPlayer != null){
                serverPlayer.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(aspectsProvider -> {
                    aspectsProvider.clearTargets();
                    aspectsProvider.mergeTargets(targets);
                });
                PacketManager.sendToPlayer(new PlayerTargetSyncS2CPacket(targets), serverPlayer);
            }


        });
        return true;
    }
}
