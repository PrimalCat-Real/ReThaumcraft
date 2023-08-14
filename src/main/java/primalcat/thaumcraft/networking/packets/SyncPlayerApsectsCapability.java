package primalcat.thaumcraft.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.api.Aspect;
import primalcat.thaumcraft.common.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.init.AspectInit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SyncPlayerApsectsCapability {
    private Map<String, Integer> aspectMap;
    private String targetName;

    public SyncPlayerApsectsCapability(Map<String, Integer> aspectMap, String target) {
        this.aspectMap = aspectMap;
        this.targetName = target;

    }

    public SyncPlayerApsectsCapability(FriendlyByteBuf buf) {
        int aspectCount = buf.readInt();
        aspectMap = new HashMap<>();
        for (int i = 0; i < aspectCount; i++) {
            String aspectName = buf.readUtf();
            int value = buf.readInt();
            aspectMap.put(aspectName, value);
        }
        targetName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(aspectMap.size());
        for (Map.Entry<String, Integer> entry : aspectMap.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
        buf.writeUtf(targetName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            player.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(aspectsProvider -> {
                // Handle the received data, for example:
//                aspectsProvider.setAspects(new ArrayList<>(aspectMap.keySet()), aspectMap);
                aspectsProvider.mergeMaps(aspectMap);
                aspectsProvider.addTarget(targetName);
//                aspectsProvider.addAspect(AspectInit.FIRE.getName(), 5);
                player.sendSystemMessage(Component.literal("Received aspect data from client "+aspectsProvider.getAspects().get(AspectInit.FIRE.getName())));
                player.sendSystemMessage(Component.literal("Received target data from client "+aspectsProvider.getTargetsList()));
            });
        });
        return true;
    }
}
