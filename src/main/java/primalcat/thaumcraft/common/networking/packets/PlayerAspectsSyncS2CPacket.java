package primalcat.thaumcraft.common.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.core.aspects.AspectList;
import primalcat.thaumcraft.client.ScanManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlayerAspectsSyncS2CPacket {
    private Map<String, Integer> targetAspects;

    public PlayerAspectsSyncS2CPacket(AspectList targetAspects) {
        this.targetAspects = targetAspects.toMap();
    }

    public PlayerAspectsSyncS2CPacket(Map<String, Integer> targetAspects) {
        this.targetAspects = targetAspects;
    }

    public PlayerAspectsSyncS2CPacket(FriendlyByteBuf buf) {
        int aspectCount = buf.readInt();
        targetAspects = new HashMap<>();
        for (int i = 0; i < aspectCount; i++) {
            String aspectName = buf.readUtf();
            int value = buf.readInt();
            targetAspects.put(aspectName, value);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(targetAspects.size());
        for (Map.Entry<String, Integer> entry : targetAspects.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(targetAspects != null) {
                ScanManager.setPlayerAspects(targetAspects);
            }
        });
        return true;
    }
}
